from rest_framework import serializers
from . import models as post_models

class PostSerializer(serializers.ModelSerializer):
    class Meta:
        model = post_models.Post
        fields = ("pk", "user.nickname", "created", "title", "text")