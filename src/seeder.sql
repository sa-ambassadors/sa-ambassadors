
insert into role(name) values ('ROLE_USER');
insert into role(name) values ('USER');
insert into role(name) values ('ROLE_INTERN');
insert into role(name) values ('ROLE_EMPLOYER');
insert into role(name) values ('ROLE_ADMIN');
insert into role(name) values ('ROLE_ISAPPROVED');

insert into users(email, enabled, password, username, role_id) values ('admin@admin.com', 1,'$2a$10$bfnCu8US7n6iKB5PQzKMZOR1h7V70bWbcAF9iDsidtCJNwYUwn8.C', 'admin', 5)