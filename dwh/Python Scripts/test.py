import tools as dwh
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD, PORT_OP, PORT_DWH

# Define the connection parameters
conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)

# Create cursors
cursor_op = conn_op.cursor()
cursor_dwh = conn_dwh.cursor()

# Test query
select_query = "SELECT survey_id FROM survey;"

try:
    # Execute the query on the operational database
    cursor_op.execute(select_query)
    rows = cursor_op.fetchall()
    for row in rows:
        print(row)

# Insert the data into the data warehouse test table
    insert_query = "INSERT INTO test (test1) VALUES (%s);"
    cursor_dwh.executemany(insert_query, rows)
    conn_dwh.commit()
    print("Data inserted successfully.")

finally:
    # Close cursors and connections
    cursor_op.close()
    cursor_dwh.close()
    conn_op.close()
    conn_dwh.close()
