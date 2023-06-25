package com.PlanMyTrip.ExceptionHandling;

public class BookedRoomsNotFoundException extends RuntimeException {
    public BookedRoomsNotFoundException(String s) {
        super(s);

    }
}
