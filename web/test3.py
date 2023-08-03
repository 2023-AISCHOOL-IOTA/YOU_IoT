import csv

# CSV 파일 경로 지정
file_path = "data/pp_parking.csv"



import pymysql as ps

db = ps.connect(
    host="project-db-stu3.smhrd.com", user="Insa4_IOTA_hacksim_3", passwd="aishcool3", db="Insa4_IOTA_hacksim_3", charset="utf8",
    port = 3307
)


# 커서 생성
cursor = db.cursor()
cursor.execute("use Insa4_IOTA_hacksim_3")


# CSV 파일 열기
with open(file_path, newline="") as csvfile:
    # CSV 파일 읽기
    reader = csv.reader(csvfile)
    next(reader)
    
    for row in reader:
    # INSERT 쿼리 실행 parkingID = int(row[0])
        pp_parkingID = int(row[0])
        road_address = row[1]
        remaining_seats = int(row[2])
        Sortation = row[3]
        parking_name = row[4]
        parking_type = row[5]
        
        sql = "INSERT INTO parking (pp_parkingID, road_address, remaining_seats, Sortation, parking_name, parking_type) VALUES (%s, %s, %s, %s, %s, %s)"
        values = (pp_parkingID, road_address, remaining_seats, Sortation, parking_name, parking_type)
        cursor.execute(sql, values)





# 변경사항 커밋
db.commit()
# 연결 종료
cursor.close()
db.close()