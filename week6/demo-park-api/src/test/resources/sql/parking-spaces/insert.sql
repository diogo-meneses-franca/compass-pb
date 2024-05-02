INSERT INTO users(id, username, password, role) VALUES (100, "ana@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_ADMIN");
INSERT INTO users(id, username, password, role) VALUES (102, "bob@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");
INSERT INTO users(id, username, password, role) VALUES (101, "bia@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");

INSERT INTO parking_spaces(id, code, status) VALUES(12, "A-02", "FREE");
INSERT INTO parking_spaces(id, code, status) VALUES(13, "A-03", "FREE");
INSERT INTO parking_spaces(id, code, status) VALUES(14, "A-04", "OCCUPIED");
INSERT INTO parking_spaces(id, code, status) VALUES(15, "A-05", "FREE");