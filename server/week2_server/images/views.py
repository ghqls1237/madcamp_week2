from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
import json
from users import models as user_models
from . import models as image_models
from rest_framework.response import Response
from . import serializers

@csrf_exempt
@api_view(["GET", "POST", "DELETE"])
def images(request):
    if request.method == "POST":
        params_json = request.body.decode(encoding = "utf-8")
        data_json = json.loads(request.body)
        # print(data_json)
        uid = data_json["uid"]
        try:
            user = user_models.User.objects.get(uid=uid)
            image = data_json["image"]
            image_obj = image_models.Image.objects.create(image=image, user=user)
            image_obj.save()
            return Response(f"{image_obj.pk}")
        except:
            # user does not exists error
            print("ERROR : User Does not exists")
            return Response("{Result:Error}")
    elif request.method == "GET":
        uid = request.GET.get("uid")
        user=user_models.User.objects.get(uid=uid)
        queryset = image_models.Image.objects.filter(user=user)
        serializer = serializers.ImageSerializer(queryset, many=True)
        
        return Response(serializer.data)
    
    elif request.method == "DELETE":
        pk = request.GET.get("pk")
        try:
            image = image_models.Image.objects.get(pk=pk)
            image.delete()
            Response("{Result:Success}")
        except:
            return Response("{Result:Error}")
