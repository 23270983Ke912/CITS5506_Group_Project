from PiicoDev_VEML6030 import PiicoDev_VEML6030
from time import sleep

import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BOARD)
pin_led = 37

GPIO.setup(pin_led,GPIO.OUT)
GPIO.output(pin_led, GPIO.HIGH)

light = PiicoDev_VEML6030()
try:
    while True:
        lightVal = light.read()
        print(str(lightVal)+ "lux")
    
        if lightVal < 15:
            print ("LED ON")
            GPIO.output(pin_led, GPIO.LOW)
        else:
            print("led off")
            GPIO.output(pin_led, GPIO.HIGH)
    
        sleep(1)
except KeyboardInterrupt:
    pass
finally:
    GPIO.cleanup()
