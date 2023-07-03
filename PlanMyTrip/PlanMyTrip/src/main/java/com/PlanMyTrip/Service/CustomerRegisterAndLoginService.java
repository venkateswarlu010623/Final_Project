package com.PlanMyTrip.Service;

import com.PlanMyTrip.Entity.Customer;
import com.PlanMyTrip.ExceptionHandling.CustomerNotFoundException;
import com.PlanMyTrip.ExceptionHandling.InvalidCredentialsException;
import com.PlanMyTrip.Model.CustomerLogin;
import com.PlanMyTrip.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class CustomerRegisterAndLoginService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    PayMentRepository payMentRepository;

    public Customer saveCustomer(Customer customer)
    {
        customer.setBookings(new LinkedList<>());
        return customerRepository.save(customer);
    }

    public String customerLogin(CustomerLogin customerLogin) throws InvalidCredentialsException {
        Customer customer = customerRepository.findByCustomerEmail(customerLogin.getCustomerEmail());

        if (customer != null)
        {
            if (customer.getPassword().equals(customerLogin.getPassword()))
            {
                return "Customer login successful. Redirecting to the home page...";
            }
            else
            {
                throw new InvalidCredentialsException("Invalid password. Please try again.");
            }
        }
        else
        {
            throw new CustomerNotFoundException("Customer account not found. Please register to continue.");
        }
    }



}
