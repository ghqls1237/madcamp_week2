from django.db import models

# Create your models here.

class Sea(models.Model):
    pkk = models.IntegerField(primary_key = True)
    name = models.CharField(max_length=15, unique=True, null=False, blank=False)
    # cascade 는 참조된 object가 삭제되면, 이 object를 참조하는 object들도 다 삭제하는거
    def __str__(self):
        return self.name
