-- Agregar columna date_of_birth a la tabla users
ALTER TABLE users ADD COLUMN date_of_birth DATE;

-- Actualizar usuarios existentes con una fecha de nacimiento por defecto (opcional)
-- UPDATE users SET date_of_birth = '1990-01-01' WHERE date_of_birth IS NULL;
