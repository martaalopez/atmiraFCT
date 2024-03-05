ENV DB_USERNAME=hc66n960k93cwuv2qhj4 \
    DB_PASSWORD=pscale_pw_vVjiI1j8m1opeWSp5xKLxRX9I49i6w9uDcI0k65Smjg
FROM maven:3.8.3-openjdk-17 AS build
LABEL authors="luish"
COPY . .
RUN mvn clean package -DskipTests
RUN mvn clean package


FROM openjdk:17-jdk-slim
COPY --from=build /target/atmiraFCT-0.0.1-SNAPSHOT.jar /app/atmiraFCT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/atmiraFCT.jar"]