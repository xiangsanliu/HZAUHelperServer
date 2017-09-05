package com.xiang.project.domain;

public class JWCheckCode {
    private byte[] checkCode;
    private String cookie;
    private String viewState;

    public String getViewState() {
        return viewState;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }

    public byte[] getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(byte[] checkCode) {
        this.checkCode = checkCode;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
