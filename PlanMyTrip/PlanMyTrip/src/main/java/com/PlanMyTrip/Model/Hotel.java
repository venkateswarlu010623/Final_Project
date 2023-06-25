package com.PlanMyTrip.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @JsonManagedReference("hotelReference")
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "hotel")
    private List<Room> rooms;
}
