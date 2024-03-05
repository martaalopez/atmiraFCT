FROM maven:3.8.3-openjdk-17 AS build
LABEL authors="luish"
COPY . .
RUN mvn clean package -DskipTests
RUN mvn clean package


FROM openjdk:17-jdk-slim
COPY --from=build /target/atmiraFCT-0.0.1-SNAPSHOT.jar atmiraFCT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]