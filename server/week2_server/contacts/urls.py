from django.urls import path
from . import views

#view와 1:1로 매핑됨
urlpatterns = [
    path('', views.contact_view)
]