import board
import neopixel
import time
pixels = neopixel.NeoPixel(board.D18, 10)
#pixels[0] = (255, 0, 0)
for i in range(0,3):
    pixels.fill((0,0,255))
    time.sleep(0.5)
    pixels.fill((0,0,0))
    time.sleep(0.5)


