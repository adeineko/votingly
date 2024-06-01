import tools as dwh
from config import SERVER, DATABASE_OP, USERNAME, PASSWORD, PORT_OP

# Define the connection parameters
conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
cursor_op = conn_op.cursor()

# Define the surveys to remove
surveys_to_remove = [7]  # Change this list to specify which surveys to remove

# Fetch a list of random users
cursor_op.execute("SELECT user_id FROM answer GROUP BY user_id ORDER BY RANDOM() LIMIT 5")
random_users = cursor_op.fetchall()

# 10 people wont have survey 4
# 13 people wont have survey 5
# 18 people wont have survey 6
# 5 people wont have survey 7

# Remove the specified surveys from the random users
for user_id in random_users:
    for survey_id in surveys_to_remove:
        cursor_op.execute("DELETE FROM answer WHERE user_id = %s AND survey_id = %s", (user_id, survey_id))

# Commit the transaction
conn_op.commit()

# Close the connection
conn_op.close()

print(f"Surveys {surveys_to_remove} removed from random users successfully.")
