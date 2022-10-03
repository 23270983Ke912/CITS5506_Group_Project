from django.shortcuts import render, redirect
from django.http import HttpResponse, JsonResponse
# Create your views here.
from django.contrib.auth.views import TemplateView
from django.views.generic import CreateView, ListView, View
from .forms import RegisterForm, EmergencyEventListForm
from django.urls import reverse_lazy
from .models import EmergencyEvent, UserProfile
from django.views.decorators.csrf import csrf_exempt


from django.utils.decorators import method_decorator  #类装饰方法
from django.contrib.auth.decorators import login_required


class indexView(TemplateView):
    template_name = 'blindstick/index.html'

"""
post data type:
{
    "username":"Kecheng",
    "firstname":"Kecheng",
    "lastname":"Lu",
    "email":"test@gmail.com",
    "password": "sdfsfdadfasdf"
}
"""
class UserView(View):
    def get(self, request):
        username = request.GET.get('username')
        queryset = UserProfile.objects.filter(username=username).first()
        if queryset:
            response = {}
            response['firstname'] = queryset.firstname
            response['lastname'] = queryset.lastname
            response['email'] = queryset.email
            return JsonResponse(response)
        return JsonResponse({"error":"Cannot find this user in database"})
    def post(self, request, *args, **kwargs):
        import json
        json_data = json.loads(request.body)
        username = json_data.get('username')
        firstname = json_data.get('firstname')
        lastname = json_data.get('lastname')
        email = json_data.get('email')
        password = json_data.get('password')
        userprofile = UserProfile(**json_data)
        userprofile.save()
        return HttpResponse("Success")



class RegisterUser(CreateView):
    form_class = RegisterForm
    success_url = reverse_lazy('login')
    template_name = 'blindstick/register.html'

"""
post data type:
    {
    "user":"user14",
    "device":"2",
    "location":"-32.037806, 115.801364",
    "time": "2022-09-17 16:00:00"
}
"""
class EmergencyEventListView(ListView):
    model =EmergencyEvent
    # form_class = EmergencyEventListForm
    template_name = 'blindstick/emergency_event.html'

    @method_decorator(login_required)
    def get(self, request):
        queryset = self.model.objects.all()
        return render(request, self.template_name, context={'queryset': queryset})
    @csrf_exempt
    def post(self, request, *args, **kwargs):
        import json
        json_data = json.loads(request.body)
        user = json_data.get('user')
        device = json_data.get('device')
        location = json_data.get('location')
        time = json_data.get('time')
        emergency_event = EmergencyEvent(**json_data)
        emergency_event.save()
        queryset = EmergencyEvent.objects.all()
        return render(request, self.template_name, context={'queryset': queryset})
