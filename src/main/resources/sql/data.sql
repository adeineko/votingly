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



INSERT INTO application_user(email, first_name, password)
VALUES ('user@gmail.com', 'Maria', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2');


