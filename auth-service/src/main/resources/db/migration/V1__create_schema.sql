-- Creación del esquema y estructura de tablas para auth-service
CREATE SCHEMA IF NOT EXISTS auth AUTHORIZATION auth_user;

CREATE TABLE IF NOT EXISTS auth.users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    alias VARCHAR(100) UNIQUE,
    role VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS auth.refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    expiry TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);