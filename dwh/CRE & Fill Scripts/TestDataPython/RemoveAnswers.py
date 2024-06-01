import tools as dwh
from config import SERVER, DATABASE_OP, USERNAME, PASSWORD, PORT_OP

# Define the connection parameters
conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
cursor_op = conn_op.cursor()

# Count the total number of records in the table
cursor_op.execute("SELECT COUNT(*) FROM answer")
total_records = cursor_op.fetchone()[0]

# Define the percentage of records to remove
percentage_to_remove = 17  # Change this value as needed

# Calculate the number of records to remove
records_to_remove = int(total_records * (percentage_to_remove / 100))

# Fetch a list of random IDs to delete
cursor_op.execute(f"SELECT answer_id FROM answer ORDER BY RANDOM() LIMIT {records_to_remove}")
records_to_delete = cursor_op.fetchall()

# Delete the selected records
for record_id in records_to_delete:
    cursor_op.execute("DELETE FROM answer WHERE answer_id = %s", (record_id,))

# Commit the transaction
conn_op.commit()

# Close the connection
conn_op.close()

print(f"{records_to_remove} records removed successfully.")
