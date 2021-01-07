from django.contrib import admin
from . import models

# Register your models here.
@admin.register(models.Image)
class ImageAdmin(admin.ModelAdmin):
    """ Image Admin"""
    list_display = ("__str__", "image")
