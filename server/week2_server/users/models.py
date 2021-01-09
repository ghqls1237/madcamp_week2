from django.db import models
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):
    """ Custom User Model"""
    # pkk = models.IntegerField(primary_key=True)
    uid = models.CharField(max_length=50,unique=True)
    LOGIN_FACEBOOK = "FaceBook"
    LOGIN_CHOICES = ((LOGIN_FACEBOOK, "FaceBook"), )
    login_method = models.CharField(choices=LOGIN_CHOICES,max_length=20,default=LOGIN_FACEBOOK)
    nickname = models.CharField(max_length=15, blank=True, null=True)


    