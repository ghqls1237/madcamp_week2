from django.db import models
from core import models as core_models

# Create your models here.
class Beach(core_models.TimeStampedModel):
    name = models.CharField(max_length=50, unique=True, blank=False, null=False)
    sea = models.ForeignKey("seas.Sea", on_delete=models.CASCADE, related_name="beaches")

    def __str__(self):
        return f"{self.name} at {self.sea.name}"
