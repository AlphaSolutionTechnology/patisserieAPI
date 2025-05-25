# Use uma imagem base do OpenJDK com Maven para build
FROM maven:3.8.6-eclipse-temurin:17-jdk-alpine AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto (pom.xml e código-fonte)
COPY pom.xml .
COPY src ./src

# Executa o build do Maven para gerar o .jar
RUN mvn clean package -DskipTests

# Etapa final: Usa uma imagem leve do OpenJDK para rodar a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o .jar gerado da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]