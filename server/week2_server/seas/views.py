from django.shortcuts import render
from rest_framework.decorators import api_view
from django.views.decorators.csrf import csrf_exempt
from . import models as seas
from .serializers import SeaSerializer
from rest_framework.response import Response
from rest_framework import status
# Create your views here.


@api_view(["GET"])
@csrf_exempt

def seas_view(request):
    if request.method == "GET": # 정보 가져오기
        queryset = seas.Sea.objects.all()
        serializer = SeaSerializer(queryset, many=True)
        
        return Response(serializer.data)
