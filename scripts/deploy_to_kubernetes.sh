echo "> kubectl delete -f $1"
kubectl delete -f $1
echo "> kubectl apply -f $1"
kubectl apply -f $1