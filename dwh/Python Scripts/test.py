import tools as dwh
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD

# Define the connection parameters
conn_op = dwh.establish_connection(SERVER, DATABASE_OP, USERNAME, PASSWORD)
conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, USERNAME, PASSWORD)

# Create cursors
cursor_op = conn_op.cursor()
cursor_dwh = conn_dwh.cursor()

# Test query
select_query = "SELECT * FROM survey;"

try:
    # Execute the query on the operational database
    cursor_op.execute(select_query)
    rows = cursor_op.fetchall()
    for row in rows:
        print(row)

finally:
    # Close cursors and connections
    cursor_op.close()
    cursor_dwh.close()
    conn_op.close()
    conn_dwh.close()
