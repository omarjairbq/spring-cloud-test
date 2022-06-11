INSERT INTO usuarios (user_name, password, nombre, apellido, email, enabled) VALUES ('omarjbq', '$2a$10$ZXY3WSrUsdCx2..Uw49i1uNQ5TJy8juF.V3zEn8XBzQ/hQy1vdhWi', 'Omar', 'Balbuena', 'omarjbq@hotmail.com', true);
INSERT INTO usuarios (user_name, password, nombre, apellido, email, enabled) VALUES ('claudia.bc9', '$2a$10$5GUQhzYsnrWensxFu2mGeOSzN/U7.v8BFJFJXTvsJXDhvXC7P21Du', 'Claudia', 'Baez', 'claudia.bc9@hotmail.com', true);

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 2);

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);