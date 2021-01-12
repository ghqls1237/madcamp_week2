from django.db import models
from core import models as core_models

# Create your models here.

class Notification(core_models.TimeStampedModel):
    post = models.ForeignKey("posts.Post", on_delete=models.CASCADE, related_name = "notifications")
    user = models.ForeignKey("users.User", on_delete=models.CASCADE, related_name = "notifications")
    from_user = models.CharField(max_length=50, default="")
    # cascade 는 참조된 object가 삭제되면, 이 object를 참조하는 object들도 다 삭제하는거
    
    def __str__(self):
        return f"Create Noti to {self.post.title}"


    def serialize_custom(self):
        notifications = {
            "user" : self.from_user,
            "post_pk" : self.post.pk,
            "date" : self.created,
            "beach" :self.post.beach.pkk,
            "pkk" : self.post.beach.sea.pkk,
            "beach_title" : self.post.beach.name
        }
        return notifications

