import RPi.GPIO as GPIO
import time

relay_pin = 24  # relay를 제어할 핀 번호 (24번 핀을 사용합니다.)
laser_pin = 23  # laser모듈을 제어할 핀 번호 (23번 핀을 사용합니다.)


# 레이저 레벨기를 켜는 함수를 정의합니다.
def level_on():
    GPIO.output(relay_pin, GPIO.HIGH)  # HIGH 신호를 출력하여 릴레이 스위치를 켭니다.

def laser_on(pin):
    GPIO.output(pin, GPIO.HIGH)

# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BCM)
GPIO.setup(relay_pin, GPIO.OUT)
GPIO.setup(laser_pin, GPIO.OUT)