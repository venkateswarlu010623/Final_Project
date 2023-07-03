package com.PlanMyTrip.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Hotels_table")
public class Hotel {

    @SequenceGenerator(name = "hotelId", initialValue = 80000)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "hotelId")
    @Id
    private int hotelId;

    @NotBlank(message = "Hotel name should not blank")
    private String hotelName;

    @NotBlank(message = "Hotel location should not blank")
    private String location;

    @NotBlank(message = "hotel manager password is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{6,12}$",message = "Invalid hotel manager password ")
    private String hotelManagerPassword;

    @JsonManagedReference("hotelReference")
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "hotel", orphanRemoval = true)
    private List<Room> rooms;

    @JsonManagedReference("hotelReference")
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;


}
