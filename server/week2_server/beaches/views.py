from django.shortcuts import render
from rest_framework.decorators import api_view
from django.views.decorators.csrf import csrf_exempt
from . import models as beaches
from .serializers import BeachSerializer
from rest_framework.response import Response
from rest_framework import status
import json
from seas import models as seas
# Create your views here.


@api_view(["GET"])
@csrf_exempt

def beaches_view(request):
    if request.method == "GET": # 정보 가져오기
        pkk = request.GET.get("pkk")
        print("pkk", pkk)
        sea = seas.Sea.objects.get(pkk = pkk )
        queryset = beaches.Beach.objects.filter(sea = sea)
        serializer = BeachSerializer(queryset, many=True)
        
        return Response(serializer.data)
