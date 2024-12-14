# Etapa 1: Build
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Etapa 2: .env
ARG ENV_FILE_PATH=.env
COPY ${ENV_FILE_PATH} /app/.env

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#export
CMD ["sh", "-c", "export $(grep -v '^#' /app/.env | xargs) && java -jar app.jar"]
