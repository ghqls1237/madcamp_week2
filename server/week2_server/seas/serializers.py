from rest_framework import serializers
from .models import Sea

class SeaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Sea
        fields = ('pkk','name')
