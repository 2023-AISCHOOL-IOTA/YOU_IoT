import pymysql as ps
import requests
import time

#db와 연동해서 시작점, 끝점, gps좌표받아옴
db = ps.connect(
    host="project-db-stu3.smhrd.com", user="Insa4_IOTA_hacksim_3", passwd="aishcool3", db="Insa4_IOTA_hacksim_3", charset="utf8mb4",
    port = 3307
)
cursor=db.cursor()
sql='select start_lon from lon_lat'
cursor.execute(sql)
start_lon=cursor.fetchall()
cursor=db.cursor()
sql='select start_lat from lon_lat'
cursor.execute(sql)
start_lat=cursor.fetchall()
cursor=db.cursor()
sql='select end_lat from lon_lat'
cursor.execute(sql)
end_lat=cursor.fetchall()
sql='select end_lon from lon_lat'
cursor.execute(sql)
end_lon=cursor.fetchall()

start_gps=[]
end_gps=[]

#db에서 받아온 gps좌표를 gps_check를 쓰기 위해 튜플에 담아주기
for i in range(len(start_lon)):
    start_gps.append((start_lon[i][0],start_lat[i][0]))
for i in range(len(start_lon)):
    end_gps.append((end_lon[i][0],end_lat[i][0]))
    
db.commit()
db.close()