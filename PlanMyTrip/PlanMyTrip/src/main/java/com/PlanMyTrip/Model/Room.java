package com.PlanMyTrip.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Rooms_table")
public class Room {

    @SequenceGenerator(name = "roomId", initialValue = 400)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "roomId")
    @Id
    private int roomId;

    @NotBlank(message = "Room type is required ")
    @Pattern(regexp = "^(Standard|Deluxe|Superior|Executive)", message = "Invalid room type")
    private String roomType;

    @NotBlank(message = "sharing is required ")
    @Pattern(regexp = "^(Single|Double|Three|Four)",message = "Invalid sharing")
    private String sharing;

    @Positive(message = "Original price must be a positive value")
    @Min(value = 1000,message = "price per day should minimum 1000")
    private double pricePerDay;


    @NotBlank(message = "room status is required ")
    @Pattern(regexp = "^(UnReserved|Reserved|Occupied|Available)",message = "Invalid room status")
    private String status;


    @JsonManagedReference("roomReference")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rooms",orphanRemoval = true)
    private List<Booking> bookings;


    @JsonBackReference("hotelReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @Override
    public String toString()
    {
        return "Room{" +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                ", sharing='" + sharing + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", status='" + status + '\'' +
                ", bookings=" + bookings +
                ", hotel=" + hotel +
                '}';
    }

}
