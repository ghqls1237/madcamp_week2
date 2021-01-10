from django.contrib import admin
from . import models

# Register your models here.
@admin.register(models.Sea)
class SeaAdmin(admin.ModelAdmin):

    list_display = (
        "__str__",
    )