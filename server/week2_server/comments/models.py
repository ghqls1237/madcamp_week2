from django.db import models
from core import models as core_models

# Create your models here.
class Comment(core_models.TimeStampedModel):
    text = models.TextField(max_length=1000)
    user = models.ForeignKey("users.User", on_delete=models.CASCADE, related_name="comments")
    post = models.ForeignKey("posts.Post", on_delete=models.CASCADE, related_name="comments")
    
    def __str__(self):
        return f"{self.user.username}'s comment on {self.post.title}"

    def serialize_custom(self):
        data = {
            "text" : self.text,
            "user" : self.user.username,
            "pk" : self.pk,
            "date" : self.created
        }
        return data