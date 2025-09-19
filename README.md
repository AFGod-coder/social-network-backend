# Social Network Backend

Una red social construida con microservicios usando Spring Boot, PostgreSQL y Docker.

## 🏗️ Arquitectura

El proyecto está compuesto por 4 microservicios:

- **auth-service** (Puerto 8081): Manejo de autenticación y usuarios
- **social-data-service** (Puerto 8082): Gestión de posts y likes
- **business-service** (Puerto 8083): Lógica de negocio
- **bff-service** (Puerto 8084): Backend for Frontend (API Gateway)

## 📋 Prerrequisitos

- Java 17 o superior
- Docker y Docker Compose
- Git

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone https://github.com/AFGod-coder/social-network-backend.git
cd social-network-backend
```

### 2. Configurar Variables de Entorno

Crear un archivo `.env` en la raíz del proyecto:

```env
# Database Configuration
POSTGRES_USER=postgres
POSTGRES_PASSWORD=password
POSTGRES_DB=social_network

# Service URLs (para desarrollo local)
AUTH_SERVICE_URL=http://localhost:8081
SOCIAL_DATA_SERVICE_URL=http://localhost:8082
BUSINESS_SERVICE_URL=http://localhost:8083
BFF_SERVICE_URL=http://localhost:8084
```

### 3. Construir y Ejecutar con Docker

```bash
# Construir todos los servicios
./gradlew build

# Levantar todos los servicios con Docker Compose
docker-compose up -d

# Verificar que todos los servicios estén ejecutándose
docker-compose ps
```

### 4. Verificar la Instalación

Los servicios estarán disponibles en:

- **Auth Service**: http://localhost:8081
- **Social Data Service**: http://localhost:8082
- **Business Service**: http://localhost:8083
- **BFF Service**: http://localhost:8084

### 5. Verificar Logs

```bash
# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f bff-service
docker-compose logs -f business-service
docker-compose logs -f social-data-service
docker-compose logs -f auth-service
```

## 🧪 Probar la API

### Usuarios de Prueba

El sistema incluye datos de prueba:

- **Usuario Admin**: 
  - Email: admin@example.com
  - Password: admin123

### Endpoints Principales

#### Autenticación
```bash
# Login
POST http://localhost:8084/api/v1/bff/auth/login
{
  "email": "admin@example.com",
  "password": "admin123"
}

# Registro
POST http://localhost:8084/api/v1/bff/auth/register
{
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan@example.com",
  "password": "password123",
  "alias": "juanperez"
}
```

#### Posts
```bash
# Obtener feed del usuario
GET http://localhost:8084/api/v1/bff/feed?userId=1

# Crear post
POST http://localhost:8084/api/v1/bff/posts
{
  "authorId": 1,
  "message": "Mi primer post"
}

# Obtener likes de un post
GET http://localhost:8084/api/v1/bff/posts/1/likes

# Dar like a un post
POST http://localhost:8084/api/v1/bff/posts/1/likes
{
  "userId": 1
}

# Quitar like de un post
DELETE http://localhost:8084/api/v1/bff/posts/1/likes/1
```

## 🔧 Desarrollo

### Estructura del Proyecto

```
social-network-backend/
├── auth-service/           # Servicio de autenticación
├── social-data-service/    # Servicio de datos sociales
├── business-service/       # Servicio de lógica de negocio
├── bff-service/           # Backend for Frontend
├── docker-compose.yml     # Configuración de Docker
└── settings.gradle        # Configuración de Gradle
```

### Comandos Útiles

```bash
# Construir un servicio específico
./gradlew :auth-service:build
./gradlew :social-data-service:build
./gradlew :business-service:build
./gradlew :bff-service:build

# Ejecutar tests
./gradlew test

# Limpiar y reconstruir
./gradlew clean build

# Reiniciar un servicio específico
docker-compose restart bff-service

# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v
```

## 🐛 Solución de Problemas

### Error 502 Bad Gateway

Si encuentras errores 502, verifica:

1. **Servicios ejecutándose**:
   ```bash
   docker-compose ps
   ```

2. **Logs de errores**:
   ```bash
   docker-compose logs bff-service
   docker-compose logs business-service
   ```

3. **Conectividad entre servicios**:
   ```bash
   # Verificar que los servicios se puedan comunicar
   docker-compose exec bff-service curl http://business-service:8083/actuator/health
   ```

### Error de Base de Datos

Si hay problemas con la base de datos:

```bash
# Reiniciar solo las bases de datos
docker-compose restart auth-db social-data-db business-db

# Ver logs de la base de datos
docker-compose logs auth-db
```

### Puerto en Uso

Si un puerto está en uso:

```bash
# Verificar qué proceso usa el puerto
netstat -ano | findstr :8084

# Cambiar puertos en docker-compose.yml si es necesario
```

## 📊 Monitoreo

### Health Checks

Cada servicio expone un endpoint de health:

- http://localhost:8081/actuator/health
- http://localhost:8082/actuator/health
- http://localhost:8083/actuator/health
- http://localhost:8084/actuator/health

### Swagger UI

Documentación de la API disponible en:

- http://localhost:8084/swagger-ui.html

## 🔐 Seguridad

- JWT tokens para autenticación
- CORS configurado para desarrollo
- Validación de entrada en todos los endpoints
- Manejo seguro de contraseñas con BCrypt

## 📝 Notas de Desarrollo

- Los servicios se comunican usando Feign Client
- Las bases de datos usan Flyway para migraciones
- Los logs están configurados para desarrollo
- CORS está habilitado para http://localhost:4200 (frontend)

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.