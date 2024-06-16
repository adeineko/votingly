INSERT INTO survey(survey_name, survey_type)
VALUES ('Environmental Issues', 'CIRCULAR'),
       ('Internet Usage', 'CIRCULAR'),
       ('Safety Issues', 'CIRCULAR');

/*Survey 1*/
INSERT INTO question(question_name, question_type, survey_id)
VALUES ('What environmental groups are you currently in?', 'OPEN', '1');
INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('How often do you recycle?', 'CHOICE', '1', false),
       ('Are you concerned about global warming?', 'CHOICE', '1', false);
INSERT INTO question(question_name, question_type, survey_id, min, max, step)
VALUES ('How concerned are you about saving endangered species?', 'RANGE', '1', 0, 10, 1);
INSERT INTO question(question_name, question_type, survey_id)
VALUES ('What can be done to improve the environmental laws in your country?',
        'OPEN', '1');

/*Survey 2*/
INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('How many hours do you spend on the internet per session?', 'CHOICE', '2', false),
       ('Where do you use the internet? Check all that apply.', 'CHOICE', '2', true);
INSERT INTO question(question_name, question_type, survey_id)
VALUES ('What sites do you visit most frequently?', 'OPEN', '2'),
       ('What do you like most about the internet?', 'OPEN', '2');
INSERT INTO question(question_name, question_type, survey_id, min, max, step)
VALUES ('How would you rate the importance of implementing measures to ensure internet safety and responsible usage in today''s digital age?',
        'RANGE', '2', 0, 10, 1);

/*Survey 3*/
INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('How safe do you feel in your country?', 'CHOICE', '3', false);

INSERT INTO question(question_name, question_type, survey_id)
VALUES ('Mention any law(s) you are uncomfortable with in your country.', 'OPEN', '3'),
       ('Could your country do anything to help you feel safer', 'OPEN', '3'),
       ('Mention any law(s) you would like instituted in your country.', 'OPEN', '3');
/*Options*/
INSERT INTO option(option_text, question)
VALUES ('Never', '2'),
       ('Monthly', '2'),
       ('Daily', '2'),
       ('Yes', '3'),
       ('No', '3'),
       ('Unsure', '3'),
       ('1 - 3 hours', '6'),
       ('6 - 7 hours', '6'),
       ('> 7 hours', '6'),
       ('School', '7'),
       ('Library', '7'),
       ('School', '7'),
       ('Phone', '7'),
       ('Not at all', '11'),
       ('Moderately', '11'),
       ('Extremely', '11');

--  All user passwords: 'maria'
INSERT INTO user_table(first_name, last_name, email, password, user_type)
VALUES ('user', 'user', 'user@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'REGULAR');

INSERT INTO user_table(first_name, last_name, email, password, user_type)
VALUES ('supervisor', 'supervisor', 'supervisor@gmail.com',
        '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'SUPERVISOR');

INSERT INTO user_table(first_name, last_name, org_id, email, password, user_type)
VALUES ('org', 'org', '1', 'org@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2',
        'ORG_ADMIN');

INSERT INTO user_table(first_name, last_name, email, password, user_type)
VALUES ('admin', 'admin', 'admin@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'P_ADMIN');

-- Insert dummy answers for Survey 2
INSERT INTO Answer (answer_id, survey_id, user_id, question_id, answer, answer_type, answer_time)
VALUES (6, 2, 1, 1, 'Option A', 'OPEN', '2024-06-10 10:15:00'),
       (7, 2, 1, 1, 'Option B', 'OPEN', '2024-06-10 10:45:00'),
       (8, 2, 1, 3, 'Option C', 'OPEN', '2024-06-10 11:15:00'),
       (9, 2, 1, 4, 'Option D', 'OPEN', '2024-06-11 11:45:00'),
       (10, 2, 1, 2, 'Option E', 'OPEN', '2024-06-11 12:15:00'),
       (11, 2, 1, 1, 'Option B', 'OPEN', '2024-06-11 10:45:00'),
       (12, 2, 1, 3, 'Option C', 'OPEN', '2024-06-11 11:15:00'),
       (13, 2, 1, 4, 'Option D', 'OPEN', '2024-06-12 11:45:00'),
       (14, 2, 1, 1, 'Option B', 'OPEN', '2024-06-12 10:45:00'),
       (15, 2, 1, 3, 'Option C', 'OPEN', '2024-06-12 11:15:00'),
       (16, 2, 1, 4, 'Option D', 'OPEN', '2024-06-12 11:45:00'),
       (17, 2, 1, 4, 'Option D', 'OPEN', '2024-06-12 11:45:00'),
       (18, 2, 1, 4, 'Option D', 'OPEN', '2024-06-12 11:45:00'),
       (19, 2, 1, 4, 'Option D', 'OPEN', '2024-06-13 11:45:00'),
       (20, 2, 1, 4, 'Option D', 'OPEN', '2024-06-13 12:45:00');








