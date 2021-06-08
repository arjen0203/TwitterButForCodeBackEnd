kubectl create namespace insecure

kubectl apply -f ../k8s/nginx-ingress-controller.yml

helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

helm install rabbit --set auth.username=admin,auth.password=RabbitPassword bitnami/rabbitmq
kubectl apply -f components.yaml