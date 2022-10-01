package com.example.ccumis405530055.blindstick;

public class LoginSession {
    private static final LoginSession instance = new LoginSession();
    private String cookie;
    private String username;
    //private constructor to avoid client applications to use constructor
    private LoginSession(){
    }
    public void setCookie(String cookie){
        this.cookie=cookie;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public String getCookie(){
        return cookie;
    }
    public static LoginSession getInstance(){
        return instance;
    }
    public void Logout(){
        this.cookie="";
        this.username="";
    }
}
