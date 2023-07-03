package com.PlanMyTrip.Repository;

import com.PlanMyTrip.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    Customer findByCustomerEmail(String customerEmail);
}
