#18번 핀으로 laser
import RPi.GPIO as GPIO

def laser_on(pin):
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(pin, GPIO.OUT)
    GPIO.output(pin, True)

def laser_off(pin):
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(pin, GPIO.OUT)
    GPIO.cleanup(pin)

laser_on(18) # led 점등