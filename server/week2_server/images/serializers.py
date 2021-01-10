from rest_framework import serializers
from . import models as image_models

class ImageSerializer(serializers.ModelSerializer):
    class Meta:
        model = image_models.Image
        fields = ("image","pk")