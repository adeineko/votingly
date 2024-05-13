import pandas as pd
import pyodbc
import tools as dwh
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD, PORT_OP, PORT_DWH

def calculate_day_of_week(day):
    days_of_week = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    day_index = (day - 1) % 7
    return days_of_week[day_index]

def fill_date_table(cursor_dwh, start_date, end_date='2026-01-01'):
    insert_query = """
    INSERT INTO dim_time (day, month, year, day_of_week, time_of_day)
    VALUES (%s, %s, %s, %s, %s)
    """
    current_date = pd.to_datetime(start_date)
    end_date = pd.to_datetime(end_date)
    time_of_day_categories = ['Morning', 'Noon', 'Afternoon', 'Evening', 'Night']

    while current_date <= end_date:
        day = current_date.day
        month = current_date.month
        year = current_date.year
        day_of_week = calculate_day_of_week(current_date.day)

        for time_of_day in time_of_day_categories:
            cursor_dwh.execute(insert_query, (
                day, month, year, day_of_week, time_of_day
            ))

        current_date += pd.Timedelta(days=1)

def main():
    try:
        conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
        cursor_op = conn_op.cursor()

        conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)
        cursor_dwh = conn_dwh.cursor()

        start_date = '2024-01-01'
        print("Start date:", start_date)

        fill_date_table(cursor_dwh, start_date, '2026-01-01')

        conn_dwh.commit()
        print("Data inserted successfully.")

    except pyodbc.Error as e:
        print(f"Error connecting to the database: {e}")

    except Exception as ex:
        print(f"An error occurred: {ex}")

    finally:
        cursor_op.close()
        conn_op.close()
        cursor_dwh.close()
        conn_dwh.close()

if __name__ == "__main__":
    main()
