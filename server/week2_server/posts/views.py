from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from users import models as user_models
from . import models as post_models
from . import serializers
from beaches import models as beach_models
import json
# Create your views here.

@csrf_exempt
@api_view(["POST", "GET"])
def posts(request):
    if request.method == "POST":
        params_json = request.body.decode(encoding = "utf-8")
        data_json = json.loads(request.body)
        print(data_json)
        beach = data_json["beach"]
        text = data_json["text"]
        title = data_json["title"]
        uid = data_json["uid"]
        try:
            user = user_models.User.objects.get(uid=uid)
            beach = beach_models.Beach.objects.get(pk=beach)
            post = post_models.Post.objects.create(title=title, text=text, user=user, beach=beach)
            post.save()
            print("OK")
            return Response("{Result:POST}")
        except:
            print("error")
            return Response("{Result:Error}")

    elif request.method == "GET":
        beach = request.GET.get("beach")
        pk = int(beach)
        beach_obj = beach_models.Beach.objects.get(pk=pk)
        # queryset = beach_obj.posts.all()
        queryset = post_models.Post.objects.filter(beach=beach_obj).order_by("-created")
        post_serialized = []
        for q in queryset:
            post_serialized.append(q.serialize_custom())
        # serializer = (queryset, many=True)
        return Response(post_serialized)


@csrf_exempt
@api_view(["GET"])
def get_post(request):
    if request.method == "GET":
        pk = request.GET.get("pk")
        post = post_models.Post.objects.get(pk=pk)
        serialized_post = post.serialize_custom()
        return Response(serialized_post)