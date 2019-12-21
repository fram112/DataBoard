package com.company;

public class AlreadyLikedException extends Exception {
    public AlreadyLikedException(){
        super();
    }
    public AlreadyLikedException(String message){
        super(message);
    }
}
