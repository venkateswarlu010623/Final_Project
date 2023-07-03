package com.PlanMyTrip.Repository;

import com.PlanMyTrip.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    Booking findByBookingDate(LocalDateTime bookingDate);
}
