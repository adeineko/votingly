import pyodbc
import tools as dwh
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD, PORT_OP, PORT_DWH


def establish_connection():
    """
    Establishes connections to the operational and data warehouse databases.
    Returns:
        tuple: Tuple containing operational and data warehouse cursors.
    """
    # Connect to the operational database
    conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
    cursor_op = conn_op.cursor()

    # Connect to the data warehouse database
    conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)
    cursor_dwh = conn_dwh.cursor()

    return cursor_op, cursor_dwh

def get_question_count(cursor_op, survey_id):
    """
    Get the number of questions in the operational database for a specific survey.
    Args:
        cursor_op (pyodbc.Cursor): Cursor object for the operational database.
        survey_id (int): ID of the survey.
    Returns:
        int: Number of questions in the operational database for the given survey.
    """
    cursor_op.execute("SELECT COUNT(*) FROM question WHERE survey_id = %s", (survey_id,))
    return cursor_op.fetchone()[0]

def get_answer_count(cursor_op, survey_id, user_id):
    """
    Get the number of answers in the operational database for a specific survey and user.
    Args:
        cursor_op (pyodbc.Cursor): Cursor object for the operational database.
        survey_id (int): ID of the survey.
        user_id (int): ID of the user.
    Returns:
        int: Number of answers in the operational database for the given survey and user.
    """
    cursor_op.execute("SELECT COUNT(*) FROM answer WHERE survey_id = %s AND user_id = %s", (survey_id, user_id))
    return cursor_op.fetchone()[0]


def fill_fact_table(cursor_op, cursor_dwh):
    """
    Fills the fact table with data from dimensions and counts from the operational database.
    Args:
        cursor_op (pyodbc.Cursor): Cursor object for the operational database.
        cursor_dwh (pyodbc.Cursor): Cursor object for the data warehouse database.
    """
    try:
        # Get user, survey, and time IDs from dimensions
        cursor_dwh.execute("SELECT user_id, survey_id FROM dim_user, dim_survey")
        rows = cursor_dwh.fetchall()

        # Insert data into the fact table
        for row in rows:
            user_id, survey_id = row
            # Check if the user has answered the survey
            answer_count = get_answer_count(cursor_op, survey_id, user_id)
            if answer_count > 0:
                print(f"Inserting data into the fact table: {user_id}, {survey_id}")
                # Get number of questions for the survey
                question_count = get_question_count(cursor_op, survey_id)
                # Insert data into the fact table with counts
                cursor_dwh.execute("INSERT INTO fact_survey (user_id, survey_id, number_of_questions, number_of_answers) VALUES (%s, %s, %s, %s)", (user_id, survey_id, question_count, answer_count))

        # cursor_dwh.commit()
        print("Data inserted successfully into the fact table.")

    except pyodbc.Error as e:
        print(f"Error executing SQL query: {e}")



def main():
    try:
        # Establish connection to both databases
        # cursor_op, cursor_dwh = establish_connection()

        conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
        cursor_op = conn_op.cursor()

        conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)
        cursor_dwh = conn_dwh.cursor()

        # Fill the fact table
        fill_fact_table(cursor_op, cursor_dwh)

        # Commit the transaction
        conn_dwh.commit()
        print("Data inserted successfully.")

    except pyodbc.Error as e:
        print(f"Error connecting to the database: {e}")

    finally:
        # Close the cursors and connections
        cursor_op.close()
        cursor_dwh.close()


if __name__ == "__main__":
    main()
