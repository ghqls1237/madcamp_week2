from django.contrib import admin
from . import models

# Register your models here.
@admin.register(models.Beach)
class BeachAdmin(admin.ModelAdmin):
    list_display = (
        "__str__",
    )

