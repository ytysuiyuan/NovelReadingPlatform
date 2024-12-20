package com.example.novelreading.common.result;

public enum ErrorCode {

    /**
     * 请求成功
     */
    SUCCESS("200", "请求成功"),
    /**
     * 请求失败
     */
    ERROR("500", "请求失败");


    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    ErrorCode(String _code, String _message) {
        this.code = _code;
        this.message = _message;
    }
}
