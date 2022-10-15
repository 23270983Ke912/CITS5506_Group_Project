#!/usr/bin/python
import RPi.GPIO as GPIO
import time

#start level distance#
distList = []
def levelDif(distance_1):
    
    time.sleep(0.2)
    print(distance_1)
    distList.append(float(distance_1))     

    if ( len(distList) > 1):
        if( len(distList) > 2 ):
             distList.pop(0)
        #print("dist: ",distList)
        #calculate diff distance
        calcDist = distList[1] - distList[0] 
        #print("dist: ",calcDist)
        if ( calcDist > 10 ): #hole
            #print("hole")
            distList.pop(0)
            return 1
        elif ( calcDist < -10 ):#step
            #print("step")
            distList.pop(0)
            return 2

    return 0
#end level distance#

#start vibrator#
def vibrator(qtd,vibrator,intensity):
    GPIO.setup(vibrator, GPIO.OUT)  # Set GPIO pin X to output mode.
    pwm = GPIO.PWM(vibrator, intensity)   # Initialize PWM on pwmPin intensity frequency

    dc=0                               # set dc variable to 0 for 0%
    pwm.start(dc)                      # Start PWM with 0% duty cycle

    while qtd > 0 :
        pwm.start(dc) 
        pwm.ChangeDutyCycle(intensity)
        time.sleep(0.5)
        pwm.stop()
        time.sleep(0.5)
        qtd -=1            
    
try:
    GPIO.setmode(GPIO.BOARD)
    
    PIN_TRIGGER_1 = 33 #output
    PIN_ECHO_1 = 35 #input
    
    GPIO.setup(PIN_TRIGGER_1, GPIO.OUT)
    GPIO.setup(PIN_ECHO_1, GPIO.IN)
    
    #initialize sensor
    GPIO.output(PIN_TRIGGER_1, GPIO.LOW)
    
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
        
        response_1 = levelDif(distance_1)
        
        #call the vibrator function
        if response_1 == 1:
            #qtd,vibrator,intensity
            #print("v 1, 3")
            vibrator(3,40,100)
        elif response_1 == 2:
            #print("v 2, 2")
            vibrator(2,40,100)
        
finally:
    GPIO.cleanup()
