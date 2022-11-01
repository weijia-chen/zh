package com.weijia.zh.user.exception;

//数据库异常
public class DatabaseException extends RuntimeException{

    public DatabaseException(){}

    public DatabaseException(String message){
        super(message);
    }
}
