# CITS5506_Group_Project
IoT Group Project for group 11

##### There are three main modules in this IoT project: 

- ###### Server

- ###### mobile application 

- ###### IoT device (raspberry pi)

##### The system structure is as follows:

![](project_structure.png)

##### Setup on the server side：

###### Setup postgresql：

- Install postgresql through the following link: <https://www.postgresql.org/download/>

- Login into postgres session: 

  `sudo -u postgres psql`

- Create database: 

  `CREATE DATABASE blindstick；`

- Create user with password: 

  `CREATE USER user1 WITH PASSWORD ‘123456’；`

- Give access to the user: 

  `GRANT ALL PRIVILEGES ON DATABASE blindstick TO user1；`

- Exit postgres session: 

  `\q`

###### Setup Django server：（Dueto the github repository is private please contact the development team for access）

- Clone the repository to your server/local computer

  `git clone https://github.com/23270983Ke912/CITS5506\_Group\_Project.git`

- Change directory to CITS5506\_Group\_Project folder and checkout the web\_app branch

  `cd CITS5506\_Group\_Project`

  `git checkout web\_app`

- Install pipenv by using(pipenv package used for buid virtual environment）

  `pip install pipenv`

- go into project folder and install virtual environment with needed packages

  `cd project`

  `pipenv install`

- Start the virtual environment 

  `pipenv shell`

- Make database migration

  `python3 manage.py makemigrations blindstick`

  `python3 manage.py migrate`

- Load test data

  `python3 manage.py loaddata blindstick\_data.json`

- Run server on local network and remember your Ip address

  `python3 manage.py runserver <your\_local\_ip\_address>:8000`

##### Setup on the Client side：

- Install android studio via link <https://developer.android.com/studio>

- Clone the repository to your server computer

  `git clone https://github.com/23270983Ke912/CITS5506\_Group\_Project.git`

- cd into CITS5506\_Group\_Project folder and checkout the Android-APP branch

  `cd CITS5506\_Group\_Project`

  `git checkout Android-APP`

- Use android studio to open the project folder called Blindstick

- Build and compile

- Edit ServerInfo.java in /app/src/main/java/com/example/ccumis405530055 to change the serverurl to your ip address with port of your Django server

- Connect android phone to android studio 

- Run the app on an android phone

##### Setup on raspberry pi side:

- Enable i2c in your raspberry pi 
- Clone the repository to your raspberry pi:
  `git clone https://github.com/23270983Ke912/CITS5506\_Group\_Project.git`

- cd into CITS5506\_Group\_Project folder and checkout the prototype branch
- Install all necessary python dependency 
- Run setup code before using the execute the program shell file: sudo hciconfig hci0 piscan

- Run the program file


Useful links: 

- For enable I2C --- <https://www.raspberrypi-spy.co.uk/2014/11/enabling-the-i2c-interface-on-the-raspberry-pi>
- Package for deriving light sensor --- <https://core-electronics.com.au/guides/raspberry-pi-pico/piicodev-ambient-light-sensor-veml6030-quickstart-guide-for-rpi-pico/>

Hardware Wire: 

- ###### Distance Detection Sensor
  - 3x 1k OHM Resistor
  - VCC connects to Pin 2 (5V)
  - Trig connects to Pin 7 (GPIO 4)
  - Echo connects to R1 (1k OHM)
  - R2 and R3 (1k OHM + 1 k OHM) connects from R1 to Ground
  - Wire from R1 and R2+R3 connects to Pin 11 (GPIO 17)
  - GND connects to Pin 6 (Ground)

- ###### LightSensor
  - GND connectto GND (pin9)
  - 3.3V connect to 3.3V (pin1)
  - SDA connect to GPIO2 (pin3)
  - SCL connect to GPIO3 (pin5)
  - Relay Pin2 toRPi GPIO26 
  - Relay VCC to RPI 5Vpin•LED positive to Relay common pin (COM)
  - LED negative to 12V power supply negative 
  - 12V power supply positive to Relay Normally Open (NO)

- ###### Gyroscope
  - V to 5V (Pin 2)
  - G to GND (Pin 6)
  - SD to GPIO 23 (Pin 16)
  - SC to GPIO 24 (Pin 18)



