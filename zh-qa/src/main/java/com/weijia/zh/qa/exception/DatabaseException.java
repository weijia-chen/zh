package com.weijia.zh.qa.exception;

//数据库异常
public class DatabaseException extends RuntimeException{

    public  DatabaseException(){}

    public DatabaseException(String message){
        super(message);
    }
}
