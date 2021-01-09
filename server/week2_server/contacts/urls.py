from django.urls import path
from . import views

#view와 1:1로 매핑됨
app_name = "contacts"
urlpatterns = [
    path("", views.contact_view, name="contacts")
]