package com.PlanMyTrip.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


@Data
@NoArgsConstructor
public class HotelManagementLogin {


    private int hotelId;

    @NotBlank(message = "Customer password is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "Invalid Customer password")
    private String password;
}
