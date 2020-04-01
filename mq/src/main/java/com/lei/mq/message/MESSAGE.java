package com.lei.mq.message;

public enum MESSAGE {

    PHONE("01"),WX("02"),THIRD("03");

    private String code;

    MESSAGE(String code) {
        this.code=code;
    }

    private String getCode(){
        return code;
    }
}
