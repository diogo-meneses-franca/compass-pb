INSERT INTO users(id, username, password, role) VALUES (100, "ana@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");
INSERT INTO users(id, username, password, role) VALUES (101, "bia@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_ADMIN");
INSERT INTO users(id, username, password, role) VALUES (102, "bob@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");

INSERT INTO clients(id, name, cpf, id_user) VALUES (11, 'Bianca Silva', '02797865227', 101);
INSERT INTO clients(id, name, cpf, id_user) VALUES (12, 'Bob Marley', '36228624407', 102);
