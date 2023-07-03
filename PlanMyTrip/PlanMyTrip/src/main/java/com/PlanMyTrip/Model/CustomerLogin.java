package com.PlanMyTrip.Model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class CustomerLogin {


    @NotBlank(message = "customer email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@cjsstechnologies\\.com$", message = "Invalid email format")
    private String customerEmail;

    @NotBlank(message = "Customer password is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "Invalid Customer password")
    private String password;

}
