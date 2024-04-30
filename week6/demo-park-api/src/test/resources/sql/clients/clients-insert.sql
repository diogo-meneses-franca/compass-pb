insert into USERS(id, username, password, role) values (100, "ana@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");
insert into USERS(id, username, password, role) values (101, "bia@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_ADMIN");
insert into USERS(id, username, password, role) values (102, "bob@email.com", "$2a$12$IRl5qrAh01smKzIbajP5Cu9TYEeGqmioAO.S8SxFx/wBZZjKLw/wS", "ROLE_CLIENT");

insert into CLIENTS(id, name, cpf, id_user) values (11, 'Bianca Silva', '02797865227', 101);
insert into CLIENTS(id, name, cpf, id_user) values (12, 'Bob Marley', '36228624407', 102);
