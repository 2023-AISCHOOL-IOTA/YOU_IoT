#pip install smbus 이거 먼저 해야됨
import time
import I2C_LCD_driver

# LCD 초기화
mylcd = I2C_LCD_driver.lcd()
#i2c 연결이라서 SDA핀, SCL핀 연결해야됨
try:
    while True:
        # 현재 시간 가져오기
        current_time = time.strftime('%H:%M:%S')

        # 시간 형식을 시간과 분으로 분리
        hour, minute, _ = current_time.split(':')
        hour = int(hour)

        # 현재 시간이 09시부터 18시 사이인지 확인하여 가능/불가능 판별
        if 9 <= hour <= 18:
            availability = '가능'
        else:
            availability = '불가능'

        # LCD에 표시
        mylcd.lcd_display_string(current_time, 1, 0)  # 0행, 0열에 현재 시간 표시
        mylcd.lcd_display_string(availability, 2, 0)   # 1행, 0열에 '가능' 또는 '불가능' 표시

        time.sleep(1)  # 1초마다 업데이트

except KeyboardInterrupt:
    pass

finally:
    # 종료 시 GPIO 정리
    mylcd.lcd_clear()