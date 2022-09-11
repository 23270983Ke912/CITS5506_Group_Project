from django.db import models

# Create your models here.
from django.contrib.auth.models import User


# 创建所有的模型的基类
class BaseModel(models.Model):

    create_time = models.DateTimeField(auto_now_add=True, verbose_name='创建时间')
    update_time = models.DateTimeField(auto_now_add=True, verbose_name='更新时间')
    is_delete = models.BooleanField(default=False, verbose_name='是否删除')

    class Meta:

        abstract = True


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