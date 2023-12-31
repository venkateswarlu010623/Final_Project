package com.PlanMyTrip.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Bookings_table")
public class Booking {

    @SequenceGenerator(name = "bookingId", initialValue = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "bookingId")
    @Id
    private int bookingId;


    @NotNull(message = "Checkin date is required")
    @FutureOrPresent(message = "The date must be in the future or the present")
    private LocalDate checkInDate;

    @NotNull(message = "Checkout date is required")
    @FutureOrPresent(message = "The date must be in the future or the present")
    private LocalDate checkOutDate;

    @Positive(message = "Original price must be a positive value")
    private double originalPrice;

    @Positive(message = "Discount price must be a positive value")
    private double discountPrice;

    @NotNull(message = "Booking date is required")
    @PastOrPresent(message = "Booking date must be in the past or present")
    private LocalDateTime bookingDate;

    @NotBlank(message = "Booking status is required")
    @Pattern(regexp = "^(Confirmed|Pending|Cancelled|Completed|Payment Initiated)", message = "Invalid booking status")
    private String bookingStatus;


    @JsonBackReference("customerReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private Customer customer;


    @JsonBackReference("roomReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId")
    private Room rooms;


    @JsonBackReference("hotelReference")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @JsonBackReference("payMentReference")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payMentId")
    public PayMent payment;

}