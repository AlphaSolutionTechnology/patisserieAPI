FROM maven:3.8.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

COPY start-app.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/start-app.sh

USER nobody

ENTRYPOINT ["/usr/local/bin/start-app.sh"]