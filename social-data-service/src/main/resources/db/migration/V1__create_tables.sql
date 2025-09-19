-- Creación del esquema y estructura de tablas para social-data-service
CREATE SCHEMA IF NOT EXISTS social AUTHORIZATION social_data_user;

CREATE TABLE IF NOT EXISTS social.users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    alias VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    date_of_birth DATE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS social.posts (
    id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    author_id BIGINT NOT NULL REFERENCES social.users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS social.likes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES social.users(id) ON DELETE CASCADE,
    post_id BIGINT NOT NULL REFERENCES social.posts(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uniq_user_post_like UNIQUE (user_id, post_id)
);