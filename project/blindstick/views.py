from django.shortcuts import render, redirect

# Create your views here.
from django.contrib.auth.views import TemplateView
from django.views.generic import CreateView
from .forms import RegisterForm
from django.urls import reverse_lazy

class indexView(TemplateView):
    template_name = 'blindstick/index.html'

    # def get_context_data(self, **kwargs):
    #     kwargs = super().get_context_data(**kwargs)
    #     kwargs['info'] =
    #
    #     return kwargs



# class UserLogin(View):
#     template_name = 'blindstick/login.html'
#     form_class = AuthenticationForm
#
#     def get(self, request):
#         form = self.form_class()
#         message = ''
#         return render(request, self.template_name, context={'login_form': form, 'message': message})
#
#     def post(self, request):
#         form = self.form_class(request.POST)
#         if form.is_valid():
#             username = form.cleaned_data['username']
#             password = form.cleaned_data['password']
#             user = authenticate(
#                 username,
#                 password
#             )
#             if user is not None:
#                 login(request, user)
#                 messages.info(request, f"You are now logged in as {username}.")
#                 return redirect('index')
#             else:
#                 messages.error(request,"Invalid username or password.")
#         else:
#             messages.error(request, "Invalid username or password.")
#         return render(request, self.template_name, context={'login_form': form, 'message': messages})




class RegisterUser(CreateView):
    form_class = RegisterForm
    success_url = reverse_lazy('login')
    template_name = 'blindstick/register.html'