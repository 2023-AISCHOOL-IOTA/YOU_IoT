import neopixel
import RPi.GPIO as GPIO
import time

led_bar_pin = 18  #led를 제어할 핀 번호 (18번 핀을 사용합니다.)
relay_pin = 24  # relay를 제어할 핀 번호 (24번 핀을 사용합니다.)
laser_pin = 23  # laser모듈을 제어할 핀 번호 (23번 핀을 사용합니다.)
led_nums = 10  # LED 바에 사용된 LED의 개수
# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BCM)
GPIO.setup(relay_pin, GPIO.OUT)
GPIO.setup(laser_pin, GPIO.OUT)
GPIO.setup(led_bar_pin, GPIO.OUT)
# Neopixel 객체를 생성합니다.
pixels = neopixel.NeoPixel(led_bar_pin, led_nums, brightness=0.2, auto_write=False)


# LED 바의 색을 설정하는 함수를 정의합니다.
def set_led_bar_color(color):
    for i in range(led_nums):
        pixels[i] = color
    pixels.show()

# LED 바를 꺼주는 함수를 정의합니다.
def turn_off_led_bar():
    set_led_bar_color((0, 0, 0))

# 레이저 레벨기를 끄는 함수를 정의합니다.
def turn_off_laser_level():
    GPIO.output(relay_pin, GPIO.LOW)  # LOW 신호를 출력하여 릴레이 스위치를 끕니다.

#레이저 모듈을 끄는 함수를 정의합니다.
def laser_off(pin):
    GPIO.output(pin, False)
    
turn_off_laser_level() #레벨기 끄기
time.sleep(1)
laser_off(laser_pin) # laser모듈 끄기
time.sleep(1)
turn_off_led_bar() #led바 끄기

GPIO.cleanup()