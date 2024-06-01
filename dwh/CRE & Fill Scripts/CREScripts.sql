-- Create User Dimension table
CREATE TABLE dim_user (
                          user_id SERIAL PRIMARY KEY,
                          last_name VARCHAR,
                          first_name VARCHAR
);

-- Create Survey Dimension table
CREATE TABLE dim_survey (
                            survey_id SERIAL PRIMARY KEY,
                            survey_name VARCHAR
);

-- Create Time Dimension table
CREATE TABLE dim_time (
                          time_id SERIAL PRIMARY KEY,
                          day INT,
                          month INT,
                          year INT,
                          day_of_week VARCHAR,
                          time_of_day VARCHAR
);

-- Create Fact Survey table
CREATE TABLE fact_survey (
                             user_id INT,
                             time_id INT,
                             survey_id INT,
                             number_of_questions INT,
                             number_of_answers INT,
                             total_time_spent INT,
                             FOREIGN KEY (user_id) REFERENCES dim_user(user_id),
                             FOREIGN KEY (time_id) REFERENCES dim_time(time_id),
                             FOREIGN KEY (survey_id) REFERENCES dim_survey(survey_id)
);

CREATE TABLE test_user_table(
    id BIGINT,
    org_id BIGINT,
    user_type VARCHAR(31),
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    notes_taken VARCHAR(255),
    password VARCHAR(255)
);

