package com.gw2apiparser;

public class FailedHttpCallException extends Exception{
    public FailedHttpCallException(String errorMessage){
        super(errorMessage);
    }

}
