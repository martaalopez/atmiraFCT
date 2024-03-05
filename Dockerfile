FROM maven:3.8.3-openjdk-17 AS build

ARG DB_USERNAME
ARG DB_PASSWORD
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD

LABEL authors="luish"
COPY . .
RUN mvn clean package -DskipTests
RUN mvn clean package


FROM openjdk:17-jdk-slim
COPY --from=build /target/atmiraFCT-0.0.1-SNAPSHOT.jar /app/atmiraFCT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/atmiraFCT.jar"]