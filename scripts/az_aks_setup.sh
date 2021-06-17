#!/bin/sh
RG_NAME="codex"
ACR_NAME="codexacr"
AKS_NAME="codex-aks"
INGRESS_NAMESPACE="ingress-nginx"
HOST="code-x.club"

# Remind the user to change environment variables from codex.kube to the right host.
# e.g. code-x.club
echo "A reminder to set the host to the correct values for services like frontend, auth, and email!"
read -p "Press enter to continue or ctrl-c to exit."

# Set script to exit on error
set -e
 Login to Azure
 https://docs.microsoft.com/en-us/cli/azure/reference-index?view=azure-cli-latest#az_login
az login

# Create resource group
# https://docs.microsoft.com/en-us/cli/azure/group?view=azure-cli-latest#az_group_create
az group create -n $RG_NAME -l westeurope
# Create container registry
# https://docs.microsoft.com/en-us/cli/azure/acr?view=azure-cli-latest#az_acr_create
az acr create -g $RG_NAME -n $ACR_NAME --sku Basic

# Create kubernetes cluster
# https://docs.microsoft.com/en-us/cli/azure/aks?view=azure-cli-latest#az_aks_create
az aks create -g $RG_NAME -n $AKS_NAME -l westeurope --attach-acr $ACR_NAME --generate-ssh-keys -c 1 -z 1
# Link kubectl
# https://docs.microsoft.com/en-us/cli/azure/aks?view=azure-cli-latest#az_aks_get_credentials
az aks get-credentials -g $RG_NAME -n $AKS_NAME

# Run the generic setup.sh since as we linked kubectl
sh ./setup.sh

# Create an SQL database on azure and set deploy the credentials
# TODO: setup SQL database

# Find all available services
for service_dir in ../*/services/*/
do
  dir=${service_dir%*/} # Remove trailing slash
  service=${dir##*/} # Remove everything till final slash

  # Build and push each service's container
  # https://docs.microsoft.com/en-us/cli/azure/acr?view=azure-cli-latest#az_acr_build
  az acr build -r $ACR_NAME -t $service -f $dir/deploy/docker/Dockerfile $dir

  # Let's read the current deployment file for this service
  k8s_deployment=`cat $dir/deploy/k8s/deployment.yml`
  # Set the image property to the image just pushed to ACR
  updated_k8s_deployment=${k8s_deployment//"image: $service"/"image: $ACR_NAME.azurecr.io/$service"}
  # Apply the updated k8s deployment on AKS
  echo "$updated_k8s_deployment" | kubectl apply -f -
done

# Let's read the ingress settings
ingress_settings=`cat ../k8s/ingress-settings.yml`
# Update all occurrences of the host property
updated_ingress_settings=${ingress_settings//"http://codex.kube"/"https://$HOST"}
final_ingress_settings=${updated_ingress_settings//"codex.kube"/"$HOST"}
# Apply updated ingress settings
echo "$final_ingress_settings" | kubectl apply -f -

# Fetch our assigned IP and print it to console
ip=$(kubectl get svc -n $INGRESS_NAMESPACE ingress-nginx-controller | grep -E -o "([0-9]{1,3}[\.]){3}[0-9]{1,3}" - | tail -1)
echo "Your application is available at: http://$ip/"
