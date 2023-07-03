package com.PlanMyTrip.Model;

import com.PlanMyTrip.Entity.Booking;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@NoArgsConstructor
@Data
public class CustomerBookingRequest {

    @Min(value = 6000,message = "invalid customer id")
    private int customerId;

    private String customerName;

    @NotBlank(message = "Hotel location should not blank")
    private String location;

    @NotBlank(message = "Hotel name should not blank")
    private String hotelName;

    private int room_number;

    @NotBlank(message = "Room type is required ")
    @Pattern(regexp = "^(Standard|Deluxe|Superior|Executive)", message = "Invalid room type")
    private String roomType;

    @NotBlank(message = "sharing is required ")
    @Pattern(regexp = "^(Single|Double|Three|Four)",message = "Invalid sharing")
    private String sharing;

    private Booking bookingDetails;

}
