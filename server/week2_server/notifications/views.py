from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from . import models as contacts
import json
from users import models as user_models

# request는 장고에 의해 자동으로 전달되는 HTTP 요청 객체
# request는 사용자가 전달한 데이터를 확인할 때 사용

@api_view(["GET"])
@csrf_exempt

def notification_view(request):
    if request.method == "GET": # 정보 가져오기
        print(request)
        # uid = request.GET.get("uid")
        # print("uid : ", uid)
        # user=user_models.User.objects.get(uid=uid)
        # queryset = contacts.Contact.objects.filter(user=user)
        # serializer = UserSerializer(queryset, many=True)
        
        # return Response(serializer.data)
