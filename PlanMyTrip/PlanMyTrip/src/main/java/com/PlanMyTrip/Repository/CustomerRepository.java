package com.PlanMyTrip.Repository;

import com.PlanMyTrip.Model.Booking;
import com.PlanMyTrip.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
