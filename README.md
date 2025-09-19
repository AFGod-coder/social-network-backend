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

## 🚀 Instalación

```bash
# Clonar el repositorio
git clone https://github.com/AFGod-coder/social-network-backend.git
cd social-network-backend

# Construir y ejecutar con Docker
./gradlew build
docker-compose up -d
```

## 🧪 Usuarios de Prueba

- **Usuario Admin**: 
  - Email: admin@example.com
  - Password: admin123

## 🔧 Funcionalidades

### Autenticación
- ✅ Login/Logout con JWT
- ✅ Registro de usuarios
- ✅ Refresh tokens

### Posts
- ✅ Crear publicaciones
- ✅ Feed de usuario (posts de otros usuarios)
- ✅ Sistema de likes/dislikes

### Usuarios
- ✅ Gestión de perfiles
- ✅ CRUD de usuarios

## 📊 API Endpoints

### BFF Service (Puerto 8084)
- `POST /api/v1/bff/auth/login` - Login
- `POST /api/v1/bff/auth/register` - Registro
- `GET /api/v1/bff/feed?userId={id}` - Feed de usuario
- `POST /api/v1/bff/posts` - Crear post
- `POST /api/v1/bff/posts/{id}/likes` - Dar like
- `DELETE /api/v1/bff/posts/{postId}/likes/{likeId}` - Quitar like

## 🚀 Despliegue

```bash
# Construir para producción
./gradlew build

# Ejecutar con Docker Compose
docker-compose up -d
```

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.