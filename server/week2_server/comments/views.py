from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from . import models as comment_models
from posts import models as post_models
import json
from users import models as user_models
from notifications import models as notification_models


@csrf_exempt
@api_view(["GET", "POST"])
def comments(request):
    if request.method == "GET":
        pk = request.GET.get("pk")
        post = post_models.Post.objects.get(pk=pk)
        queryset = comment_models.Comment.objects.filter(post=post)
        comments_serialized = []
        for q in queryset:
            comments_serialized.append(q.serialize_custom())
        # serializer = (queryset, many=True)
        return Response(comments_serialized)
    
    elif request.method == "POST":
        params_json = request.body.decode(encoding = "utf-8")
        data_json = json.loads(request.body)
        uid = data_json["uid"]
        post_pk = data_json["post"]
        text = data_json["text"]

        try:
            user = user_models.User.objects.get(uid=uid)
            post = post_models.Post.objects.get(pk=post_pk)
            post_owner = post.user
            post_comments = post.comments.all()
            post_comments_users = []
            print(post_comments)
            for comment in post_comments:
                if(comment.user != user):
                    if(comment.user in post_comments_users):
                        continue
                    else:
                        post_comments_users.append(comment.user)

            # post owner
            if(post_owner != user):
                post_comments_users.append(post_owner)

            for user_temp in post_comments_users:
                noti = notification_models.Notification.objects.create(post=post, user=user_temp)
                noti.save()
    
            comment = comment_models.Comment.objects.create(text=text, user=user, post=post)
            comment.save()
            return Response(comment.serialize_custom())
        except:
            return Response("{Result:Error}")
    
    