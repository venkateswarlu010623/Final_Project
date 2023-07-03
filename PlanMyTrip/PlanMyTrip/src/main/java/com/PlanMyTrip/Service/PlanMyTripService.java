package com.PlanMyTrip.Service;

import com.PlanMyTrip.Entity.*;
import com.PlanMyTrip.ExceptionHandling.*;
import com.PlanMyTrip.Model.*;
import com.PlanMyTrip.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanMyTripService {

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



//1.Register a Hotels and customers

    public Hotel saveHotel(Hotel hotel)
    {
        hotel.setRooms(new LinkedList<>());
        hotel.setBookings(new LinkedList<>());
        return hotelRepository.save(hotel);
    }

    public Hotel saveRooms(List<Room> rooms, int hotelId)
    {
        Optional<Hotel> savedHotel = hotelRepository.findById(hotelId);
        if (savedHotel.isPresent())
        {
            Hotel hotel = savedHotel.get();
            rooms.forEach(rooms1 ->{
                rooms1.setHotel(hotel);
                rooms1.setBookings(new LinkedList<>());
            });
            hotel.getRooms().addAll(rooms);
            return hotelRepository.save(hotel);
        }
        else
        {
            throw new HotelNotFoundException("Hotel not found with hotelId "+hotelId);
        }
    }
    public void deleteHotel(int hotelId)
    {
        hotelRepository.deleteById(hotelId);
    }


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



    public String hotelManagementLogin(HotelManagementLogin hotelManagementLogin) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelManagementLogin.getHotelId());

        if (hotel.isPresent()) {
            Hotel existingHotel = hotel.get();

            if (existingHotel.getHotelManagerPassword().equals(hotelManagementLogin.getPassword()))
            {
                return "Hotel management login successful. Redirect to the home page.";
            }
            else
            {
                throw new InvalidCredentialsException("Invalid password. Please check your login details.");
            }
        }
        else
        {
            throw new HotelNotFoundException("Hotel not found. Please register your hotel.");
        }
    }



    //2.Retrieve hotels based on Room Type, sharing, location and sort based on price [Hotels should not be retrieved if no vacancy]

    public List<Hotel> findHotelsByRoomTypeAndSharingAndLocation(HotelGetRequest hotelGetRequest) {
        List<Hotel> savedHotels = hotelRepository.findByLocation(hotelGetRequest.getLocation());

        if (!savedHotels.isEmpty())
        {
            return savedHotels.stream()
                    .filter(hotel ->!hotel.getRooms().isEmpty())
                    .map(hotel -> {
                        List<Room> savedRooms = hotel.getRooms().stream()
                                .filter(room -> (room.getSharing().equalsIgnoreCase(hotelGetRequest.getSharing()) &&
                                                room.getRoomType().equalsIgnoreCase(hotelGetRequest.getRoomType())) &&
                                                room.getRoomStatus().equalsIgnoreCase(hotelGetRequest.getRoomStatus()))
                                .collect(Collectors.toList());

                        System.out.println(hotelGetRequest.getRoomStatus());
                        if (!savedRooms.isEmpty())
                        {
                            List<Room> existingRooms = savedRooms.stream()
                                    .sorted(Comparator.comparingDouble(Room::getPricePerDay))
                                    .collect(Collectors.toList());
                            hotel.setRooms(existingRooms);
                            return hotel;
                        }
                        else
                        {
                            throw new RoomNotFoundException("Rooms not found with "+hotelGetRequest.getSharing()+" sharing in room type "+hotelGetRequest.getRoomType());
                        }
                    })
                    .collect(Collectors.toList());
        }
        else
        {
            throw new HotelListNotFoundException("Hotel list not found with "+hotelGetRequest.getLocation()+" location");
        }
    }


    //    3.API for customer to book hotel rooms. [customer gets a coins 2% on original price after he successfully checked-in]

    public CustomerBookingRequest createCustomerBooking(CustomerBookingRequest customerBookingRequest) throws HotelNotFoundException, RoomNotFoundException, RoomNotAvailableException, CustomerNotFoundException, InterruptedException {

        Optional<Hotel> savedHotel = hotelRepository.findByLocationAndHotelName(customerBookingRequest.getLocation(),customerBookingRequest.getHotelName());

        if(savedHotel.isPresent())
        {
            Hotel existingHotel = savedHotel.get();

            Room existingRoom = existingHotel.getRooms().stream().filter(savedRooms->savedRooms.getRoomType().equalsIgnoreCase(customerBookingRequest.getRoomType()) &&
                                                                savedRooms.getSharing().equalsIgnoreCase(customerBookingRequest.getSharing()))
                                                    .findFirst().orElse(null);

            if (existingRoom != null)
            {
               Booking savedBooking = existingRoom.getBookings().stream().filter(existingBookings ->{

                    if(customerBookingRequest.getBookingDetails().getCheckInDate().isBefore(existingBookings.getCheckInDate()) && customerBookingRequest.getBookingDetails().getCheckOutDate().isBefore(existingBookings.getCheckInDate()) && (customerBookingRequest.getBookingDetails().getCheckInDate().isEqual(LocalDate.now()) || customerBookingRequest.getBookingDetails().getCheckInDate().isAfter(LocalDate.now()) ||customerBookingRequest.getBookingDetails().getCheckInDate().isEqual(customerBookingRequest.getBookingDetails().getCheckOutDate())))
                    {
                        return false;
                    }
                    else if(customerBookingRequest.getBookingDetails().getCheckInDate().isAfter(existingBookings.getCheckOutDate())  && customerBookingRequest.getBookingDetails().getCheckOutDate().isAfter(existingBookings.getCheckOutDate()))
                    {
                        return false;
                    }
                    else
                    {
                       return true;
                    }
                }).findFirst().orElse(null);

               if (savedBooking == null)
               {
                   Optional<Customer> registeredCustomer =  customerRepository.findById(customerBookingRequest.getCustomerId());

                   if (registeredCustomer.isPresent())
                   {
                       Customer customer = registeredCustomer.get();

                       long days = ChronoUnit.DAYS.between(customerBookingRequest.getBookingDetails().getCheckInDate(), customerBookingRequest.getBookingDetails().getCheckOutDate());

                       double originalPrice = existingRoom.getPricePerDay()*days;

                       double discount = originalPrice*((double) 2 /100);

                       double discountedPrice = originalPrice - discount;

                       synchronized (customer)
                       {
                           Booking booking = new Booking();

                           LocalDateTime today = LocalDateTime.now();

                           booking.setCheckInDate(customerBookingRequest.getBookingDetails().getCheckInDate());
                           booking.setCheckOutDate(customerBookingRequest.getBookingDetails().getCheckOutDate());
                           booking.setOriginalPrice(originalPrice);
                           booking.setDiscountPrice(discountedPrice);
                           booking.setBookingDate(today);
                           booking.setRooms(existingRoom);
                           booking.setCustomer(customer);
                           booking.setHotel(existingRoom.getHotel());
                           booking.setBookingStatus("Payment Initiated");
                           customer.getBookings().add(booking);

                           List<Booking> bookingList = customer.getBookings().stream()
                                   .sorted(Comparator.comparing(Booking::getBookingDate).reversed())
                                   .collect(Collectors.toList());

                           customer.setBookings(bookingList);

                       Customer updatecustomer =   customerRepository.save(customer);

                           Thread paymentThread = new Thread(() -> {

                               try {
                                   Thread.sleep(30000);
                               } catch (InterruptedException e) {
                                   throw new PayMentThreadInterruptedException("Payment thread interrupted successfully after payment");
                               }

                               Optional<PayMent> payment = payMentRepository.findByBookingBookingId(updatecustomer.getBookings().get(0).getBookingId());

                               PayMent customerPayMent = null;

                               if (payment.isPresent())
                               {
                                   customerPayMent = payment.get();

                                   if (customerPayMent.getPaymentStatus().equals("Paid"))
                                   {
                                       updatecustomer.getBookings().get(0).setBookingStatus("Confirmed");
                                   }
                                   else
                                   {
                                       updatecustomer.getBookings().get(0).setBookingStatus("Pending");
                                   }
                               }
                               else
                               {
                                   updatecustomer.getBookings().get(0).setBookingStatus("Pending");
                               }

                               updatecustomer.getBookings().get(0).setPayment(customerPayMent);
                               bookingRepository.save(updatecustomer.getBookings().get(0));
                           });

                           paymentThread.start();
                           paymentThread.join();

                       }

                       customerBookingRequest.setBookingDetails(customer.getBookings().get(0));
                       customerBookingRequest.setCustomerName(customer.getCustomerName());
                       customerBookingRequest.setRoom_number(existingRoom.getRoomId());

                       return customerBookingRequest;
                   }
                   else
                   {
                       throw new CustomerNotFoundException("Customer not registered in PlanMyTrip/ redirect to register page");
                   }
               }
               else
               {
                   throw new RoomNotAvailableException("Room not available between given checkin date "+ customerBookingRequest.getBookingDetails().getCheckInDate()+" and checkout date "+customerBookingRequest.getBookingDetails().getCheckOutDate());
               }
            }
            else
            {
                throw new RoomNotFoundException("Room not found with "+customerBookingRequest.getSharing()+" sharing in "+customerBookingRequest.getRoomType()+" rooms");
            }
        }
        else
        {
            throw new HotelNotFoundException("Hotel not found with name "+customerBookingRequest.getHotelName()+" in location "+customerBookingRequest.getLocation());
        }
    }


        public PayMent  createPayMent(int customerId,PayMent customerPayMent)
    {

       Customer  customer = customerRepository.findById(customerId).get();


        List<Booking> bookingList = customer.getBookings().stream()
                .sorted(Comparator.comparing(Booking::getBookingDate).reversed())
                .collect(Collectors.toList());

        Booking currentBooking = bookingList.get(0);

        LocalDate today = LocalDate.now();

        currentBooking.setPayment(customerPayMent);

        PayMent payMent = currentBooking.getPayment();

        if(currentBooking.getOriginalPrice() == payMent.getAmount())
        {
            payMent.setPaymentStatus("Paid");
        }
        else
        {
            payMent.setPaymentStatus("Failed");
        }
        payMent.setPaymentDate(today);
        payMent.setBooking(currentBooking);
        return payMentRepository.save(payMent);
    }


    public PayMent  updatePayMent(int bookingId,PayMent customerPayMent)
    {

        Optional<Customer>  customer = customerRepository.findById(bookingId);
        Booking booking = bookingRepository.findById(bookingId).get();
        LocalDate today = LocalDate.now();

        booking.setPayment(customerPayMent);

        PayMent payMent = booking.getPayment();

        if(booking.getOriginalPrice() == payMent.getAmount())
        {
            payMent.setPaymentStatus("Paid");
        }
        else
        {
            payMent.setPaymentStatus("Failed");
        }


        if (payMent.getPaymentStatus().equals("Paid"))
        {
            booking.setBookingStatus("Confirmed");
        }
        else
        {
            booking.setBookingStatus("Pending");
        }
        payMent.setPaymentDate(today);
        payMent.setBooking(booking);
        return payMentRepository.save(payMent);
    }



//4.API to update room status when customer check-in and check-out

    public List<Room> updateRoomStatus() {
        List<Room> rooms = roomRepository.findAll();

        List<Customer> customers = customerRepository.findAll();

        rooms.forEach(room -> {

            if(room.getBookings().isEmpty())
            {
                room.setRoomStatus("UnReserved");
            }
        });
        List<Room> bookedRooms = rooms.stream()
                .filter(room -> !room.getBookings().isEmpty())
                .collect(Collectors.toList());

        bookedRooms.forEach(room -> {
            room.getBookings().forEach(booking -> {
                LocalDate today = LocalDate.now();

                if (booking.getCheckInDate().isEqual(today))
                {
                    room.setRoomStatus("Occupied");
                }
                else if (today.isAfter(booking.getCheckOutDate()))
                {
                    room.setRoomStatus("Available");
                }
                else
                {
                    room.setRoomStatus("Reserved");
                }
            });

            roomRepository.save(room);
        });

        List<Customer> bookedCustomer = customers.stream()
                .filter(customer -> !customer.getBookings().isEmpty())
                .collect(Collectors.toList());

        bookedCustomer.forEach(customer -> {
            customer.getBookings().forEach(booking -> {

                if(booking.getCheckInDate().isEqual(LocalDate.now()))
                {
                    long days = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());

                           double coinBalance = customer.getCoinsBalance();
                           double originalPrice = booking.getRooms().getPricePerDay()*days;
                           customer.setCoinsBalance((((double) 2 / 100) * originalPrice) + coinBalance);

                    customerRepository.save(customer);
                }
            });
        });


        return rooms;
    }


//5.Retrieve bookings with original price and discount price of customer based on date

    public List<String> customerBookings(int customerId,LocalDate date)
    {
       Optional<Customer> customer = customerRepository.findById(customerId);

       if(customer.isPresent())
       {
           Customer existingCustomer = customer.get();

           List<Booking> bookings = existingCustomer.getBookings();

           if(!bookings.isEmpty())
           {
               List<String> bookingDetailsList = bookings.stream().filter(booking ->booking.getBookingDate().toLocalDate().isEqual(date))
                                                                  .map(booking -> "BOOKING DATE ="+ booking.getBookingDate()+" "+
                                                                                  "ORIGINAL PRICE ="+ booking.getOriginalPrice()+" "+
                                                                                  "DISCOUNTED PRICE ="+ booking.getDiscountPrice())
                                                                  .collect(Collectors.toList());

               if (!bookingDetailsList.isEmpty())
               {
                   return bookingDetailsList;
               }
               else
               {
                   throw new BookingsNotFoundException(" Bookings not found in give "+date+" date");
               }
           }
           else
           {
               throw new BookingsNotFoundException(" Bookings not found for customerId "+customerId);
           }
       }
       else
       {
           throw new CustomerNotFoundException("Customer not found with customerId "+customerId);
       }
    }

//6.Retrieve report for PlanMyTrip based on hotel, which contains planMyTrip changes, booking details.

public List<String> getHotelReport(int hotelId)
{
    Optional<Hotel> saveHotel = hotelRepository.findById(hotelId);

    if (saveHotel.isPresent())
    {
        Hotel existingHotel = saveHotel.get();

       List<Room> bookedRooms = existingHotel.getRooms().stream().filter(room -> !room.getBookings().isEmpty()).collect(Collectors.toList());

       if(!bookedRooms.isEmpty())
       {
           List<Booking> bookingList = new LinkedList<>();

           bookedRooms.forEach(bookings->{

               bookingList.addAll(bookings.getBookings());

           });
          return bookingList.stream().map(booking -> "CUSTOMER NAME = " + booking.getCustomer().getCustomerName() + " " +
                                             "ROOM ID = " + booking.getRooms().getRoomId() + " " +
                                             "BOOKING DATE = " + booking.getBookingDate() + " " +
                                             "ORIGINAL PRICE = " + booking.getOriginalPrice() + " " +
                                             "PLAN MY TRIP charges = " +( booking.getOriginalPrice() * (0.05)))
                              .collect(Collectors.toList());
       }
       else
       {
           throw new BookedRoomsNotFoundException("Booked rooms not found in "+existingHotel.getHotelName()+" hotel");
       }
    }
    else
    {
        throw new HotelNotFoundException("Hotel not found with hotel id "+hotelId);
    }
}

//7. API to cancel the booking of customer

    public String cancelBooking(int customerId, int bookingId) throws BookingListNotFoundException, BookingNotCancelledException {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent())
        {
            Customer existingCustomer = customer.get();

            List<Booking> bookings = existingCustomer.getBookings();

            if (!bookings.isEmpty())
            {
                Booking existedBooking = bookings.stream()
                        .filter(booking -> bookingId == booking.getBookingId())
                        .findFirst()
                        .orElse(null);

                if (existedBooking != null)
                {
                    LocalDateTime now = LocalDateTime.now();
                    long hours = ChronoUnit.HOURS.between(now, existedBooking.getCheckInDate().atStartOfDay());

                    if (hours > 24)
                    {
//                        bookings.remove(existedBooking);
                        existedBooking.setBookingStatus("Cancelled");
                        customerRepository.save(existingCustomer);

                        return "Your booking with booking Id " + bookingId + " has been cancelled successfully";
                    }
                    else
                    {
                        throw new BookingNotCancelledException("Booking cannot be cancelled within 24 hours of check-in");
                    }

                }
                else
                {
                    throw new BookingNotFoundException("Booking not found in your booking list with bookingId " + bookingId);
                }
            }
            else
            {
                throw new BookingListNotFoundException("Bookings not found for customerId " + customerId);
            }
        }
        else
        {
            throw new CustomerNotFoundException("Customer not found with customerId " + customerId);
        }
    }



    public Hotel getOneHotel(int hotelId)
    {

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);

        if (hotel.isPresent())
        {
            return hotel.get();
        }
        else
        {
            throw new HotelNotFoundException("Hotel not found with hotelId "+hotelId);
        }
    }

    public Room getOneRoom(int roomId)
    {
        Optional<Room> room = roomRepository.findById(roomId);

        if (room.isPresent())
        {
            return room.get();
        }
        else
        {
            throw new RoomNotFoundException("Room not found with roomId "+roomId);
        }

    }

    public Booking getOneBooking(int bookingId)
    {

        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent())
        {
            return booking.get();
        }
        else
        {
            throw new BookingNotFoundException("Booking not found with bookingId "+bookingId);
        }

    }


    public Customer getOneCustomer(int customerId)
    {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent())
        {
            return customer.get();
        }
        else
        {
            throw new CustomerNotFoundException("Customer not found with customerId "+customerId);
        }

    }

}

