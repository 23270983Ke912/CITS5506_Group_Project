#!/usr/bin/python
import RPi.GPIO as GPIO
import time
import json

#calculate distance
def calcDist(p_end_t, p_start_t):                
    pulse_duration = p_end_t - p_start_t
    distance = round(pulse_duration * 17150, 2)
    return distance

#start level distance#
distList = []
def levelDif(distance_1):
    
    time.sleep(0.2)
    
    distList.append(float(distance_1))     

    if ( len(distList) > 1):
        if( len(distList) > 2 ):
             distList.pop(0)
        
        #calculate diff distance
        calcDist = distList[1] - distList[0] 

        if ( calcDist > 10 ): #hole
            distList.pop(0)
            return 1
        elif ( calcDist < -10 ):#step
            distList.pop(0)
            return 2
#end level distance#

try:
    print("A")
    GPIO.setmode(GPIO.BOARD)
    
    PIN_TRIGGER_1 = 7 #output
    PIN_ECHO_1 = 11 #input
    
    GPIO.setup(PIN_TRIGGER_1, GPIO.OUT)
    GPIO.setup(PIN_ECHO_1, GPIO.IN)
    
    #initialize sensor
    GPIO.output(PIN_TRIGGER_1, GPIO.LOW)
    
    time.sleep(2)

    
    while True:      
        #sensor hole
        GPIO.output(PIN_TRIGGER_1, GPIO.HIGH)
        time.sleep(0.00001)
        GPIO.output(PIN_TRIGGER_1, GPIO.LOW)
    
        while GPIO.input(PIN_ECHO_1)==0:
            pulse_start_time_1 = time.time()
        while GPIO.input(PIN_ECHO_1)==1:
            pulse_end_time_1 = time.time()
                
        pulse_duration_1 = pulse_end_time_1 - pulse_start_time_1
        distance_1 = round(pulse_duration_1 * 17150, 2)
        response_1 = levelDif(distance_1)
        
        details = {
            "sensor_l":response_1
            }
        json.dumps(details)
        
        if response_1 == 1:
            print("hole")
        elif response_1 ==2:
            print("step")
        
finally:
    GPIO.cleanup()

