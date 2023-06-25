package com.PlanMyTrip.ExceptionHandling;

public class BookingsNotFoundException extends RuntimeException {
    public BookingsNotFoundException(String s) {
        super(s);

    }
}
