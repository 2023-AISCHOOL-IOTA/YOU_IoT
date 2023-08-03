import pandas as pd
import sqlite3
import json

csv_file_path = 'C:\\Users\\aischool03\\OneDrive - 인공지능산업융합사업단\\바탕 화면\\project\\you_iot\\web\\pp_parking.csv'
database_file_path = 'C:\\Users\\aischool03\\OneDrive - 인공지능산업융합단\\바탕 화면\\project\\you_iot\\web\\pp_parking.db'
json_file_path = 'C:\\Users\\aischool03\\OneDrive - 인공지능산업융합단\\바탕 화면\\project\\you_iot\\web\\pp_parking.json'
table_name = 'pp_parking'

# Read the data from the CSV file
data = pd.read_csv(csv_file_path)

# Connect to the SQLite database and save the data
conn = sqlite3.connect(database_file_path)
data.to_sql(table_name, conn, if_exists='replace', index=False)
conn.close()

# Read the data from the database and convert it to JSON
conn = sqlite3.connect(database_file_path)
query = f"SELECT * FROM {table_name}"
json_data = pd.read_sql_query(query, conn).to_json(orient='records')
conn.close()

# Save the JSON data to a file
with open(json_file_path, 'w', encoding='utf-8') as jsonfile:
    jsonfile.write(json_data)
