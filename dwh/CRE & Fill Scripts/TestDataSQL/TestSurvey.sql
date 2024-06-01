-- Elle's additional test data 4 the datawarehouse
-- Test survey 4 and 5
INSERT INTO survey(survey_name, survey_type)
VALUES ('Local Elections', 'LINEAR'),
       ('Local Environment Improvement', 'LINEAR');

-- Test Users 5 - 12
INSERT INTO user_table(first_name, last_name, email, password, user_type)
VALUES ('Ross', 'Ross', 'ross@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'REGULAR'),
       ('Anna', 'Anna', 'anna@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'REGULAR'),
       ('Sofiia', 'Sofiia', 'soffia@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2',
        'REGULAR'),
       ('Nathan', 'Nathan', 'nathan@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2',
        'REGULAR'),
       ('Elle', 'Elle', 'elle@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'REGULAR'),
       ('John', 'John', 'john@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'REGULAR'),
       ('Rachel', 'Rachel', 'rachel@gmail.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2',
        'REGULAR'),
       ('Megan', 'Megan', 'megan@gamil.com', '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2', 'REGULAR');

-- Test questions for survey 4
INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('How important are local elections to you?', 'CHOICE', '4', false),
       ('How often do you vote in local elections?', 'CHOICE', '4', false);

INSERT INTO question(question_name, question_type, survey_id)
VALUES ('What is the most important issue to you in local elections?', 'OPEN', '4');

INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('What can be done to improve local elections?', 'CHOICE', '4', true);

INSERT INTO question(question_name, question_type, survey_id, min, max, step)
VALUES ('How would you rate the importance of local elections in your country?', 'RANGE', '4', 0, 10, 1);

INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('Which of the following issues do you believe should be the top priority for candidates running in the upcoming local elections? (Select all that apply)',
        'CHOICE', '4', true);

INSERT INTO question(question_name, question_type, survey_id)
VALUES ('In your opinion, what qualities do you think are most important for a candidate running for local office to possess?',
        'OPEN', '4');

INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('Which level of local government do you feel has the most impact on your daily life?', 'CHOICE', '4', true),
       ('How satisfied are you with the current performance of local government in addressing community needs and concerns?',
        'CHOICE', '4', false);

INSERT INTO question(question_name, question_type, survey_id, min, max, step)
VALUES ('On a scale of 1 to 10, how informed do you feel about the candidates running for local office in the upcoming elections?',
        'RANGE','4', 0, 10, 1);

-- Test options for survey 4
INSERT INTO option(option_text, question)
VALUES ('Not important', '15'),
       ('Somewhat important', '15'),
       ('Very important', '15'),
       ('Never', '16'),
       ('Rarely', '16'),
       ('Sometimes', '16'),
       ('Often', '16'),
       ('More transparency', '18'),
       ('More candidates', '18'),
       ('More information on candidates', '18'),
       ('More information on issues', '18'),
       ('More information on voting process', '18'),
       ('Education', '20'),
       ('Healthcare', '20'),
       ('Economic development', '20'),
       ('Public safety', '20'),
       ('City/town council', '22'),
       ('County government', '22'),
       ('School board', '22'),
       ('Very satisfied', '23'),
       ('Satisfied', '23'),
       ('Neutral', '23'),
       ('Dissatisfied', '23'),
       ('Very dissatisfied', '23');

-- Link options to questions
INSERT INTO question_options(choice_question_id, options_option_id)
VALUES ('15', '17'),
       ('15', '18'),
       ('15', '19'),
       ('16', '20'),
       ('16', '21'),
       ('16', '22'),
       ('16', '23'),
       ('18', '24'),
       ('18', '25'),
       ('18', '26'),
       ('18', '27'),
       ('18', '28'),
       ('20', '29'),
       ('20', '30'),
       ('20', '31'),
       ('20', '32'),
       ('22', '33'),
       ('22', '34'),
       ('22', '35'),
       ('23', '36'),
       ('23', '37'),
       ('23', '38'),
       ('23', '39'),
       ('23', '40');


-- Test questions for survey 5
INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('How important is the local environment to you?', 'CHOICE', '5', false),
       ('How often do you participate in local environment improvement activities?', 'CHOICE', '5', false);

INSERT INTO question(question_name, question_type, survey_id)
VALUES ('What is the most important issue to you in local environment improvement?', 'OPEN', '5');

INSERT INTO question(question_name, question_type, survey_id, min, max, step)
VALUES ('How would you rate the importance of local environment improvement in your country?', 'RANGE', '5', 0, 10, 1),
       ('On a scale of 1 to 10, how would you rate the level of cleanliness of public spaces (e.g., parks, streets) in our local area?',
        'RANGE', '5', 0, 10, 1),
       ('Please rate your level of satisfaction with the accessibility and availability of recycling facilities and programs in our community, with 1 being "Not Satisfied" and 10 being "Very Satisfied".',
        'RANGE', '5', 0, 10, 1),
       ('How would you rate the effectiveness of local government communication and engagement regarding environmental issues and initiatives, with 1 being "Not Effective" and 10 being "Extremely Effective"?',
        'RANGE', '5', 0, 10, 1);

INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('Which of the following environmental conservation efforts do you believe would have the most significant positive impact on our local community?',
        'CHOICE', '5', true);

-- Test options for survey 5
INSERT INTO option(option_text, question)
VALUES ('Not important', '25'),
       ('Somewhat important', '25'),
       ('Very important', '25'),
       ('Never', '26'),
       ('Rarely', '26'),
       ('Sometimes', '26'),
       ('Often', '26'),
       ('More recycling programs', '32'),
       ('More community clean-up events', '32'),
       ('More tree planting initiatives', '32'),
       ('More public education campaigns', '32'),
       ('More community gardens', '32');

-- Link options to questions
INSERT INTO question_options(choice_question_id, options_option_id)
VALUES ('25', '41'),
       ('25', '42'),
       ('25', '43'),
       ('26', '44'),
       ('26', '45'),
       ('26', '46'),
       ('26', '47'),
       ('32', '48'),
       ('32', '49'),
       ('32', '50'),
       ('32', '51'),
       ('32', '52');

-- NOTES
-- Skipped answers = count answers for survey 4 = 18 and count questions * count userid to see how many are skipped

-- ADDITIONAL FUN QUESTIONS
-- Does the type of question correlate to how many times it gets skipped? NOT WITH OUR STAR MODEL

-- Create survey 6
INSERT INTO survey(survey_name, survey_type)
VALUES ('Fun Questions', 'LINEAR');

-- Create questions for survey 6
INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('What is your favorite color?', 'CHOICE', '6', false),
       ('What is your favorite animal?', 'CHOICE', '6', false),
       ('What is your favorite food?', 'CHOICE', '6', false),
       ('What is your favorite movie genre?', 'CHOICE', '6', false),
       ('What is your favorite season?', 'CHOICE', '6', false),
       ('What is your favorite holiday?', 'CHOICE', '6', false),
       ('What is your favorite sport?', 'CHOICE', '6', false),
       ('What is your favorite hobby?', 'CHOICE', '6', false),
       ('What is your favorite book genre?', 'CHOICE', '6', false),
       ('What is your favorite music genre?', 'CHOICE', '6', false),
       ('What is your favorite book?', 'CHOICE', '6', false),
       ('What is your favorite travel destination?', 'CHOICE', '6', false);

-- Create options for survey 6
INSERT INTO option(option_text, question)
VALUES ('Red', '33'),
       ('Blue', '33'),
       ('Green', '33'),
       ('Yellow', '33'),
       ('Dog', '34'),
       ('Cat', '34'),
       ('Bird', '34'),
       ('Fish', '34'),
       ('Pizza', '35'),
       ('Burger', '35'),
       ('Pasta', '35'),
       ('Salad', '35'),
       ('Action', '36'),
       ('Comedy', '36'),
       ('Drama', '36'),
       ('Horror', '36'),
       ('Spring', '37'),
       ('Summer', '37'),
       ('Fall', '37'),
       ('Winter', '37'),
       ('Christmas', '38'),
       ('Halloween', '38'),
       ('Thanksgiving', '38'),
       ('New Year''s', '38'),
       ('Soccer', '39'),
       ('Basketball', '39'),
       ('Football', '39'),
       ('Baseball', '39'),
       ('Reading', '40'),
       ('Writing', '40'),
       ('Drawing', '40'),
       ('Cooking', '40'),
       ('Fiction', '41'),
       ('Non-fiction', '41'),
       ('Mystery', '41'),
       ('Romance', '41'),
       ('Pop', '42'),
       ('Rock', '42'),
       ('Rap', '42'),
       ('Country', '42'),
         ('Harry Potter', '43'),
         ('The Great Gatsby', '43'),
         ('To Kill a Mockingbird', '43'),
         ('Pride and Prejudice', '43'),
         ('Paris', '44'),
         ('London', '44'),
         ('Tokyo', '44'),
         ('New York City', '44');

-- Link options to questions
INSERT INTO question_options(choice_question_id, options_option_id)
VALUES ('33', '53'),
       ('33', '54'),
       ('33', '55'),
       ('33', '56'),
       ('34', '57'),
       ('34', '58'),
       ('34', '59'),
       ('34', '60'),
       ('35', '61'),
       ('35', '62'),
       ('35', '63'),
       ('35', '64'),
       ('36', '65'),
       ('36', '66'),
       ('36', '67'),
       ('36', '68'),
       ('37', '69'),
       ('37', '70'),
       ('37', '71'),
       ('37', '72'),
       ('38', '73'),
       ('38', '74'),
       ('38', '75'),
       ('38', '76'),
       ('39', '77'),
       ('39', '78'),
       ('39', '79'),
       ('39', '80'),
       ('40', '81'),
       ('40', '82'),
       ('40', '83'),
       ('40', '84'),
       ('41', '85'),
       ('41', '86'),
       ('41', '87'),
       ('41', '88'),
       ('42', '89'),
       ('42', '90'),
       ('42', '91'),
       ('42', '92'),
         ('43', '93'),
         ('43', '94'),
         ('43', '95'),
         ('43', '96'),
         ('44', '97'),
         ('44', '98'),
         ('44', '99'),
         ('44', '100');

-- Create survey 7 short
INSERT INTO survey(survey_name, survey_type)
VALUES ('Movie Questions', 'LINEAR');

-- Create questions for survey 7
INSERT INTO question(question_name, question_type, survey_id, is_multi_choice)
VALUES ('What is your favorite movie genre?', 'CHOICE', '7', false),
       ('What is your favorite movie?', 'CHOICE', '7', false),
       ('What is your favorite movie franchise?', 'CHOICE', '7', false),
       ('What is your favorite movie director?', 'CHOICE', '7', false),
       ('What is your favorite movie actor?', 'CHOICE', '7', false);

-- Create options for survey 7
INSERT INTO option(option_text, question)
VALUES ('Action', '45'),
       ('Comedy', '45'),
       ('Drama', '45'),
       ('Horror', '45'),
       ('Harry Potter', '46'),
       ('The Great Gatsby', '46'),
       ('To Kill a Mockingbird', '46'),
       ('Pride and Prejudice', '46'),
       ('Harry Potter', '47'),
       ('Star Wars', '47'),
       ('Marvel Cinematic Universe', '47'),
       ('James Bond', '47'),
       ('Christopher Nolan', '48'),
       ('Quentin Tarantino', '48'),
       ('Steven Spielberg', '48'),
       ('Martin Scorsese', '48'),
       ('Leonardo DiCaprio', '49'),
       ('Tom Hanks', '49'),
       ('Meryl Streep', '49'),
       ('Denzel Washington', '49');

-- Link options to questions
INSERT INTO question_options(choice_question_id, options_option_id)
VALUES ('45', '101'),
       ('45', '102'),
       ('45', '103'),
       ('45', '104'),
       ('46', '105'),
       ('46', '106'),
       ('46', '107'),
       ('46', '108'),
       ('47', '109'),
       ('47', '110'),
       ('47', '111'),
       ('47', '112'),
       ('48', '113'),
       ('48', '114'),
       ('48', '115'),
       ('48', '116'),
       ('49', '117'),
       ('49', '118'),
       ('49', '119'),
       ('49', '120');



