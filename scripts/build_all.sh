echo "### Building Maven Modules ###"
echo "> mvn clean package -f ../pom.xml"
mvn clean package -Dmaven.test.skip=true -f ../pom.xml -T8C