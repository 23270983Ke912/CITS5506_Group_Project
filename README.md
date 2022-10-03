# CITS5506_Group_Project
IoT Group Project for group 11

# Configure env
1. Install pipenv by using  `pip install pipenv` 
2. Install all dependencies by executing `pipenv install` in the project folder with pipfile

# Database
login into postgres session\
`sudo -u postgres psql`\
create database\
`CREATE DATABASE blindstick;`\
create user with password\
`CREATE USER user1 WITH PASSWORD '123456';`\
give access\
`GRANT ALL PRIVILEGES ON DATABASE blindstick TO user1;`\
exit\
`\q`

# Run server
Database migration \
`python3 manage.py makemigrations blindstick` \
`python3 manage.py migrate`\
Load test data\
`python3 manage.py loaddata blindstick_data.json`
Run server\
`python3 manage.py runserver 127.0.0.1:50001`
