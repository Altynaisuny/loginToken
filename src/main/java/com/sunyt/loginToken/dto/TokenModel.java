package com.sunyt.loginToken.dto;

public class TokenModel {
    private String token;

    private String userId;

    private boolean isLogin;

    public TokenModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
