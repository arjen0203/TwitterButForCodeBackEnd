echo "### Building Maven Modules ###"
echo "> mvn clean package -f ../pom.xml"
mvn clean package -Dmaven.test.skip=true -f ../pom.xml -T8C
echo "### Building & Deploying Services ###"
./deploy_service.sh "example-service"
./deploy_service.sh "post-service"
./deploy_service.sh "frontend-service"
./deploy_service.sh "user-service"
./deploy_service.sh "auth-service"