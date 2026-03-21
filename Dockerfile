# Stage 1 — build inside Docker
FROM gradle:8-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2 — run
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/Spring-Security-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]