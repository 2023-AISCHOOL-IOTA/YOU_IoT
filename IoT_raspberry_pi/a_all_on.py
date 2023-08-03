#sudo pip3 install rpi_ws281x adafruit-circuitpython-neopixel
#sudo python3 -m pip install --force-reinstall adafruit-blinka  터미널에서 설치 먼저.

import board
import neopixel
import RPi.GPIO as GPIO
import time


PIN_NUMBER = board.D18  #led를 제어할 핀 번호 (D18은 BCM 핀 번호로 18번 핀을 의미합니다.)
RELAY_PIN = 24  # relay를 제어할 핀 번호 (24번 핀을 사용합니다.)
laser_pin = 23  # laser모듈을 제어할 핀 번호 (23번 핀을 사용합니다.)
NUM_LEDS = 15  # LED 바에 사용된 LED의 개수

# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BCM)
GPIO.setup(RELAY_PIN, GPIO.OUT)
GPIO.setup(laser_pin, GPIO.OUT)
# Neopixel 객체를 생성합니다.
pixels = neopixel.NeoPixel(PIN_NUMBER, NUM_LEDS, brightness=0.2, auto_write=False)


# LED 바의 색을 설정하는 함수를 정의합니다.
def set_led_bar_color(color):
    for i in range(NUM_LEDS):
        pixels[i] = color
    pixels.show()

# LED 바를 켜주는 함수를 정의합니다.
#pixels[0] = (255, 0, 0) 첫 번째 led 빨간색
#pixels.fill((0, 255, 0)) 전체 led 녹색
#우리가 만들 무늬에 따라 아래 내용 수정.
def turn_on_led_bar(color=(255, 0, 0)):
    set_led_bar_color(color)

# 레이저 레벨기를 켜는 함수를 정의합니다.
def turn_on_laser_level():
    GPIO.output(RELAY_PIN, GPIO.HIGH)  # HIGH 신호를 출력하여 릴레이 스위치를 켭니다.

def laser_on(pin):
    GPIO.output(pin, True)
    
#레벨기 on
turn_on_laser_level()
time.sleep(1)
#led바를 빨간색으로 켜기   (R,G,B)
turn_on_led_bar((255, 0, 0))
time.sleep(1)
#레이저모듈on
laser_on(laser_pin)