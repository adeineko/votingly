# CURRENTLY FOR REGULAR USERS ONLY

import tools as dwh
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD, PORT_OP, PORT_DWH

# Define the connection parameters
conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)

# Create cursors
cursor_op = conn_op.cursor()
cursor_dwh = conn_dwh.cursor()

# Define SQL query to fetch data from the source table
select_query = """
SELECT id, first_name, last_name FROM user_table WHERE user_type = 'REGULAR';
"""

# Execute the query
cursor_op.execute(select_query)

# Fetch all the rows
rows = cursor_op.fetchall()

# Define the insert query
insert_query = """
INSERT INTO dim_user (user_id, first_name, last_name)
VALUES (%s, %s, %s);
"""

# Execute the insert query for each row
for row in rows:
    cursor_dwh.execute(insert_query, row)

# Commit the transaction
conn_dwh.commit()

# Close the cursors and connections
cursor_op.close()
cursor_dwh.close()
conn_op.close()
conn_dwh.close()
