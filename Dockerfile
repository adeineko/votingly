FROM eclipse-temurin:17.0.10_7-jdk-alpine
COPY build/libs/Integration4-17.0.10.jar Integration4-17.0.10.jar
ENTRYPOINT ["java","-jar","/Integration4-17.0.10.jar"]

# https://hub.docker.com/_/eclipse-temurin/tags?page=&page_size=&ordering=&name=17.0.10
