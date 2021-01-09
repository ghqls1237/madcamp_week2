from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from . import models


@admin.register(models.User)
class UserAdmin(UserAdmin):
    fieldsets = UserAdmin.fieldsets + (("CustomProfile", {
        "fields": (
            "nickname",
            "login_method",
            "uid",
        )
    }), )

    list_display = (
        "email",
        "nickname",
        "login_method",
    )