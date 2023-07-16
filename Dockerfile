FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/employee-crud-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=docker"]
