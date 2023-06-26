package com.PlanMyTrip.ExceptionHandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class PlanMyTripExceptionHandlingClass<map> {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> fieldValidationExceptionHandler(MethodArgumentNotValidException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->errorMap.put(error.getField(),error.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HotelNotFoundException.class)
    public Map<String,String> hotelNotFoundExceptionHandler(HotelNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookedRoomsNotFoundException.class)
    public Map<String,String> bookedRoomsNotFoundExceptionHandler(BookedRoomsNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingsNotFoundException.class)
    public Map<String,String> bookingsNotFoundExceptionHandler(BookingsNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingNotFoundException.class)
    public Map<String,String> bookingNotFoundExceptionExceptionHandler(BookingNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public Map<String,String> customerNotFoundExceptionHandler(CustomerNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HotelListNotFoundException.class)
    public Map<String,String> hotelListNotFoundExceptionHandler(HotelListNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoomNotAvailableException.class)
    public Map<String,String> roomNotAvailableExceptionHandler(RoomNotAvailableException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoomNotFoundException.class)
    public Map<String,String> roomNotFoundExceptionHandler(RoomNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingListNotFoundException.class)
    public Map<String,String> bookingListNotExceptionHandler(BookingListNotFoundException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingNotCancelledException.class)
    public Map<String,String> bookingNotCancelledExceptionHandler(BookingNotCancelledException exception)
    {
        Map<String,String> errorMap = new TreeMap<>();
        errorMap.put("error" ,exception.getMessage());
        return errorMap;
    }
}
