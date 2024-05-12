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

def get_first_last_answer_timestamp(cursor_op, survey_id, user_id):
    """
    Get the timestamps of the first and last answers for a specific survey and user.
    Args:
        cursor_op (pyodbc.Cursor): Cursor object for the operational database.
        survey_id (int): ID of the survey.
        user_id (int): ID of the user.
    Returns:
        tuple: Tuple containing the timestamps of the first and last answers.
    """
    cursor_op.execute("SELECT MIN(answer_time), MAX(answer_time) FROM answer WHERE survey_id = %s AND user_id = %s", (survey_id, user_id))
    return cursor_op.fetchone()

def get_time_id(cursor_op, cursor_dwh, user_id, survey_id):
    """
    Get the date_id from the data warehouse based on the log_time fetched from the operational database.
    Args:
        cursor_op (pyodbc.Cursor): Cursor object for the operational database.
        cursor_dwh (pyodbc.Cursor): Cursor object for the data warehouse database.
        user_id (int): ID of the user.
        survey_id (int): ID of the survey.
    Returns:
        int: Date ID if found, None otherwise.
    """
    try:
        # Query the operational database to fetch the log_time
        cursor_op.execute("SELECT MIN(answer_time) FROM answer WHERE survey_id = %s AND user_id = %s", (survey_id, user_id))
        log_time_row = cursor_op.fetchone()
        if log_time_row:
            log_time = log_time_row[0]

            # Extract components from the log_time
            day = log_time.day
            month = log_time.month
            year = log_time.year
            hour = log_time.hour

            # Determine the time of day based on the hour
            if 6 <= hour < 11:
                time_of_day = "Morning"
            elif 11 <= hour < 14:
                time_of_day = "Noon"
            elif 14 <= hour < 18:
                time_of_day = "Afternoon"
            elif 18 <= hour < 23:
                time_of_day = "Evening"
            else:
                time_of_day = "Night"

            # Date query
            date_query = """
                SELECT time_id
                FROM dim_time
                WHERE day = %s AND month = %s AND year = %s AND time_of_day = %s
                """

            # Execute the date query with the day, month, year, and time_of_day parameters
            cursor_dwh.execute(date_query, (day, month, year, time_of_day))

            # Fetch the date_id from the result set
            date_row = cursor_dwh.fetchone()

            # If date_id is found, return it
            if date_row:
                (date_id,) = date_row
                return date_id
            else:
                return None
        else:
            return None

    except pyodbc.Error as e:
        print(f"Error executing SQL query: {e}")
        return None



def fill_fact_table(cursor_op, cursor_dwh):
    """
    Fills the fact table with data from dimensions and counts from the operational database.
    Args:
        cursor_op (pyodbc.Cursor): Cursor object for the operational database.
        cursor_dwh (pyodbc.Cursor): Cursor object for the data warehouse database.
    """
    try:
        # Fetch user and survey IDs from the data warehouse
        cursor_dwh.execute("SELECT user_id, survey_id FROM dim_user, dim_survey")
        rows = cursor_dwh.fetchall()

        # Insert data into the fact table
        for row in rows:
            user_id, survey_id = row

            # Check if the user has answered the survey
            answer_count = get_answer_count(cursor_op, survey_id, user_id)
            if answer_count > 0:
                print(f"Inserting data into the fact table: user_id={user_id}, survey_id={survey_id}")

                # Get number of questions for the survey
                question_count = get_question_count(cursor_op, survey_id)

                # Get first and last answer timestamps
                first_answer, last_answer = get_first_last_answer_timestamp(cursor_op, survey_id, user_id)

                # Calculate duration in minutes
                duration = ((last_answer - first_answer).total_seconds() / 60) if first_answer and last_answer else None

                # Get date_id based on the first answer timestamp and time of day
                time_id = get_time_id(cursor_op, cursor_dwh, user_id, survey_id)
                if time_id is not None:
                    # Insert data into the fact table with counts, duration, and date_id
                    cursor_dwh.execute("INSERT INTO fact_survey (user_id, survey_id, number_of_questions, number_of_answers, total_time_spent, time_id) VALUES (%s, %s, %s, %s, %s, %s)", (user_id, survey_id, question_count, answer_count, duration, time_id))
                    print("Data inserted successfully.")
                else:
                    print("No date_id found.")

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
