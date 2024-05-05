INSERT INTO users(id, username, password, role) VALUES (100, "ana@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_ADMIN");
INSERT INTO users(id, username, password, role) VALUES (101, "bia@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");
INSERT INTO users(id, username, password, role) VALUES (102, "bob@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");

INSERT INTO clients(id, name, cpf, id_user) VALUES (11, 'Ana Maria', '36228624407', 100);
INSERT INTO clients(id, name, cpf, id_user) VALUES (12, 'Bianca Silva', '02797865227', 101);
INSERT INTO clients(id, name, cpf, id_user) VALUES (13, 'Bob Marley', '38153663216', 102);

INSERT INTO parking_spaces(id, code, status) VALUES(12, "A-02", "OCCUPIED");
INSERT INTO parking_spaces(id, code, status) VALUES(13, "A-03", "OCCUPIED");
INSERT INTO parking_spaces(id, code, status) VALUES(14, "A-04", "OCCUPIED");
INSERT INTO parking_spaces(id, code, status) VALUES(15, "A-05", "FREE");
INSERT INTO parking_spaces(id, code, status) VALUES(16, "A-06", "FREE");

INSERT INTO clients_parking_spaces(invoice_number, plate, brand, model, color, checkin, id_client, id_parking_space) VALUES('20230313-101300', 'ACB-0100', 'FIAT', 'Uno Mille', 'Black', '2023-03-13 10:13:00', 11, 12);
INSERT INTO clients_parking_spaces(invoice_number, plate, brand, model, color, checkin, id_client, id_parking_space) VALUES('20230313-101400', 'ACB-0200', 'FIAT', 'Toro', 'Black', '2023-03-13 10:14:00', 11, 13);
INSERT INTO clients_parking_spaces(invoice_number, plate, brand, model, color, checkin, id_client, id_parking_space) VALUES('20230313-101500', 'ACB-0300', 'FIAT', 'Bravo', 'Black', '2023-03-13 10:15:00', 11, 14);