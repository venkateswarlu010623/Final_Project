package com.PlanMyTrip.Entity;

import com.PlanMyTrip.Entity.Booking;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Customers_table")
public class Customer {

    @SequenceGenerator(name = "customerId", initialValue = 6000)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "customerId")
    @Id
    private int customerId;

    @NotBlank(message = "customer name is required")
    private String customerName;


    @NotBlank(message = "customer email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@cjsstechnologies\\.com$", message = "Invalid email format")
    private String customerEmail;


//    @NotBlank(message = "Customer password is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "Invalid Customer password")
    private String password;


    @PositiveOrZero(message = "Coins balance must be a positive value")
    private double coinsBalance;

    @JsonManagedReference("customerReference")
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
