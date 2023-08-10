#18번 핀으로 laser
import RPi.GPIO as GPIO

laserPin=18

def laser_on(pin):
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(pin, GPIO.OUT)
    GPIO.output(pin, True)

laser_on(laserPin) # led 점등