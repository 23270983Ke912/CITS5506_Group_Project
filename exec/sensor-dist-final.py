#!/usr/bin/python
import RPi.GPIO as GPIO
import time


#start check obstacle distance#
def obstacle(dist):
    #print("dist: ",dist)
    time.sleep(0.2)

    if (dist <= 150 and dist > 55):
        return 3
    if (dist <= 55):
        return 4
#end obstacle#
    
#start vibrator#
def vibrator(vibrator,intensity):
    GPIO.setup(vibrator, GPIO.OUT)  # Set GPIO pin X to output mode.
    pwm = GPIO.PWM(vibrator, intensity)   # Initialize PWM on pwmPin 100Hz frequency

    dc=0                               # set dc variable to 0 for 0%
    pwm.start(dc)                      # Start PWM with 0% duty cycle

    pwm.ChangeDutyCycle(intensity)
    time.sleep(1.5)            # wait 1.5 seconds 
 
    pwm.stop()                         # stop PWM
#end vibrator#

#start setGPIO#
def setGPIO(x,y):
    #set output and input number
    PIN_TRIGGER = x
    PIN_ECHO = y
    
    #setup gpio
    GPIO.setup(PIN_TRIGGER, GPIO.OUT)
    GPIO.setup(PIN_ECHO, GPIO.IN)
    
    #initialize sensor
    GPIO.output(PIN_TRIGGER, GPIO.LOW)
    
    time.sleep(2)
    
    return PIN_TRIGGER,PIN_ECHO
#end setGPIO#

#start startTrigger#
def startTrigger(PIN_TRIGGER,PIN_ECHO):    
    GPIO.output(PIN_TRIGGER, GPIO.HIGH)
    time.sleep(0.00001)
    GPIO.output(PIN_TRIGGER, GPIO.LOW)

    while GPIO.input(PIN_ECHO)==0:
        pulse_start_time = time.time()
    while GPIO.input(PIN_ECHO)==1:
        pulse_end_time = time.time()
        
    #calculate duratin=on
    pulse_duration = pulse_end_time - pulse_start_time
    return pulse_duration
#end startTrigger#

#star calculate distance#
def calcDist(pulse_duration):
    distance = round(pulse_duration * 17150, 2)
    return distance
#end calculate distance#


try:
    #start board
    GPIO.setmode(GPIO.BOARD) 
    
    PIN_TRIGGER_1,PIN_ECHO_1 = setGPIO(7,11) #left
    PIN_TRIGGER_2,PIN_ECHO_2 = setGPIO(29,31) #right
    
    vibr_left = 38
    vibr_right = 40
    
    while True:
        duration_1 = startTrigger(PIN_TRIGGER_1,PIN_ECHO_1)
        duration_2 = startTrigger(PIN_TRIGGER_2,PIN_ECHO_2)
    
        distance_1 = calcDist(duration_1)
        distance_2 = calcDist(duration_2)
        
        response_1 = obstacle(distance_1)
        response_2 = obstacle(distance_2)
        
        #activate vibrator
        if response_1 == 3:
            #print("left 38 70")
            vibrator(vibr_left,70) #which vibrator, intensity
        elif response_1 == 4:            
            #print("left 38 100")
            vibrator(vibr_left,100)
        
        if response_2 == 3:
            #print("right 40 70")
            vibrator(vibr_right,70)
        elif response_2 ==4:          
            #print("right 40 100")
            vibrator(vibr_right,100)

        
finally:
    GPIO.cleanup()

