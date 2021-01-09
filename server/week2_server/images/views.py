from django.shortcuts import render


def images(request):
    if request.method == "POST":
        print("images POST!!")
    elif request.method == "GET":
        print("image GET!!")
