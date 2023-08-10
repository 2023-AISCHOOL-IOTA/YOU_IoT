import RPi.GPIO as GPIO
import time

relay_pin = 24  # relay를 제어할 핀 번호 (24번 핀을 사용합니다.)
laser_pin = 23  # laser모듈을 제어할 핀 번호 (23번 핀을 사용합니다.)
led_nums = 10  # LED 바에 사용된 LED의 개수
# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BCM)
GPIO.setup(relay_pin, GPIO.OUT)
GPIO.setup(laser_pin, GPIO.OUT)

# 레이저 레벨기를 끄는 함수를 정의합니다.
def level_off():
    GPIO.output(relay_pin, GPIO.LOW)  # LOW 신호를 출력하여 릴레이 스위치를 끕니다.

#레이저 모듈을 끄는 함수를 정의합니다.
def laser_off(pin):
    GPIO.output(pin, False)

laser_off(laser_pin)
level_off()

GPIO.cleanup()