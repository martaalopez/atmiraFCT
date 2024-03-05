FROM maven:3.8.3-openjdk-17 AS build
LABEL authors="luish"
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk-slin
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]