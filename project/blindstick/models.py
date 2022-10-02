from django.db import models

# Create your models here.
from django.contrib.auth.models import User


class UserProfile(models.Model):

    USER_GENDER_TYPE = (
        ('male', 'M'),
        ('female', 'F'),
    )
    user = models.CharField('user', max_length=64, blank=False, default='')
    birthday = models.DateField('birth', null=True, blank=True)
    gender = models.CharField('gender', max_length=6, choices=USER_GENDER_TYPE, default='male')
    family_address = models.CharField('address', max_length=100, blank=True, default='')
    location = models.CharField('location', max_length=100, blank=True, default='')

    class Meta:
        verbose_name = 'User Profile'
        verbose_name_plural = verbose_name

    def __str__(self):
        return self.user

class EmergencyEvent(models.Model):
    user = models.CharField('username', max_length=100, blank=False, null=False)
    device = models.CharField('device',  max_length=100, blank=False, null=False)
    location = models.CharField('location', max_length=100, blank=True, default='')
    time = models.DateTimeField('occur_time', blank=False, null=False)
    is_processed = models.BooleanField('is_processed', blank=False, null=False, default=False)