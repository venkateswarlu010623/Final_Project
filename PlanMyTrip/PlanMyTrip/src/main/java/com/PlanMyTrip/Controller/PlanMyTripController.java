package com.PlanMyTrip.Controller;


import com.PlanMyTrip.ExceptionHandling.BookingListNotFoundException;
import com.PlanMyTrip.ExceptionHandling.BookingNotCancelledException;
import com.PlanMyTrip.Model.*;
import com.PlanMyTrip.Service.PlanMyTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PlanMyTripController {

    @Autowired
    PlanMyTripService planMyTripService;


    @PostMapping("/hotels/register")
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel)
    {
        Hotel savedHotel = planMyTripService.saveHotel(hotel);

        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }


    @PostMapping("/hotel-rooms/register/hotelId-{hotelId}")
    public ResponseEntity<Hotel> createHotelRooms(@Valid @RequestBody List<Room> rooms, @PathVariable int hotelId)
    {

        Hotel updatedHotel = planMyTripService.saveRooms(rooms,hotelId);

        return new ResponseEntity<>(updatedHotel, HttpStatus.CREATED);
    }


    @DeleteMapping("/remove/hotel/hotelId-{hotelId}")
    public void removeHotel(@PathVariable int hotelId)
    {
         planMyTripService.deleteHotel(hotelId);
    }


    @PostMapping("/customer/register")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer)
    {

        Customer savedCustomer = planMyTripService.saveCustomer(customer);

        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }


    @PostMapping("/hotels")
    public ResponseEntity<List<Hotel>> getHotelsByRoomTypeAndSharingAndLocation( @Valid @RequestBody HotelGetRequest hotelGetRequest)
    {

        List<Hotel> hotels = planMyTripService.findHotelsByRoomTypeAndSharingAndLocation(hotelGetRequest);

        return new ResponseEntity<>(hotels, HttpStatus.FOUND);
    }


    @PostMapping("/customer/booking")
    public ResponseEntity<CustomerBookingRequest> addBooking(@Valid @RequestBody CustomerBookingRequest customerBookingRequest)
    {
        CustomerBookingRequest updatedCustomer = planMyTripService.createCustomerBooking(customerBookingRequest);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.ACCEPTED);
    }


    @PutMapping("/update/room/status")
    public ResponseEntity<List<Room>> updateRoom()
    {
        List<Room> updateRoomStatus =planMyTripService.updateRoomStatus();

        return new ResponseEntity<>(updateRoomStatus, HttpStatus.ACCEPTED);
    }


    @GetMapping("/customer/bookings/{customerId}/{bookingDate}")
    public ResponseEntity<List<String>> getCustomerBookings(@PathVariable int customerId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate bookingDate)
    {

        List<String> customerBookings =planMyTripService.customerBookings(customerId,bookingDate);

        return new ResponseEntity<>(customerBookings, HttpStatus.FOUND);
    }


    @GetMapping("/hotel/report/{hotelId}")
    public ResponseEntity<List<String>> findHotelReport(@PathVariable int hotelId)
    {
        List<String> hotelReport = planMyTripService.getHotelReport(hotelId);

        return new ResponseEntity<>(hotelReport, HttpStatus.FOUND);
    }


    @PutMapping("/cancel/customer/booking/customerId-{customerId}/bookingId-{bookingId}")
    public String removeCustomerBooking(@PathVariable int customerId,@PathVariable int bookingId)
    {
        return planMyTripService.cancelBooking(customerId,bookingId);
    }


    @GetMapping("/hotel/hotelId-{hotelId}")
    public ResponseEntity<Hotel> findOneHotel(@PathVariable int hotelId)
    {
        Hotel hotel = planMyTripService.getOneHotel(hotelId);

        return new ResponseEntity<>(hotel, HttpStatus.FOUND);
    }

    @GetMapping("/room/roomId-{roomId}")
    public ResponseEntity<Room> findOneRoom(@PathVariable int roomId)
    {
        Room room = planMyTripService.getOneRoom(roomId);

        return new ResponseEntity<>(room, HttpStatus.FOUND);
    }

    @GetMapping("/booking/bookingId-{bookingId}")
    public ResponseEntity<Booking> findOneBooking(@PathVariable int bookingId)
    {
        Booking booking = planMyTripService.getOneBooking(bookingId);

        return new ResponseEntity<>(booking,HttpStatus.FOUND);
    }

    @GetMapping("/customer/customerId-{customerId}")
    public ResponseEntity<Customer> findOneCustomer(@PathVariable int customerId)
    {
        Customer customer = planMyTripService.getOneCustomer(customerId);

        return new ResponseEntity<>(customer,HttpStatus.FOUND);
    }
}
