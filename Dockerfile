
FROM openjdk:23-slim-bookworm

WORKDIR /app

COPY build/libs/prueba-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

