# Generated by Django 3.1.5 on 2021-01-11 14:48

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Contact',
            fields=[
                ('pkk', models.IntegerField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=50)),
                ('phone', models.CharField(max_length=40)),
                ('image', models.TextField(blank=True, null=True)),
            ],
        ),
    ]
