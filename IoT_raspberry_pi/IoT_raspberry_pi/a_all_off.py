
import board
import neopixel
import RPi.GPIO as GPIO
import time


PIN_NUMBER = board.D18  #led를 제어할 핀 번호 (D18은 BCM 핀 번호로 18번 핀을 의미합니다.)
RELAY_PIN = 24  # relay를 제어할 핀 번호 (24번 핀을 사용합니다.)
laser_pin = 23  # laser모듈을 제어할 핀 번호 (23번 핀을 사용합니다.)
NUM_LEDS = 15  # LED 바에 사용된 LED의 개수

# Neopixel 객체를 생성합니다.
pixels = neopixel.NeoPixel(PIN_NUMBER, NUM_LEDS, brightness=0.2, auto_write=False)
# GPIO 핀 모드 설정
GPIO.setmode(GPIO.BCM)
GPIO.setup(RELAY_PIN, GPIO.OUT)
GPIO.setup(laser_pin, GPIO.OUT)


# LED 바의 색을 설정하는 함수를 정의합니다.
def set_led_bar_color(color):
    for i in range(NUM_LEDS):
        pixels[i] = color
    pixels.show()

# LED 바를 꺼주는 함수를 정의합니다.
def turn_off_led_bar():
    set_led_bar_color((0, 0, 0))

# 레이저 레벨기를 끄는 함수를 정의합니다.
def turn_off_laser_level():
    GPIO.output(RELAY_PIN, GPIO.LOW)  # LOW 신호를 출력하여 릴레이 스위치를 끕니다.

#레이저 모듈을 끄는 함수를 정의합니다.
def laser_off(pin):
    GPIO.output(pin, False)
    
turn_off_laser_level() #레벨기 끄기
time.sleep(1)
laser_off(laser_pin) # laser모듈 끄기
time.sleep(1)
turn_off_led_bar() #led바 끄기

GPIO.cleanup()