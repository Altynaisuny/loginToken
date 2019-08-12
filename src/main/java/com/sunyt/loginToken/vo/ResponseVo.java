package com.sunyt.loginToken.vo;

import org.springframework.http.HttpStatus;

public class ResponseVo<T> {

    private static final String SUCCESS = "1";

    private static final String FAIL = "0";

    private String code;

    private String msg;

    private T body;

    public ResponseVo(String code, T body) {
        this.code = code;
        this.body = body;
    }

    public ResponseVo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseVo<T> success(T body){
        return new ResponseVo<>(SUCCESS, body);
    }

    public static <T> ResponseVo<T> success(HttpStatus httpStatus, T body){
        return new ResponseVo<>(httpStatus.toString(), body);
    }

    public static <T> ResponseVo<T> error(String msg){
        return new ResponseVo<>(FAIL, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
