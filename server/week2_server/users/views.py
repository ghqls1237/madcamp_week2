from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from . import models as user_models
from .serializers import UserSerializer

#serialize를 한다는 것은 json이나 xml 파일 등으로 바꾸어 주는 것.

@api_view(["GET", "POST"])
@csrf_exempt
def login(request):
    if request.method == "GET":
        queryset = user_models.User.objects.all()
        serializer = UserSerializer(queryset, many=True)
        return Response(serializer.data)
    elif request.method == "POST":
        print("method is POST")