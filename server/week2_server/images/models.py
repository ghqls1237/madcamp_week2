from django.db import models
from core import models as core_models

# Create your models here.
class Image(core_models.TimeStampedModel):
    image = models.ImageField(upload_to="images")
    user = models.ForeignKey("users.User", on_delete=models.CASCADE, related_name="images")
    

    def __str__(self):
        return f"{self.user.nickname}'s image"