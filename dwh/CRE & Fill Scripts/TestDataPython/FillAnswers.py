import random
from datetime import datetime, timedelta
import tools as dwh
from config import SERVER, DATABASE_OP, USERNAME, PASSWORD, PORT_OP

# Define the connection parameters
conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
cursor_op = conn_op.cursor()

# Create table if not exists
# cursor_op.execute('''CREATE TABLE IF NOT EXISTS answer_test (
#                         id SERIAL PRIMARY KEY,
#                         question_id INT,
#                         survey_id INT,
#                         user_id INT,
#                         answer_type VARCHAR(31),
#                         answer VARCHAR(255),
#                         answer_time TIMESTAMP
#                     )''')

# Surveys
# Questions for survey 4
survey_4_questions = [
    {'question_name': 'What is the most important issue to you in local elections?',
     'question_type': 'OPEN'},
    {'question_name': 'What can be done to improve local elections?',
     'question_type': 'CHOICE', 'is_multi_choice': True},
    {'question_name': 'How would you rate the importance of local elections in your country?',
     'question_type': 'RANGE', 'min': 0, 'max': 10, 'step': 1},
    {'question_name': 'Which of the following issues do you believe should be the top priority for candidates running in the upcoming local elections? (Select all that apply)',
     'question_type': 'CHOICE', 'is_multi_choice': True},
    {'question_name': 'In your opinion, what qualities do you think are most important for a candidate running for local office to possess?',
     'question_type': 'OPEN'},
    {'question_name': 'Which level of local government do you feel has the most impact on your daily life?',
     'question_type': 'CHOICE', 'is_multi_choice': True},
    {'question_name': 'How satisfied are you with the current performance of local government in addressing community needs and concerns?',
     'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'On a scale of 1 to 10, how informed do you feel about the candidates running for local office in the upcoming elections?',
     'question_type': 'RANGE', 'min': 0, 'max': 10, 'step': 1}
]

# Questions for survey 5
survey_5_questions = [
    {'question_name': 'How important is the local environment to you?',
     'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'How often do you participate in local environment improvement activities?',
     'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is the most important issue to you in local environment improvement?',
     'question_type': 'OPEN'},
    {'question_name': 'How would you rate the importance of local environment improvement in your country?',
     'question_type': 'RANGE', 'min': 0, 'max': 10, 'step': 1},
    {'question_name': 'On a scale of 1 to 10, how would you rate the level of cleanliness of public spaces (e.g., parks, streets) in our local area?',
     'question_type': 'RANGE', 'min': 0, 'max': 10, 'step': 1},
    {'question_name': 'Please rate your level of satisfaction with the accessibility and availability of recycling facilities and programs in our community, with 1 being "Not Satisfied" and 10 being "Very Satisfied".',
     'question_type': 'RANGE', 'min': 0, 'max': 10, 'step': 1},
    {'question_name': 'How would you rate the effectiveness of local government communication and engagement regarding environmental issues and initiatives, with 1 being "Not Effective" and 10 being "Extremely Effective"?',
     'question_type': 'RANGE', 'min': 0, 'max': 10, 'step': 1},
    {'question_name': 'Which of the following environmental conservation efforts do you believe would have the most significant positive impact on our local community?',
     'question_type': 'CHOICE', 'is_multi_choice': True}
]

# Questions for survey 6
survey_6_questions = [
    {'question_name': 'What is your favorite color?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite animal?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite food?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite movie genre?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite season?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite holiday?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite sport?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite hobby?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite book genre?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite music genre?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite book?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite travel destination?', 'question_type': 'CHOICE', 'is_multi_choice': False}
]

# Questions for survey 7
survey_7_questions = [
    {'question_name': 'What is your favorite movie genre?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite movie?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite movie franchise?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite movie director?', 'question_type': 'CHOICE', 'is_multi_choice': False},
    {'question_name': 'What is your favorite movie actor?', 'question_type': 'CHOICE', 'is_multi_choice': False}
]


# Function to generate random answers based on question type
def generate_answer(question_type):
    if question_type == 'OPEN':
        return 'Random open-ended answer'
    elif question_type == 'CHOICE':
        options = ['Option 1', 'Option 2', 'Option 3', 'Option 4']
        return random.choice(options)
    elif question_type == 'RANGE':
        return random.randint(0, 10)

# Generate and insert answers for survey 4
for user_id in range(5, 201):  # Generate answers for users 5 to 200
    answer_time = datetime(datetime.now().year, 4, random.randint(1, 30), random.randint(0, 23), random.randint(0, 59))
    for question_id, question in enumerate(survey_4_questions, start=13):  # Start question_id from 13
        answer_type = question['question_type']
        answer = generate_answer(answer_type)
        # Generate a random time delta between 1 to 5 minutes
        time_delta = timedelta(minutes=random.randint(1, 5))
        answer_time += time_delta  # Increment answer time by the time delta

        # Generate an answer
        answer = generate_answer(answer_type)

        # Insert the answer into the database
        cursor_op.execute('''INSERT INTO answer (question_id, survey_id, user_id, answer_type, answer, answer_time)
                             VALUES (%s, %s, %s, %s, %s, %s)''',
                          (question_id, 4, user_id, answer_type, answer, answer_time))

# Generate and insert answers for survey 5
for user_id in range(5, 201):  # Generate answers for users 5 to 200
    answer_time = datetime(datetime.now().year, 4, random.randint(1, 30), random.randint(0, 23), random.randint(0, 59))
    for question_id, question in enumerate(survey_5_questions, start=13):  # Start question_id from 13
        answer_type = question['question_type']

        # Generate a random time delta between 1 to 5 minutes
        time_delta = timedelta(minutes=random.randint(1, 5))
        answer_time += time_delta  # Increment answer time by the time delta

        # Generate an answer
        answer = generate_answer(answer_type)

        # Insert the answer into the database
        cursor_op.execute('''INSERT INTO answer (question_id, survey_id, user_id, answer_type, answer, answer_time)
                             VALUES (%s, %s, %s, %s, %s, %s)''',
                          (question_id, 5, user_id, answer_type, answer, answer_time))

# Generate and insert answers for survey 6
for user_id in range(5, 201):  # Generate answers for users 5 to 200
    answer_time = datetime(datetime.now().year, 4, random.randint(1, 30), random.randint(0, 23), random.randint(0, 59))
    for question_id, question in enumerate(survey_6_questions, start=13):  # Start question_id from 13
        answer_type = question['question_type']

        # Generate a random time delta between 1 to 5 minutes
        time_delta = timedelta(minutes=random.randint(1, 5))
        answer_time += time_delta  # Increment answer time by the time delta

        # Generate an answer
        answer = generate_answer(answer_type)

        # Insert the answer into the database
        cursor_op.execute('''INSERT INTO answer (question_id, survey_id, user_id, answer_type, answer, answer_time)
                             VALUES (%s, %s, %s, %s, %s, %s)''',
                          (question_id, 6, user_id, answer_type, answer, answer_time))

# Generate and insert answers for survey 7
for user_id in range(5, 201):  # Generate answers for users 5 to 200
    answer_time = datetime(datetime.now().year, 4, random.randint(1, 30), random.randint(0, 23), random.randint(0, 59))
    for question_id, question in enumerate(survey_7_questions, start=13):  # Start question_id from 13
        answer_type = question['question_type']

        # Generate a random time delta between 1 to 5 minutes
        time_delta = timedelta(minutes=random.randint(1, 5))
        answer_time += time_delta  # Increment answer time by the time delta

        # Generate an answer
        answer = generate_answer(answer_type)

        # Insert the answer into the database
        cursor_op.execute('''INSERT INTO answer (question_id, survey_id, user_id, answer_type, answer, answer_time)
                             VALUES (%s, %s, %s, %s, %s, %s)''',
                          (question_id, 7, user_id, answer_type, answer, answer_time))

# Commit the transaction
conn_op.commit()

# Close the connection
conn_op.close()

print("Answers inserted successfully.")
