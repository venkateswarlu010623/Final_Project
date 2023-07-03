package com.PlanMyTrip.ExceptionHandling;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String s) {

        super(s);
    }
}
