package com.PlanMyTrip.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class HotelGetRequest {

    @NotBlank(message = "Room type is required ")
    @Pattern(regexp = "^(Standard|Deluxe|Superior|Executive)", message = "Invalid room type")
    private String roomType;


    @NotBlank(message = "sharing is required ")
    @Pattern(regexp = "^(Single|Double|Three|Four)",message = "Invalid sharing")
    private String sharing;

    @NotBlank(message = "room status is required ")
    @Pattern(regexp = "^(UnReserved|Reserved|Occupied|Available)",message = "Invalid room status")
    private String roomStatus;

    @NotBlank(message = "Hotel location should not blank")
    private String location;

    @NotNull(message = "Checkin date is required")
    @FutureOrPresent(message = "The date must be in the future or the present")
    private LocalDate checkInDate;

    @NotNull(message = "Checkout date is required")
    @FutureOrPresent(message = "The date must be in the future or the present")
    private LocalDate checkOutDate;

}
