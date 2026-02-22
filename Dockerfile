FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY build/libs/Spring-Security-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]