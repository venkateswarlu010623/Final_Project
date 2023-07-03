package com.PlanMyTrip.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Payment_table")
public class PayMent {

    @SequenceGenerator(name = "payMentId", initialValue = 7000)
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "payMentId")
    @Id
    private int payMentId;
    private double amount;
    private LocalDate paymentDate;
    private String paymentStatus;
    private String paymentMethod;

    @JsonManagedReference("payMentReference")
    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Booking booking;


}
