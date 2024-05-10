import pandas as pd
import pyodbc

import tools as dwh
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD, PORT_OP, PORT_DWH

# Define the connection parameters
# conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
# conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)

import pandas as pd
import pyodbc
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD

def calculate_day_of_week(day):
    days_of_week = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    # Adjust day to match Python's datetime.weekday() convention
    day_index = (day - 1) % 7
    return days_of_week[day_index]

def calculate_time_of_day(hour):
    if 6 <= hour < 10:
        return 'Morning'
    elif 10 <= hour < 13:
        return 'Noon'
    elif 13 <= hour < 18:
        return 'Afternoon'
    elif 18 <= hour < 23:
        return 'Evening'
    else:
        return 'Night'


def fill_date_table(cursor_dwh, start_date, end_date='2040-01-01'):
    """
    Fills the 'date_table' table with date-related data.
    Args:
        cursor_dwh: The cursor object for the 'tutorial_dwh' database.
        start_date (str): The start date for filling the table.
        end_date (str): The end date for filling the table (default is '2040-01-01').
    """
    insert_query = """
    INSERT INTO dim_time (day, month, year, day_of_week)
    VALUES (%s, %s, %s, %s)
    """
    current_date = pd.to_datetime(start_date)
    end_date = pd.to_datetime(end_date)
    #id = 0
    while current_date <= end_date:
        # id += 1
        day = current_date.day
        week = current_date.week
        month = current_date.month
        year = current_date.year
        day_of_week = calculate_day_of_week(day)
       # time_of_day = calculate_time_of_day(hour)

        # Execute the INSERT query
        cursor_dwh.execute(insert_query, (
            day, month, year, day_of_week
        ))

        # Commit the transaction
        # cursor_dwh.commit()
        current_date += pd.Timedelta(days=1)

def main():
    try:
        # Connect to the 'tutorial_op' database
        conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
        cursor_op = conn_op.cursor()

        # Connect to the 'tutorial_dwh' database
        conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)
        cursor_dwh = conn_dwh.cursor()

        # Fetch minimum order date
        start_date = '2024-01-01'
        print(start_date)

        # Fill the 'date_table'
        fill_date_table(cursor_dwh, start_date, '2100-01-01')

        # Commit the transaction
        conn_dwh.commit()

        # Close the connections
        cursor_op.close()
        conn_op.close()
        cursor_dwh.close()
        conn_dwh.close()

    except pyodbc.Error as e:
        print(f"Error connecting to the database: {e}")

if __name__ == "__main__":
    main()
