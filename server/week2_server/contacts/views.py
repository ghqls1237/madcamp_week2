from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from . import models as contacts
from .serializers import UserSerializer


# request는 장고에 의해 자동으로 전달되는 HTTP 요청 객체
# request는 사용자가 전달한 데이터를 확인할 때 사용

@api_view(["GET", "POST", "PUT"])
@csrf_exempt
def contact_view(request):
    if request.method == "GET": # 정보 가져오기
        queryset = contacts.Contact.objects.all()
        serializer = UserSerializer(queryset, many=True)
        
        return Response(serializer.data)

    elif request.method == "POST": # 정보 추가하기
        print("method is POST")

    elif request.method == "PUT": # 정보 수정하기
        print("method is PUT")

    elif request.method == "DELETE":
        print("method is Delete")