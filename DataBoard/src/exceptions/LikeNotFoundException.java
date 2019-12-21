package com.company;

public class LikeNotFoundException extends Exception {
    public LikeNotFoundException(){
        super();
    }
    public LikeNotFoundException(String message){
        super(message);
    }
}
