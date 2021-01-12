from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from . import models as contacts
from .serializers import UserSerializer
import json
from users import models as user_models

# request는 장고에 의해 자동으로 전달되는 HTTP 요청 객체
# request는 사용자가 전달한 데이터를 확인할 때 사용

@api_view(["GET", "POST", "DELETE","PUT"])
@csrf_exempt

def contact_view(request):
    if request.method == "GET": # 정보 가져오기
        uid = request.GET.get("uid")
        print("uid : ", uid)
        user=user_models.User.objects.get(uid=uid)
        queryset = contacts.Contact.objects.filter(user=user)
        serializer = UserSerializer(queryset, many=True)
        
        return Response(serializer.data)

    elif request.method == "POST": # 정보 추가하기
        params_json = request.body.decode(encoding = "utf-8")
        data_json = json.loads(request.body)
        name = data_json["name"]
        phone = data_json["phone"]
        image = data_json["image"]
        uid = data_json["uid"]
        user = user_models.User.objects.get(uid=uid)
        contact = contacts.Contact.objects.create(name = name, phone = phone, image = image, user = user)

        return Response("{Result:Post}")
        
    elif request.method == "DELETE": # 정보 삭제하기
        pk = request.GET.get("pk")
        contact = contacts.Contact.objects.get(pkk = pk)
        contact.delete()
        return Response("{Result:Put}")
