from django.db import models

# Create your models here.
from django.contrib.auth.models import User


class UserProfile(models.Model):

    username = models.CharField('user', max_length=64, blank=False, default='', unique=True)
    firstname = models.CharField('firstname', max_length=64, blank=False, default='')
    lastname = models.CharField('lastname', max_length=64, blank=False, default='')
    email = models.EmailField('email', blank=False, default='', unique=True)
    password = models.CharField('password', max_length=64, blank=False, default='' )

    def __str__(self):
        return self.username

class EmergencyEvent(models.Model):
    user = models.CharField('username', max_length=100, blank=False, null=False)
    device = models.CharField('device',  max_length=100, blank=False, null=False)
    location = models.CharField('location', max_length=100, blank=True, default='')
    time = models.DateTimeField('occur_time', blank=False, null=False)
    is_processed = models.BooleanField('is_processed', blank=False, null=False, default=False)