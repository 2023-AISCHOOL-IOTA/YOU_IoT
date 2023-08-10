import pymysql as ps
import requests
import time
import I2C_LCD_driver
import RPi.GPIO as GPIO
import led_on #a_led_on_final.py 파일 이름에 맞춰서 변경
import led_off#a_led_off_final.py 파일 이름에 맞춰서 변경


#db와 연동해서 시작점, 끝점, 허용시간 테이블 받아옴
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

#시작점, 끝점 gps좌표를 담아둘 리스트
start_gps=[]
end_gps=[]
#현재 위치가 어느 지점에 속해있는지 확인하는 변수
target_point=0
#gps 모듈에서 받아온 값을 담아둘 변수
longitude_now=0
latitude_now=0
# LCD 초기화
mylcd = I2C_LCD_driver.lcd()
#i2c 연결이라서 SDA핀, SCL핀 연결해야됨
is_time_change=True #디바운서 코드를 위한 변수
relay_pin = 24  # relay를 제어할 핀 번호 (24번 핀을 사용합니다.)
laser_pin = 23  # laser모듈을 제어할 핀 번호 (23번 핀을 사용합니다.)

# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BCM)
GPIO.setup(relay_pin, GPIO.OUT)
GPIO.setup(laser_pin, GPIO.OUT)

#현재 gps좌표가 어느 구간에 있는지 확인하는 함수
def gps_check(point1, point2, target_point):
    min_x = min(point1[0], point2[0])
    max_x = max(point1[0], point2[0])
    min_y = min(point1[1], point2[1])
    max_y = max(point1[1], point2[1])
    if min_x <= target_point[0] <= max_x and min_y <= target_point[1] <= max_y:
        return True
    return False

#현재 gps 모듈이 없어서 임의의 gps값 설정
#gps 
longitude_now=126.8398
latitude_now=35.1470000

#db에서 받아온 gps좌표를 gps_check를 쓰기 위해 튜플에 담아주기
for i in range(len(start_lon)):
    start_gps.append((start_lon[i][0],start_lat[i][0]))
for i in range(len(end_lon)):
    end_gps.append((end_lon[i][0],end_lat[i][0]))

#현재 gps좌표가 어느 노상주차 허용구간에 있는지 확인
for i in range(len(start_gps)):
    start_point = start_gps[i]
    end_point = end_gps[i]
    now_point = (longitude_now,latitude_now)
    if gps_check(start_point, end_point, now_point):
        target_point+=i+1

#gps좌표 index로 허용시간 찾기
cursor=db.cursor()
sql=f'select street_id, permit_t from street_park where street_id={target_point}'
cursor.execute(sql)
permit_time=cursor.fetchall()

#끝내기전에 db와 연결종료
db.commit()
db.close()

#현재시간과 허용시간을 비교해서 가능, 불가능 lcd에 표시  
while True:
    # 현재 시간 가져오기
    current_time = time.strftime('%H:%M:%S')
    # 시간 형식을 시간과 분으로 분리
    hour, minute, _ = current_time.split(':')
    hour = int(hour)
    # 현재 시간이 09시부터 18시 사이인지 확인하여 가능/불가능 판별
    if int(permit_time[0][1][:2])<int(permit_time[0][1][6:8]):
        if hour>=int(permit_time[0][1][:2]) and hour<=int(permit_time[0][1][6:8]) :
            availability = 'ok'
        else :
            availability = 'no'
    else :
        if hour>=int(permit_time[0][1][:2]) or hour<=int(permit_time[0][1][6:8]) :
            availability = 'ok'
        else :
            availability = 'no'
    if availability=='ok':
        # LCD에 표시
        mylcd.lcd_display_string(current_time, 1, 0)  # 0행, 0열에 현재 시간 표시
        mylcd.lcd_display_string(availability, 2, 0)   # 1행, 0열에 '가능' 또는 '불가능' 표시
    else:
        if len(permit_time_here[0][1])>12:
            if hour>=int(permit_time_here[0][1][13:15]) or hour<=int(permit_time_here[0][1][19:21]) :
                availability = 'ok'
            else :
                availability = 'no'
            mylcd.lcd_display_string(current_time, 1, 0)  # 0행, 0열에 현재 시간 표시
            mylcd.lcd_display_string(availability, 2, 0)  # 1행, 0열에 '가능' 또는 '불가능' 표시
    if is_time_change==True and availability == 'ok':
        led_on.laser_on(laser_pin)
        led_on.level_on()
        print("good")
        is_time_change=False
    if is_time_change==False and availability == 'no':
        led_off.laser_off(laser_pin)
        led_off.level_off()
        print("bad")
        is_time_change=True
    time.sleep(1)  # 1초마다 업데이트