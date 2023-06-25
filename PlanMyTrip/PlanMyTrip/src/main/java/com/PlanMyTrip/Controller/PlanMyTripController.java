package com.PlanMyTrip.Controller;


import com.PlanMyTrip.Model.*;
import com.PlanMyTrip.Service.PlanMyTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PlanMyTripController {

    @Autowired
    PlanMyTripService planMyTripService;


    @PostMapping("/hotels/register")
    public Hotel createHotel(@Valid @RequestBody Hotel hotel)
    {
        return planMyTripService.saveHotel(hotel);
    }


    @PostMapping("/hotel-rooms/register/hotelId-{hotelId}")
    public Hotel createHotelRooms(@Valid @RequestBody List<Room> rooms, @PathVariable int hotelId)
    {
        return planMyTripService.saveRooms(rooms,hotelId);
    }


    @DeleteMapping("/remove/hotel/hotelId-{hotelId}")
    public void removeHotel(@PathVariable int hotelId)
    {
         planMyTripService.deleteHotel(hotelId);
    }


    @PostMapping("/customer/register")
    public Customer createCustomer(@Valid @RequestBody Customer customer)
    {
        return planMyTripService.saveCustomer(customer);
    }


    @PostMapping("/hotels")
    public List<Hotel> getHotelsByRoomTypeAndSharingAndLocation( @Valid @RequestBody HotelGetRequest hotelGetRequest)
    {
        return planMyTripService.findHotelsByRoomTypeAndSharingAndLocation(hotelGetRequest);
    }


    @PostMapping("/customer/booking")
    public CustomerBookingRequest addBooking(@Valid @RequestBody CustomerBookingRequest customerBookingRequest)
    {
        return planMyTripService.createCustomerBooking(customerBookingRequest);
    }


    @PutMapping("/update/room/status")
    public List<Room> updateRoom()
    {
        return planMyTripService.updateRoomStatus();
    }


    @GetMapping("/customer/bookings/{customerId}/{bookingDate}")
    public List<String> getCustomerBookings(@PathVariable int customerId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate bookingDate)
    {
        return planMyTripService.customerBookings(customerId,bookingDate);
    }


    @GetMapping("/hotel/report/{hotelId}")
    public List<String> findHotelReport(@PathVariable int hotelId)
    {
        return planMyTripService.getHotelReport(hotelId);
    }
}
