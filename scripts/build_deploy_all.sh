echo "### Building Maven Modules ###"
echo "> mvn clean install -f ../pom.xml"
mvn clean install -f ../pom.xml
echo "### Building & Deploying Services ###"
./deploy_service.sh "example-service"
./deploy_service.sh "post-service"
./deploy_service.sh "frontend-service"