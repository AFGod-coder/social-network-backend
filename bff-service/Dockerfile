# Usa JDK 17 para construir la app
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copia todo el proyecto
COPY . .

# Construye el jar usando Gradle (sin tests)
RUN ./gradlew bootJar -x test

# --------------------------
# Imagen final
# --------------------------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia el jar construido
COPY --from=builder /app/build/libs/*.jar app.jar

# Expone el puerto (se define en application.yml)
EXPOSE 8080

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]
