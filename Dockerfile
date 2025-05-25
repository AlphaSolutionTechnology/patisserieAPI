# Etapa de build: Usa uma imagem com Maven para compilar o projeto
FROM maven:3.8.6-eclipse-temurin-17 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto (pom.xml e código-fonte)
COPY pom.xml .
COPY src ./src

# Executa o build do Maven para gerar o .jar
RUN mvn clean package

# Etapa final: Usa uma imagem leve para rodar a aplicação
FROM eclipse-temurin:17-jdk-focal

# Define o diretório de trabalho
WORKDIR /app

# Copia o .jar gerado da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]