-- Agregar columna role a la tabla users en el esquema social
ALTER TABLE social.users ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER';

-- Actualizar usuarios existentes con el rol USER
UPDATE social.users SET role = 'USER' WHERE role IS NULL;
