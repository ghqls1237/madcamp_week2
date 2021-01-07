from django.contrib import admin
from .models import Contact

# Register your models here.
@admin.register(models.Contact)
class ContactAdmin(admin.ModelAdmin):
    """Contact Admin"""

# Register your models here.
@admin.register(models.Image)
class ImageAdmin(admin.ModelAdmin):
    """ Image Admin"""
    list_display = ("__str__", "image")
