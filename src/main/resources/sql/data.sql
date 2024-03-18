INSERT INTO survey(survey_name, survey_type)
VALUES ('The Tartar Steppe', 'CIRCULAR');
INSERT INTO survey(survey_name, survey_type)
VALUES ('Poem Strip', 'CIRCULAR');
INSERT INTO survey(survey_name, survey_type)
VALUES ('Restless Nights: Selected Stories of Dino Buzzati', 'CIRCULAR');

INSERT INTO question(question_name, question_type, survey_id)
VALUES ('What is your favorite color?', 'OPEN', '1');
INSERT INTO question(question_name, question_type, survey_id)
VALUES ('What is your favorite food?', 'OPEN', '2');



INSERT INTO user_table(first_name, last_name, email, password, user_type)
VALUES ('user', 'user', 'user@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'REGULAR');

INSERT INTO user_table(first_name, last_name, email, password, user_type, notes_taken)
VALUES ('supervisor', 'supervisor', 'supervisor@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'SUPERVISOR', 'Please resolve the issue in survey');

INSERT INTO user_table(first_name, last_name, org_id, email, password, user_type)
VALUES ('org', 'org', '1', 'org@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'ORG_ADMIN');

INSERT INTO user_table(first_name, last_name, email, password, user_type)
VALUES ('admin', 'admin', 'admin@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'P_ADMIN');
