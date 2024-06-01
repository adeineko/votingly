import tools as dwh
import random
from config import SERVER, DATABASE_OP, DATABASE_DWH, USERNAME, PASSWORD, PORT_OP, PORT_DWH

# Define the connection parameters
conn_op = dwh.establish_connection(SERVER, DATABASE_OP, PORT_OP, USERNAME, PASSWORD)
conn_dwh = dwh.establish_connection(SERVER, DATABASE_DWH, PORT_DWH, USERNAME, PASSWORD)

# Create cursors
cursor_op = conn_op.cursor()
cursor_dwh = conn_dwh.cursor()

# Sample data for generating user names and emails
first_names = ['Liam', 'Olivia', 'Noah', 'Emma', 'Oliver', 'Charlotte', 'James', 'Amelia', 'Lucas', 'Isabella']
last_names = ['Smith', 'Johnson', 'Williams', 'Jones', 'Brown', 'Davis', 'Miller', 'Wilson', 'Moore', 'Taylor']
domains = ['gmail.com', 'yahoo.com', 'outlook.com', 'example.com']

# Password hash (a common placeholder for bcrypt hashed passwords)
password_hash = '$2a$12$1OnZfrFbk3HYlrpgbHYdyOG2kFKO97.n3f3CcToY.1cA2orKmRJl2'

# Function to generate a random email
def generate_email(first_name, last_name, domain):
    return f"{first_name.lower()}.{last_name.lower()}@{domain}"

# Create a table if it doesn't exist
# cursor_op.execute('''CREATE TABLE IF NOT EXISTS test_user_table
#                    (id BIGINT, org_id BIGINT, user_type VARCHAR(31), email VARCHAR(255),
#                     first_name VARCHAR(255), last_name VARCHAR(255), notes_taken VARCHAR(255), password VARCHAR(255))''')

# Generate and insert user data
for user_id in range(13, 201):
    first_name = random.choice(first_names)
    last_name = random.choice(last_names)
    email = generate_email(first_name, last_name, random.choice(domains))
    user_type = 'REGULAR'

    cursor_op.execute('''INSERT INTO user_table (id, user_type, email, first_name, last_name, password)
                         VALUES (%s, %s, %s, %s, %s, %s)''',
                      (user_id, user_type, email, first_name, last_name, password_hash))

# Commit the transaction
conn_op.commit()

# Close the connections
cursor_op.close()
conn_op.close()

print("Users 13 to 200 inserted successfully into user_table.")
