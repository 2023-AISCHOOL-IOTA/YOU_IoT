#20번 핀으로 led
#sudo pip3 install rpi_ws281x adafruit-circuitpython-neopixel
#sudo python3 -m pip install --force-reinstall adafruit-blinka  터미널에서 설치 먼저.
import board
import neopixel
import time

# LED 바에 연결된 핀 번호와 LED의 개수를 설정합니다.
PIN_NUMBER = board.D20  # 라즈베리파이의 GPIO 핀 번호 (D18은 BCM 핀 번호로 18번 핀을 의미합니다.)
NUM_LEDS = 15  # LED 바에 사용된 LED의 개수

# Neopixel 객체를 생성합니다.
pixels = neopixel.NeoPixel(PIN_NUMBER, NUM_LEDS, brightness=0.2, auto_write=False)

# LED 바의 색을 설정하는 함수를 정의합니다.
def set_led_bar_color(color):
    for i in range(NUM_LEDS):
        pixels[i] = color
    pixels.show()

# LED 바를 꺼주는 함수를 정의합니다.
def turn_off_led_bar():
    set_led_bar_color((0, 0, 0))

turn_off_led_bar()