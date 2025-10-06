FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/FoodSecretApiConnection-0.0.1-SNAPSHOT.jar app.jar

RUN apk add --no-cache ca-certificates
RUN apk add --no-cache curl

ENTRYPOINT ["java", "-jar", "app.jar"]
