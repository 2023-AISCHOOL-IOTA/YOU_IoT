import RPi.GPIO as GPIO
import time

# 7세그먼트에 각 숫자를 표시하기 위한 세그먼트 패턴 (공통 음극 기준)
segments = {
    '0': [1, 1, 1, 1, 1, 1, 0],
    '1': [0, 1, 1, 0, 0, 0, 0],
    '2': [1, 1, 0, 1, 1, 0, 1],
    '3': [1, 1, 1, 1, 0, 0, 1],
    '4': [0, 1, 1, 0, 0, 1, 1],
    '5': [1, 0, 1, 1, 0, 1, 1],
    '6': [1, 0, 1, 1, 1, 1, 1],
    '7': [1, 1, 1, 0, 0, 0, 0],
    '8': [1, 1, 1, 1, 1, 1, 1],
    '9': [1, 1, 1, 0, 0, 1, 1],
}

# 7세그먼트에 숫자를 표시할 핀들 설정 (공통 음극 기준)
segments_pins = [7, 11, 13, 15, 29, 31, 33]

# GPIO 모드 설정
GPIO.setmode(GPIO.BOARD)

# 핀들을 출력 핀으로 설정
for pin in segments_pins:
    GPIO.setup(pin, GPIO.OUT)
    GPIO.output(pin, GPIO.LOW)

def display_number(number):
    digit_pins = segments[str(number)]
    for pin, state in zip(segments_pins, digit_pins):
        GPIO.output(pin, state)

try:
    while True:
        # 현재 시간 가져오기
        now = time.localtime()
        hour = now.tm_hour
        minute = now.tm_min

        # 시간과 분 각각의 10의 자리와 1의 자리 표시
        display_number(hour // 10)
        time.sleep(0.001)
        display_number(hour % 10)
        time.sleep(0.001)

        display_number(minute // 10)
        time.sleep(0.001)
        display_number(minute % 10)
        time.sleep(0.001)

except KeyboardInterrupt:
    pass

# GPIO 리소스 정리
GPIO.cleanup()