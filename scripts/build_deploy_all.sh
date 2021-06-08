./build_all.sh
echo "### Building & Deploying Services ###"
./deploy_service.sh "example-service"
./deploy_service.sh "post-service"
./deploy_service.sh "frontend-service"
./deploy_service.sh "user-service"
./deploy_service.sh "auth-service"