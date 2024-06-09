# Utiliza uma imagem base do Maven
FROM maven:3.8.6-openjdk-11-slim AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte do projeto
COPY src ./src

# Compila o projeto e executa os testes
RUN mvn clean install
#RUN mvn clean install -DskipTests

# Utiliza uma imagem do OpenJDK para executar a aplicação
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Define o comando de execução da aplicação
CMD ["java", "-jar", "app.jar"]