import RPi.GPIO as GPIO
import time

# 릴레이에 연결된 GPIO 핀 번호를 설정합니다. (BCM 모드 사용)
RELAY_PIN = 21  # 라즈베리파이의 GPIO 핀 번호 (21번 핀을 사용합니다.)

# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BCM)
GPIO.setup(RELAY_PIN, GPIO.OUT)

# 레이저 레벨기를 켜는 함수를 정의합니다.
def turn_on_laser_level():
    GPIO.output(RELAY_PIN, GPIO.LOW)  # LOW 신호를 출력하여 릴레이 스위치를 켭니다.

# 레이저 레벨기를 끄는 함수를 정의합니다.
def turn_off_laser_level():
    GPIO.output(RELAY_PIN, GPIO.HIGH)  # HIGH 신호를 출력하여 릴레이 스위치를 끕니다.

turn_on_laser_level()
time.sleep(1)
