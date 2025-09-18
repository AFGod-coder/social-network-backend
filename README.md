# 🌐 Social Network Backend

Una red social completa construida con microservicios usando Spring Boot, que incluye autenticación JWT, gestión de usuarios, posts y likes.

## 🏗️ Arquitectura

El proyecto está construido con una arquitectura de microservicios que incluye:

- **Auth Service**: Manejo de autenticación y usuarios
- **Social Data Service**: Gestión de datos sociales (posts, likes)
- **Business Service**: Lógica de negocio y orquestación
- **BFF Service**: Backend-for-Frontend para el cliente

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Security** con JWT
- **Spring Cloud OpenFeign** para comunicación entre servicios
- **PostgreSQL** como base de datos
- **Docker & Docker Compose** para containerización
- **Flyway** para migraciones de base de datos
- **Swagger/OpenAPI** para documentación
- **Lombok** para reducir código boilerplate

## 📋 Prerrequisitos

- Java 17 o superior
- Docker y Docker Compose
- Git

## 🛠️ Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd social-network-backend
```

### 2. Ejecutar con Docker Compose
```bash
docker-compose up
```

Esto iniciará todos los servicios:
- **Auth Service**: http://localhost:8082
- **Social Data Service**: http://localhost:8081
- **Business Service**: http://localhost:8083
- **BFF Service**: http://localhost:8084

### 3. Verificar que los servicios estén funcionando
```bash
# Verificar estado de los contenedores
docker-compose ps

# Ver logs
docker-compose logs -f
```

## 📚 Documentación de la API

Una vez que los servicios estén ejecutándose, puedes acceder a la documentación Swagger en:

- **Auth Service**: http://localhost:8082/swagger-ui.html
- **Social Data Service**: http://localhost:8081/swagger-ui.html
- **Business Service**: http://localhost:8083/swagger-ui.html
- **BFF Service**: http://localhost:8084/swagger-ui.html

## 🔐 Autenticación

El sistema utiliza JWT (JSON Web Tokens) para la autenticación:

1. **Registro**: `POST /api/v1/bff/auth/register`
2. **Login**: `POST /api/v1/bff/auth/login`
3. **Usar token**: Incluir en header `Authorization: Bearer <token>`

## 🎯 Endpoints Principales

### BFF Service (Puerto 8084) - Punto de entrada principal

#### Autenticación
- `POST /api/v1/bff/auth/register` - Registrar nuevo usuario
- `POST /api/v1/bff/auth/login` - Iniciar sesión
- `GET /api/v1/bff/auth/users/{id}` - Obtener usuario de autenticación

#### Funcionalidades (requieren autenticación)
- `GET /api/v1/bff/users/{id}` - Obtener usuario completo
- `GET /api/v1/bff/feed?userId={id}` - Obtener feed de usuario
- `POST /api/v1/bff/posts` - Crear nuevo post
- `POST /api/v1/bff/posts/{postId}/likes` - Dar like a un post
- `GET /api/v1/bff/posts/{postId}/likes/count` - Contar likes de un post

## 🗄️ Base de Datos

El proyecto utiliza PostgreSQL con las siguientes bases de datos:

- **auth_db**: Datos de autenticación y usuarios
- **social_data_db**: Posts, likes y datos sociales
- **business_db**: Datos de lógica de negocio

Las migraciones se ejecutan automáticamente al iniciar los servicios.

## 🧪 Ejemplo de Uso

### 1. Registrar un usuario
```bash
curl -X POST http://localhost:8084/api/v1/bff/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@ejemplo.com",
    "password": "password123",
    "firstName": "Juan",
    "lastName": "Pérez",
    "alias": "juanperez",
    "dateOfBirth": "1990-01-01"
  }'
```

### 2. Iniciar sesión
```bash
curl -X POST http://localhost:8084/api/v1/bff/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@ejemplo.com",
    "password": "password123"
  }'
```

### 3. Crear un post (usar token del login)
```bash
curl -X POST http://localhost:8084/api/v1/bff/posts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <tu-token-jwt>" \
  -d '{
    "authorId": 1,
    "message": "¡Hola mundo! Mi primer post en la red social."
  }'
```

## 🏗️ Estructura del Proyecto

```
social-network-backend/
├── auth-service/                 # Servicio de autenticación
│   ├── src/main/java/com/example/auth/
│   ├── src/main/resources/
│   └── build.gradle
├── social-data-service/          # Servicio de datos sociales
│   ├── src/main/java/com/example/socialdata/
│   ├── src/main/resources/
│   └── build.gradle
├── business-service/             # Servicio de lógica de negocio
│   ├── src/main/java/com/example/business/
│   ├── src/main/resources/
│   └── build.gradle
├── bff-service/                  # Backend-for-Frontend
│   ├── src/main/java/com/example/bff/
│   ├── src/main/resources/
│   └── build.gradle
├── docker-compose.yml            # Configuración de Docker
├── .gitignore
└── README.md
```

## 🔧 Desarrollo

### Ejecutar servicios individualmente

```bash
# Auth Service
cd auth-service
./gradlew bootRun

# Social Data Service
cd social-data-service
./gradlew bootRun

# Business Service
cd business-service
./gradlew bootRun

# BFF Service
cd bff-service
./gradlew bootRun
```

### Compilar todos los servicios

```bash
# Desde la raíz del proyecto
./gradlew build
```

## 🐳 Docker

### Comandos útiles

```bash
# Iniciar todos los servicios
docker-compose up

# Iniciar en segundo plano
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Reconstruir imágenes
docker-compose build --no-cache

# Limpiar volúmenes
docker-compose down -v
```

## 🚀 Despliegue

### Variables de entorno

Los servicios pueden configurarse mediante variables de entorno:

- `SPRING_DATASOURCE_URL`: URL de la base de datos
- `SPRING_DATASOURCE_USERNAME`: Usuario de la base de datos
- `SPRING_DATASOURCE_PASSWORD`: Contraseña de la base de datos
- `JWT_SECRET`: Clave secreta para JWT
- `JWT_EXPIRATION`: Tiempo de expiración del token

### Producción

Para desplegar en producción:

1. Configurar variables de entorno
2. Usar bases de datos externas
3. Configurar HTTPS
4. Implementar monitoreo y logging
5. Configurar balanceadores de carga

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Autores

- **Tu Nombre** - *Desarrollo inicial* - [TuGitHub](https://github.com/tuusuario)

## 🙏 Agradecimientos

- Spring Boot team por el excelente framework
- Docker team por la containerización
- PostgreSQL team por la base de datos
- Todos los contribuidores de las librerías utilizadas

---

**¡Disfruta construyendo tu red social! 🚀**
