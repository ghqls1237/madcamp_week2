from django.db import models
from django.contrib.auth.models import User
from users import models as user_models
# Create your models here.



class Contact(models.Model):
    pkk = models.IntegerField(primary_key = True)
    name = models.CharField(max_length=50)
    phone = models.CharField(max_length = 40)
    image = models.ImageField(blank=True, null=True)
    user = models.ForeignKey("users.User",on_delete=models.CASCADE, related_name = "contacts")  
    # cascade 는 참조된 object가 삭제되면, 이 object를 참조하는 object들도 다 삭제하는거
    def __str__(self):
        return self.name


#Contact.objects.filter(id = 1) -> 이미 조건에 해당하는 데이터를 모두 찾아준다.





    