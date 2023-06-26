package com.PlanMyTrip.ExceptionHandling;

public class BookingListNotFoundException extends RuntimeException {
    public BookingListNotFoundException(String s) {
        super(s);
    }
}
