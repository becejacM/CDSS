
-- admin
-- CDSS421054-sbnz => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (1, 'admin', '$2a$10$H0bjPW8lFs9VSaBrySMp4e/ht1MhrtfLWenraISxFBp2.Iofd6yuC', 'admin@mailinator.com', 'admin', 'admin', 1, 'ADMIN');
-- CDSS421054-sbnz => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (2, 'doctor1', '$2a$10$rRpzJm1LFO73jGfdqE4cJeW5wcpUzN1XItUvoHJlx6WyR9D0zjMuW', 'doctor1@mailinator.com', 'Milana', 'Becejac', 1, 'DOCTOR');
-- Operator-2-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (3, 'doctor2', '$2a$10$rRpzJm1LFO73jGfdqE4cJeW5wcpUzN1XItUvoHJlx6WyR9D0zjMuW', 'doctor2@mailinator.com', 'Nada', 'Macura', 1, 'DOCTOR');
-- Operator-2-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (4, 'doctor3', '$2a$10$rRpzJm1LFO73jGfdqE4cJeW5wcpUzN1XItUvoHJlx6WyR9D0zjMuW', 'doctor3@mailinator.com', 'DR', 'House', 1, 'DOCTOR'); 

insert into privilege(id, name) value (1, 'READ_PRIVILEGE');
insert into privilege(id, name) value (2, 'WRITE_PRIVILEGE');
insert into privilege(id, name) value (3, 'CHANGE_PASSWORD_PRIVILEGE');

insert into role(id, name) value(1, 'ADMIN');
insert into role(id, name) value(2, 'DOCTOR');

insert into roles_privileges(role_id, privilege_id) value (1,1);
insert into roles_privileges(role_id, privilege_id) value (1,2);
insert into roles_privileges(role_id, privilege_id) value (1,3);
insert into roles_privileges(role_id, privilege_id) value (2,1);
insert into roles_privileges(role_id, privilege_id) value (2,3);

insert into users_roles(user_id, role_id) value (1, 1);
insert into users_roles(user_id, role_id) value (2, 2);
insert into users_roles(user_id, role_id) value (3, 2);
insert into users_roles(user_id, role_id) value (4, 2);


--- dodavanje pacijenata
insert into patient(id, email, firstname, lastname, address, medical_card_number) values (1, 'pacijent1@mailinator.com', 'pacijent1', 'pacijentic1', 'adresa 1', '111111');
insert into patient(id, email, firstname, lastname, address, medical_card_number) values (2, 'pacijent2@mailinator.com', 'pacijent2', 'pacijentic2', 'adresa 2', '222222');
insert into patient(id, email, firstname, lastname, address, medical_card_number) values (3, 'pacijent3@mailinator.com', 'pacijent3', 'pacijentic3', 'adresa 3', '333333');

--- dodavanje medicinskog dokumenta pacijentu
insert into medical_record(id, patient_id) values (1,1);
insert into medical_record(id, patient_id) values (2,2);
insert into medical_record(id, patient_id) values (3,3);
--- dodavanje lekova
insert into medicine(id, name, type_of_medicine) values (1, 'antibiotik1', 'ANTIBIOTIC');
insert into medicine(id, name, type_of_medicine) values (2, 'antibiotik2', 'ANTIBIOTIC');
insert into medicine(id, name, type_of_medicine) values (3, 'antibiotik3', 'ANTIBIOTIC');
insert into medicine(id, name, type_of_medicine) values (4, 'painkiller1', 'PAINKILLER');
insert into medicine(id, name, type_of_medicine) values (5, 'painkiller2', 'PAINKILLER');
insert into medicine(id, name, type_of_medicine) values (6, 'painkiller3', 'PAINKILLER');
insert into medicine(id, name, type_of_medicine) values (7, 'other1', 'OTHER');
insert into medicine(id, name, type_of_medicine) values (8, 'other2', 'OTHER');
insert into medicine(id, name, type_of_medicine) values (9, 'other3', 'OTHER');

--- dodavanje sastojaka lekova
insert into medicine_ingredient(id, name) values (1, 'sastojak 1');
insert into medicine_ingredient(id, name) values (2, 'sastojak 2');
insert into medicine_ingredient(id, name) values (3, 'sastojak 3');
insert into medicine_ingredient(id, name) values (4, 'sastojak 4');
insert into medicine_ingredient(id, name) values (5, 'sastojak 5');

--- povezivanje lekova sa sastojcima
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (1, 1);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (1, 4);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (1, 5);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (4, 1);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (4, 4);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (4, 5);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (7, 1);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (7, 4);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (7, 5);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (2, 2);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (3, 3);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (5, 2);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (6, 3);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (8, 2);
insert into medicine_ingredient_table(medicine_id, medicine_ingredient_id) values (9, 3);

--- dodavanje alergija na lekove 
insert into medicine_alergies_table(medical_record_id, medicine_id) values (1,1);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (1,4);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (1,7);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (2,2);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (2,5);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (2,8);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (3,3);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (3,6);
insert into medicine_alergies_table(medical_record_id, medicine_id) values (3,9);

--- dodavanje alergija na sastojke 
insert into ingredient_alergies_table(medical_record_id, ingredient_id) values (1,1);
insert into ingredient_alergies_table(medical_record_id, ingredient_id) values (2,2);
insert into ingredient_alergies_table(medical_record_id, ingredient_id) values (3,3);

--- dodavanje simptoma
insert into symptom(id, name) values (1, 'Curenje iz nosa');
insert into symptom(id, name) values (2, 'Bol u grlu');
insert into symptom(id, name) values (3, 'Glavobolja');
insert into symptom(id, name) values (4, 'Kijanje');
insert into symptom(id, name) values (5, 'Kasalj');
insert into symptom(id, name) values (6, 'Temperatura veca od 38 stepeni');
insert into symptom(id, name) values (7, 'Drhtavica');
insert into symptom(id, name) values (8, 'Bol koji se siri do usiju');
insert into symptom(id, name) values (9, 'Temperatura veca od 40 do 41 stepeni');
insert into symptom(id, name) values (10, 'Gubitak apetita');
insert into symptom(id, name) values (11, 'Umor');
insert into symptom(id, name) values (12, 'Zuti sekret iz nosa');
insert into symptom(id, name) values (13, 'Oticanje oko ociju');
insert into symptom(id, name) values (14, 'Cesto uriniranje');
insert into symptom(id, name) values (15, 'Gubitak telesne tezine');
insert into symptom(id, name) values (16, 'Mucnina i povracanje');
insert into symptom(id, name) values (17, 'Nocturia');
insert into symptom(id, name) values (18, 'Otoci nogu i zglobova');
insert into symptom(id, name) values (19, 'Gusenje');
insert into symptom(id, name) values (20, 'Bol u grudima');
insert into symptom(id, name) values (21, 'Dijareja');
insert into symptom(id, name) values (22, 'Visok pritisak');
insert into symptom(id, name) values (23, 'Prehlada ili groznica u poslednjih 60 dana');
insert into symptom(id, name) values (24, 'Visok pritisak bar 10 puta u poslednjih 6 meseci');
insert into symptom(id, name) values (25, 'Pacijent boluje od hipertenzije vise od 6 meseci');
insert into symptom(id, name) values (26, 'Pacijent boluje od dijabetesa');
insert into symptom(id, name) values (27, 'Oporavlja se od operacije');
insert into symptom(id, name) values (28, 'U poslednjih 14 dana dijagnostikovana bolest koja kao simptom ima povisenu telesnu temperaturu');
insert into symptom(id, name) values (29, 'U poslednjih 21 dan dijagnostikovana bolest za koju je primao antibiotike');


--- dodavanje bolesti prve grupe
--- dodavanje prehlade i njenih simptoma
insert into disease(id, name, type_of_disease) values (1, 'Prehlada', 'FIRST_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (1, 'GENERAL', 1, 1);	---curenje nosa
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (2, 'GENERAL', 1, 2);	---bol u grlu
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (3, 'GENERAL', 1, 3);	---glavobolja
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (4, 'GENERAL', 1, 4);	---kijanje
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (5, 'GENERAL', 1, 5);	---kasalj


--- dodavanje groznice i njenih simptoma
insert into disease(id, name, type_of_disease) values (2, 'Groznica', 'FIRST_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (6, 'GENERAL', 2, 4);	---kijanje
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (7, 'GENERAL', 2, 2);	---bol u grlu
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (8, 'GENERAL', 2, 5);	---kasalj
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (9, 'GENERAL', 2, 6);	---temp 38
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (10, 'GENERAL', 2, 1);	---curenje nosa
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (11, 'GENERAL', 2, 3);	---glavobolja
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (12, 'GENERAL', 2, 7);	---drhtavica


--- dodavanje upala krajnika i njenih simptoma
insert into disease(id, name, type_of_disease) values (3, 'Upala krajnika', 'FIRST_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (13, 'GENERAL', 3, 2);	---bol u grlu
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (14, 'GENERAL', 3, 8);	---bol do usiju
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (15, 'GENERAL', 3, 3);	---glavobolja
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (16, 'GENERAL', 3, 9);	---temp 40 41
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (17, 'GENERAL', 3, 7);	---drhtavica
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (18, 'GENERAL', 3, 10);	---gubi apetit
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (19, 'GENERAL', 3, 7);	---drhtavica
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (20, 'GENERAL', 3, 11);	---umor
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (21, 'GENERAL', 3, 12);	---sekret iz nosa

--- dodavanje sinusna infekcija i njenih simptoma
insert into disease(id, name, type_of_disease) values (4, 'Sinusna infekcija', 'FIRST_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (22, 'GENERAL', 4, 13);	---oticanje oko ociju
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (23, 'GENERAL', 4, 3);	---glavobolja
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (24, 'GENERAL', 4, 12);	---sekret iz nosa
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (25, 'GENERAL', 4, 2);	---bol u grlu
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (26, 'GENERAL', 4, 6);	---temp 38
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (27, 'GENERAL', 4, 5);	---kasalj
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (41, 'SPECIFIC', 4, 23);---specificni zahtev

--- dodavanje bolesti druge grupe
--- dodavanje hipertenzije i njenih simptoma
insert into disease(id, name, type_of_disease) values (5, 'Hipertenzija', 'SECOND_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (42, 'SPECIFIC', 5, 24);---specificni zahtev
--- dodavanje dijabetesa i njenih simptoma
insert into disease(id, name, type_of_disease) values (6, 'Dijabetes', 'SECOND_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (28, 'GENERAL', 6, 14);	---cesto uriniranje
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (29, 'GENERAL', 6, 15);	---gubitak tezine
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (30, 'GENERAL', 6, 11);	---umor
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (31, 'GENERAL', 6, 16);	---mucnina i povracanje

--- dodavanje bolesti trece grupe
--- dodavanje hronicna bubrezna bolest i njenih simptoma
insert into disease(id, name, type_of_disease) values (7, 'Hronicna bubrezna bolest', 'THIRD_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (32, 'GENERAL', 7, 11);	---umor
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (33, 'GENERAL', 7, 17);	---nocturia
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (34, 'GENERAL', 7, 18);	---otoci nogu i zglobova
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (35, 'GENERAL', 7, 19);	---gusenje
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (36, 'GENERAL', 7, 20);	---bol u grudima
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (43, 'SPECIFIC', 7, 25);---specificni zahtev
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (44, 'SPECIFIC', 7, 26);---specificni zahtev

--- dodavanje akutna bubrezna povreda i njenih simptoma
insert into disease(id, name, type_of_disease) values (8, 'Akutna bubrezna povreda', 'THIRD_GROUP');
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (37, 'GENERAL', 8, 11);	---umor
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (38, 'GENERAL', 8, 19);	---gusenje
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (39, 'GENERAL', 8, 18);	---otoci nogu i zglobova
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (40, 'GENERAL', 8, 21);	---dijareja
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (45, 'SPECIFIC', 8, 27);---specificni zahtev
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (46, 'SPECIFIC', 8, 28);---specificni zahtev
insert into symptom_for_disease(id, type_of_symptom, disease_id, symptom_id) values (47, 'SPECIFIC', 8, 29);---specificni zahtev

insert into diagnostic_therapy(id, date, disease_id, medical_record_id) values (1, '2017-06-18 01:03:58',1, 1); --- dodata pacijentu 1 prehlada(on je alergican na 1 4 7 lekove i sastojak 1)
insert into therapy_symptoms(therapy_id, symptom_id) values (1, 1);					--- moraju biti 4 simptoma zadovoljena za 1.grupu bolesti
insert into therapy_symptoms(therapy_id, symptom_id) values (1, 2);
insert into therapy_symptoms(therapy_id, symptom_id) values (1, 3);
insert into therapy_symptoms(therapy_id, symptom_id) values (1, 4);
--insert into therapy_medicines(therapy_id, medicine_id) values (1, 2);
--insert into therapy_medicines(therapy_id, medicine_id) values (1, 5);
--insert into therapy_medicines(therapy_id, medicine_id) values (1, 8);
insert into medicine_for_therapy(id,therapy_id, medicine_id, doctor_id) values (1, 1, 2, 2);
insert into medicine_for_therapy(id,therapy_id, medicine_id, doctor_id) values (2, 1, 5, 2);
insert into medicine_for_therapy(id,therapy_id, medicine_id, doctor_id) values (3, 1, 8, 2);
