-- Datos iniciales para social-data-service
-- Contraseña para todos los usuarios: "SocialNetwork2024!"
INSERT INTO social.users (first_name, last_name, alias, email, date_of_birth, role)
VALUES
    -- Administradores
    ('Admin', 'User', 'admin', 'admin@example.com', '1990-01-01', 'ADMIN'),
    ('Super', 'Admin', 'superadmin', 'superadmin@example.com', '1985-06-15', 'ADMIN'),
    ('Tech', 'Admin', 'techadmin', 'techadmin@example.com', '1987-09-22', 'ADMIN'),
    ('System', 'Admin', 'systemadmin', 'systemadmin@example.com', '1983-12-08', 'ADMIN'),
    
    -- Usuarios regulares
    ('Juan', 'Pérez', 'juanperez', 'juan@example.com', '1992-05-15', 'USER'),
    ('María', 'García', 'mariagarcia', 'maria@example.com', '1988-12-03', 'USER'),
    ('Carlos', 'López', 'carloslopez', 'carlos@example.com', '1995-08-22', 'USER'),
    ('Ana', 'Martínez', 'anamartinez', 'ana@example.com', '1991-03-10', 'USER')
ON CONFLICT (email) DO NOTHING;

-- Insertar publicaciones usando subconsultas para obtener los IDs de los usuarios
INSERT INTO social.posts (message, author_id)
VALUES
    -- Publicaciones de administradores
    ('¡Bienvenidos a nuestra red social! Espero que disfruten esta plataforma.', (SELECT id FROM social.users WHERE email = 'admin@example.com')),
    ('Como super administrador, estoy aquí para ayudar con cualquier problema técnico.', (SELECT id FROM social.users WHERE email = 'superadmin@example.com')),
    ('Manteniendo la infraestructura tecnológica al día. ¡Siempre aprendiendo!', (SELECT id FROM social.users WHERE email = 'techadmin@example.com')),
    ('Monitoreando el sistema 24/7 para garantizar la mejor experiencia.', (SELECT id FROM social.users WHERE email = 'systemadmin@example.com')),
    ('¿Cuál es su framework favorito para desarrollo web?', (SELECT id FROM social.users WHERE email = 'admin@example.com')),
    
    -- Publicaciones de usuarios regulares
    ('Mi primer post en la plataforma. ¡Qué emocionante!', (SELECT id FROM social.users WHERE email = 'juan@example.com')),
    ('Compartiendo mi experiencia aquí. La tecnología avanza muy rápido.', (SELECT id FROM social.users WHERE email = 'maria@example.com')),
    ('Hoy aprendí algo nuevo sobre programación. ¡Es increíble!', (SELECT id FROM social.users WHERE email = 'carlos@example.com')),
    ('Recomiendo este libro que estoy leyendo. Muy interesante.', (SELECT id FROM social.users WHERE email = 'ana@example.com')),
    ('¿Alguien más está trabajando en proyectos de Spring Boot?', (SELECT id FROM social.users WHERE email = 'juan@example.com')),
    ('Las bases de datos relacionales siguen siendo fundamentales.', (SELECT id FROM social.users WHERE email = 'maria@example.com')),
    ('Microservicios: una arquitectura fascinante pero compleja.', (SELECT id FROM social.users WHERE email = 'carlos@example.com')),
    ('Docker ha revolucionado la forma en que desplegamos aplicaciones.', (SELECT id FROM social.users WHERE email = 'ana@example.com')),
    ('¿Qué opinan sobre las nuevas tendencias en desarrollo backend?', (SELECT id FROM social.users WHERE email = 'superadmin@example.com')),
    ('Implementando nuevas funcionalidades de seguridad en la plataforma.', (SELECT id FROM social.users WHERE email = 'techadmin@example.com'))
ON CONFLICT DO NOTHING;

-- Insertar likes usando subconsultas para obtener IDs de usuarios y posts
INSERT INTO social.likes (user_id, post_id)
VALUES
    -- Likes para posts de administradores
    -- Post de bienvenida (admin)
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Bienvenidos%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Bienvenidos%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Bienvenidos%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Bienvenidos%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Bienvenidos%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Bienvenidos%')),
    ((SELECT id FROM social.users WHERE email = 'ana@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Bienvenidos%')),
    
    -- Post de super admin
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%super administrador%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%super administrador%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%super administrador%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%super administrador%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%super administrador%')),
    
    -- Post de tech admin
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%infraestructura tecnológica%')),
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%infraestructura tecnológica%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%infraestructura tecnológica%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%infraestructura tecnológica%')),
    ((SELECT id FROM social.users WHERE email = 'ana@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%infraestructura tecnológica%')),
    
    -- Post de system admin
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Monitoreando el sistema%')),
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Monitoreando el sistema%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Monitoreando el sistema%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Monitoreando el sistema%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Monitoreando el sistema%')),
    
    -- Likes para posts de usuarios regulares
    -- Post de Juan (primer post)
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%primer post%')),
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%primer post%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%primer post%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%primer post%')),
    
    -- Post de María (experiencia)
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%experiencia aquí%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%experiencia aquí%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%experiencia aquí%')),
    ((SELECT id FROM social.users WHERE email = 'ana@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%experiencia aquí%')),
    
    -- Post de Carlos (programación)
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%programación%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%programación%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%programación%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%programación%')),
    
    -- Post de Ana (libro)
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%libro%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%libro%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%libro%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%libro%')),
    
    -- Post de Juan (Spring Boot)
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Spring Boot%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Spring Boot%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Spring Boot%')),
    ((SELECT id FROM social.users WHERE email = 'ana@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Spring Boot%')),
    
    -- Post de María (bases de datos)
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%bases de datos%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%bases de datos%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%bases de datos%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%bases de datos%')),
    
    -- Post de Carlos (microservicios)
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Microservicios%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Microservicios%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Microservicios%')),
    ((SELECT id FROM social.users WHERE email = 'ana@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Microservicios%')),
    
    -- Post de Ana (Docker)
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Docker%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Docker%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Docker%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%Docker%')),
    
    -- Post de super admin (tendencias backend)
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%tendencias en desarrollo backend%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%tendencias en desarrollo backend%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%tendencias en desarrollo backend%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%tendencias en desarrollo backend%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%tendencias en desarrollo backend%')),
    
    -- Post de tech admin (seguridad)
    ((SELECT id FROM social.users WHERE email = 'admin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%funcionalidades de seguridad%')),
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%funcionalidades de seguridad%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%funcionalidades de seguridad%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%funcionalidades de seguridad%')),
    ((SELECT id FROM social.users WHERE email = 'ana@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%funcionalidades de seguridad%')),
    
    -- Post de admin (framework favorito)
    ((SELECT id FROM social.users WHERE email = 'superadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%framework favorito%')),
    ((SELECT id FROM social.users WHERE email = 'techadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%framework favorito%')),
    ((SELECT id FROM social.users WHERE email = 'systemadmin@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%framework favorito%')),
    ((SELECT id FROM social.users WHERE email = 'juan@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%framework favorito%')),
    ((SELECT id FROM social.users WHERE email = 'maria@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%framework favorito%')),
    ((SELECT id FROM social.users WHERE email = 'carlos@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%framework favorito%')),
    ((SELECT id FROM social.users WHERE email = 'ana@example.com'), (SELECT id FROM social.posts WHERE message LIKE '%framework favorito%'))
ON CONFLICT (user_id, post_id) DO NOTHING;