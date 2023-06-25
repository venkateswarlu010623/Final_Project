package com.PlanMyTrip.ExceptionHandling;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String s) {
        super(s);

    }
}
