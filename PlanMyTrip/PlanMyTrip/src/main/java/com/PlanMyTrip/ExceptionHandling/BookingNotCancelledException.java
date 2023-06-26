package com.PlanMyTrip.ExceptionHandling;

public class BookingNotCancelledException extends RuntimeException {
    public BookingNotCancelledException(String s) {
        super(s);
    }
}
