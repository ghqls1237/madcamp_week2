from django.db import models
from core import models as core_models

class Sea(core_models.TimeStampedModel):
    name = models.CharField(max_length=15, unique=True, null=False, blank=False)

    def __str__(self):
        return self.name
