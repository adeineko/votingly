import psycopg2
import config

def establish_connection(server=config.SERVER, database=config.DATABASE_OP, username=config.USERNAME, password=config.PASSWORD, host=config.HOST, port=config.PORT):
    """
    Establishes a connection to the specified PostgreSQL database.
    Args:
        server (str): The server name or IP address.
        database (str): The name of the database.
        username (str): The username for authentication.
        password (str): The password for authentication.
    Returns:
        psycopg2.extensions.connection: The connection object.
    """
    try:
        connection = psycopg2.connect(
            dbname=database,
            user=username,
            password=password,
            host=host,
            port=port
        )
       # print("Connected to the PostgreSQL database.")
        return connection
    except psycopg2.Error as e:
        print("Error connecting to the PostgreSQL database:", e)
        return None

# Example usage:
conn_op = establish_connection()
