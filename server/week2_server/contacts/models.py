from django.db import models
from django.contrib.auth.models import User
from django_db_views.db_view import DBView

# Create your models here.


class Contact(models.Model):
    pkk = models.IntegerField(primary_key = True)
    name = models.CharField(max_length=50)
    phone = models.CharField(max_length = 40)
    user = models.ForeignKey("users.User",on_delete=models.CASCADE, related_name = "contacts")  
    # cascade 는 참조된 object가 삭제되면, 이 object를 참조하는 object들도 다 삭제하는거






    