echo "## Building Image $1 ##"
echo "> docker build --tag $1 --file cx/services/$1/deploy/docker/Dockerfile cx/services/$1"
docker build --tag $1 --file "../cx/services/$1/deploy/docker/Dockerfile" "../cx/services/$1"
echo "## Deploying $1 ##"
./deploy_to_kubernetes.sh "../cx/services/$1/deploy/k8s/deployment.yml"