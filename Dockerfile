FROM eclipse-temurin:17.0.10_7-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} Integration4.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/Integration4.jar"]

# https://hub.docker.com/_/eclipse-temurin/tags?page=&page_size=&ordering=&name=17.0.10

