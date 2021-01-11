from django.urls import path
from . import views

#view와 1:1로 매핑됨
app_name = "seas"
urlpatterns = [
    path("", views.seas_view, name="seas")
]