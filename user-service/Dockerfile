FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

#ENV DB_URL=jdbc:postgresql://postgres:5432/postgres
#ENV DB_USER=postgres
#ENV DB_PASSWORD=1234
#ENV KAFKA_BOOTSTRAP=kafka:9092
ENTRYPOINT ["java", "-jar", "app.jar"]
