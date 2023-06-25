package com.PlanMyTrip.ExceptionHandling;

public class HotelListNotFoundException extends RuntimeException {
    public HotelListNotFoundException(String hotelsNotFound) {
        super(hotelsNotFound);

    }
}
