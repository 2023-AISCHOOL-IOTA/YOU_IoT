#18번 핀으로 laser
import RPi.GPIO as GPIO

laserPin=18

def laser_off(pin):
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(pin, GPIO.OUT)
    GPIO.cleanup(pin)

laser_off(laserPin) # led 소등