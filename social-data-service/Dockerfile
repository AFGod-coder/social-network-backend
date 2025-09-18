# Dockerfile (multistage) - social-data-service
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew

RUN ./gradlew bootJar -x test

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

# Opcionales: mejor manejo de memoria con JAVA_OPTS
ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
