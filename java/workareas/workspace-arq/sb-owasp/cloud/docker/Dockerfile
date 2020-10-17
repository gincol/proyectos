### STAGE 1: Build ###
FROM maven:3.6.3-jdk-11 as builder

ARG artifactid
ARG sonar

COPY . /tmp/app
COPY ./pom.xml /tmp/app

WORKDIR /tmp/app

RUN mvn clean package


### STAGE 2: Setup ###
FROM openjdk:11-jre-slim

COPY --from=builder /tmp/app/target/*.jar /app.jar

EXPOSE 8080

# comando a ejecutar
CMD ["java", "-jar", "/app.jar"]
