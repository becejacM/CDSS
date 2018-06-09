
-- admin
-- CDSS421054-sbnz => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (1, 'admin', '$2a$10$H0bjPW8lFs9VSaBrySMp4e/ht1MhrtfLWenraISxFBp2.Iofd6yuC', 'admin@mailinator.com', 'admin', 'admin', 1, 'ADMIN');
-- Operator-1-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (2, 'doctor1', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'doctor1@mailinator.com', 'Milana', 'Becejac', 1, 'DOCTOR');
-- Operator-2-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (3, 'doctor2', '$2a$10$rRpzJm1LFO73jGfdqE4cJeW5wcpUzN1XItUvoHJlx6WyR9D0zjMuW', 'doctor2@mailinator.com', 'Nada', 'Macura', 1, 'DOCTOR');
  

insert into privilege(id, name) value (1, 'READ_PRIVILEGE');
insert into privilege(id, name) value (2, 'WRITE_PRIVILEGE');
insert into privilege(id, name) value (3, 'CHANGE_PASSWORD_PRIVILEGE');

insert into role(id, name) value(1, 'ADMIN');
insert into role(id, name) value(2, 'OPERATOR');

insert into roles_privileges(role_id, privilege_id) value (1,1);
insert into roles_privileges(role_id, privilege_id) value (1,2);
insert into roles_privileges(role_id, privilege_id) value (1,3);
insert into roles_privileges(role_id, privilege_id) value (2,1);
insert into roles_privileges(role_id, privilege_id) value (2,3);

insert into users_roles(user_id, role_id) value (1, 1);
insert into users_roles(user_id, role_id) value (2, 2);
insert into users_roles(user_id, role_id) value (3, 2);