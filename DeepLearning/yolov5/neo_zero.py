import board
import neopixel
import time
pixels = neopixel.NeoPixel(board.D18, 10)
#pixels[0] = (255, 0, 0)

pixels.fill((0,0,0))
