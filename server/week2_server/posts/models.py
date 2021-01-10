from django.db import models
from core import models as core_models

# Create your models here.
class Post(core_models.TimeStampedModel):
    title = models.CharField(max_length=50)
    text = models.TextField(max_length=300)
    user = models.ForeignKey("users.User", on_delete=models.CASCADE, related_name="posts")
    beach = models.ForeignKey("beaches.Beach", on_delete=models.CASCADE, related_name="posts")

    def __str__(self):
        return f"{self.user.username}'s post : {self.title}"
