FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
EXPOSE 8080
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]