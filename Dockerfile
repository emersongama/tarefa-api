# Build com Maven
FROM maven:3.8.6-openjdk-11-slim AS build

WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn dependency:go-offline
RUN mvn clean install -DskipTests

# Monta a imagem final com a OpenJDK para executar a aplicação
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]