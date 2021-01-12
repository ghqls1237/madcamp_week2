from django.urls import path
from . import views

#view와 1:1로 매핑됨
app_name = "notifications"
urlpatterns = [
    path("", views.notification_view, name="notifications")
]