-- Datos iniciales para auth-service
-- Contraseña para todos los usuarios: "contraseña123*"
INSERT INTO auth.users (email, password, first_name, last_name, alias, role, date_of_birth)
VALUES 
    -- Administradores
    ('admin@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'Admin', 'User', 'admin', 'ADMIN', '1990-01-01'),
    ('superadmin@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'Super', 'Admin', 'superadmin', 'ADMIN', '1985-06-15'),
    ('techadmin@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'Tech', 'Admin', 'techadmin', 'ADMIN', '1987-09-22'),
    ('systemadmin@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'System', 'Admin', 'systemadmin', 'ADMIN', '1983-12-08'),
    
    -- Usuarios regulares
    ('juan@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'Juan', 'Pérez', 'juanperez', 'USER', '1992-05-15'),
    ('maria@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'María', 'García', 'mariagarcia', 'USER', '1988-12-03'),
    ('carlos@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'Carlos', 'López', 'carloslopez', 'USER', '1995-08-22'),
    ('ana@example.com', '$2a$11$8eNvJwJ4EhoU6.EQq84FZuOAEg/b7mP7CTjYYqljOn6u15EVd2sUK', 'Ana', 'Martínez', 'anamartinez', 'USER', '1991-03-10')
ON CONFLICT (email) DO NOTHING;