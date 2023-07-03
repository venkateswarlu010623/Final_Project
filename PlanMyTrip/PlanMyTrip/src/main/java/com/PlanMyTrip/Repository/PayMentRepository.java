package com.PlanMyTrip.Repository;


import com.PlanMyTrip.Entity.PayMent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayMentRepository extends JpaRepository<PayMent,Integer> {

    Optional<PayMent> findByBookingBookingId(int bookingId);
}
