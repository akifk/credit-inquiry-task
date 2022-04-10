FROM maven:3.6.0-jdk-11-slim AS build

COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service

RUN mvn package
ENTRYPOINT ["java","-jar","target/credit-inquiry-task-1.0-SNAPSHOT.jar"]