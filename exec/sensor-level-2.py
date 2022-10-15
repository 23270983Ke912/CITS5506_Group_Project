#!/usr/bin/python
import RPi.GPIO as GPIO
import time

#start level distance#
distList = []
def levelDif(distance_1):
    
    time.sleep(0.2)
    
    #check if it is a valid measure
    if(distance_1 < 500):
        distList.append(float(distance_1))     
    
    if ( len(distList) > 1):
        if( len(distList) > 2 ):
             distList.pop(0)

        #calculate diff distance
        calcDist = distList[1] - distList[0] 

        if ( calcDist > 4 and calcDist < 60): #hole
            distList.pop(0)
            return 1
        elif ( calcDist < -4 and calcDist > -60 ):#step
            distList.pop(0)
            return 2

    return 0
#end level distance#

#start vibrator#
def vibrator(qtd):
    dc=0                               # set dc variable to 0 for 0%
    pwm.start(dc)                      # Start PWM with 0% duty cycle

    while qtd > 0 :
        pwm.start(dc) 
        pwm.ChangeDutyCycle(100)
        time.sleep(0.5)
        pwm.stop()
        time.sleep(0.5)
        qtd -=1            
    
try:
    GPIO.setmode(GPIO.BOARD)
    
    PIN_TRIGGER_1 = 33 #output
    PIN_ECHO_1 = 35 #input
    PIN_PWM = 38
    
    GPIO.setup(PIN_TRIGGER_1, GPIO.OUT)
    GPIO.setup(PIN_ECHO_1, GPIO.IN)
    GPIO.setup(PIN_PWM, GPIO.OUT)  # Set GPIO pin X to output mode.
    pwm = GPIO.PWM(PIN_PWM, 100)   # Initialize PWM on pwmPin intensity frequency
    
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
            #qtd
            vibrator(3)
        elif response_1 == 2:
            #qtd
            vibrator(2)
        
finally:
    GPIO.cleanup()
