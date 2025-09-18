INSERT INTO auth.users (email, password, first_name, last_name, alias, role)
VALUES (
           'admin@example.com',
           '$2a$10$7qW5G6HkXeXgP2IuFECVKuJ4HZT6C1soAhxG/nfw0kH5t2i0ZgjfO',
           'Admin',
           'User',
           'admin',
           'ADMIN'
       )
    ON CONFLICT (email) DO NOTHING;

INSERT INTO auth.users (email, password, first_name, last_name, alias, role)
VALUES
    ('frontend@gmail.com', '$2a$10$7qW5G6HkXeXgP2IuFECVKuJ4HZT6C1soAhxG/nfw0kH5t2i0ZgjfO', 'Frontend', 'User', 'frontend', 'USER')
    ON CONFLICT (email) DO NOTHING;
