#!/usr/bin/python
import RPi.GPIO as GPIO
import time
import json

#
#start level distance#
distList = []
def levelDif(dist):
    
    time.sleep(0.2)
    
    if (dist <= 120 and dist > 90):
        return 3
    if (dist <= 90):
        return 4

#end level distance#
try:
    print("B")
    GPIO.setmode(GPIO.BOARD)
    
    
    PIN_TRIGGER_1 = 29 #output
    PIN_ECHO_1 = 31 #input
    
    PIN_TRIGGER_2 = 33 #output
    PIN_ECHO_2 = 35 #input

    
    GPIO.setup(PIN_TRIGGER_1, GPIO.OUT)
    GPIO.setup(PIN_ECHO_1, GPIO.IN)
    GPIO.setup(PIN_TRIGGER_2, GPIO.OUT)
    GPIO.setup(PIN_ECHO_2, GPIO.IN)

    
    #initialize sensor
    GPIO.output(PIN_TRIGGER_1, GPIO.LOW)
    GPIO.output(PIN_TRIGGER_2, GPIO.LOW)

    
    time.sleep(2)

    
    while True:      
        
        GPIO.output(PIN_TRIGGER_1, GPIO.HIGH)
        time.sleep(0.00001)
        GPIO.output(PIN_TRIGGER_1, GPIO.LOW)
    
        while GPIO.input(PIN_ECHO_1)==0:
            pulse_start_time_1 = time.time()
        while GPIO.input(PIN_ECHO_1)==1:
            pulse_end_time_1 = time.time()
    
                
        pulse_duration_1 = pulse_end_time_1 - pulse_start_time_1
        distance_1 = round(pulse_duration_1 * 17150, 2)
        
        GPIO.output(PIN_TRIGGER_2, GPIO.HIGH)
        time.sleep(0.00001)        
        GPIO.output(PIN_TRIGGER_2, GPIO.LOW)
        
        while GPIO.input(PIN_ECHO_2)==0:
            pulse_start_time_2 = time.time()
        while GPIO.input(PIN_ECHO_2)==1:
            pulse_end_time_2 = time.time()
                
        pulse_duration_2 = pulse_end_time_2 - pulse_start_time_2
        distance_2 = round(pulse_duration_2 * 17150, 2)

        
        response_1 = levelDif(distance_1)
        response_2 = levelDif(distance_2)

        details = {
            "sensor_d1":response_1,
            "sensor_d2":response_2
            }
        json.dumps(details)
        if response_1 == 3:
            print("close A")
        elif response_1 ==4:
            print("super close A")
        
        if response_2 == 3:
            print("close B")
        elif response_2 ==4:
            print("super close B")

        
finally:
    GPIO.cleanup()


