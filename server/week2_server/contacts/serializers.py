from rest_framework import serializers
from .models import Contact

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = Contact
        fields = ('pkk','name','phone','image','user')
