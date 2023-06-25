package com.PlanMyTrip.Service;

import com.PlanMyTrip.ExceptionHandling.*;
import com.PlanMyTrip.Model.*;
import com.PlanMyTrip.Repository.BookingRepository;
import com.PlanMyTrip.Repository.CustomerRepository;
import com.PlanMyTrip.Repository.HotelRepository;
import com.PlanMyTrip.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Synchronization;
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


//1.Register a Hotels and customers

    public Hotel saveHotel(Hotel hotel)
    {
        hotel.setRooms(new LinkedList<>());
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
        customer.setBookings(new ArrayList<>());
        return customerRepository.save(customer);
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
                                .filter(room -> room.getSharing().equalsIgnoreCase(hotelGetRequest.getSharing()) &&
                                                room.getRoomType().equalsIgnoreCase(hotelGetRequest.getRoomType()) &&
                                                room.getStatus().equalsIgnoreCase(hotelGetRequest.getRoomStatus()))
                                .collect(Collectors.toList());

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

    public CustomerBookingRequest createCustomerBooking(CustomerBookingRequest customerBookingRequest) throws HotelNotFoundException, RoomNotFoundException, RoomNotAvailableException, CustomerNotFoundException {

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

                           booking.setCheckInDate(customerBookingRequest.getBookingDetails().getCheckInDate());
                           booking.setCheckOutDate(customerBookingRequest.getBookingDetails().getCheckOutDate());
                           booking.setOriginalPrice(originalPrice);
                           booking.setDiscountPrice(discountedPrice);
                           booking.setBookingDate(LocalDateTime.now());
                           booking.setRooms(existingRoom);
                           booking.setCustomer(customer);

                           customer.getBookings().add(booking);

                           double coinBalance = customer.getCoinsBalance();
                           customer.setCoinsBalance((((double) 2 / 100) * originalPrice) + coinBalance);
                           customerRepository.save(customer);
                       }

                       List<Booking> bookingList = customer.getBookings().stream()
                               .sorted(Comparator.comparing(Booking::getBookingDate).reversed())
                               .collect(Collectors.toList());

                       customerBookingRequest.setBookingDetails(bookingList.get(0));
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



//4.API to update room status when customer check-in and check-out

    public List<Room> updateRoomStatus() {
        List<Room> rooms = roomRepository.findAll();

        rooms.forEach(room -> {

            if(room.getBookings().isEmpty())
            {
                room.setStatus("UnReserved");
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
                    room.setStatus("Occupied upto "+booking.getCheckOutDate());
                }
                else if (booking.getCheckOutDate().isEqual(today))
                {
                    room.setStatus("Available");
                }
                else
                {
                    room.setStatus("Reserved");
                }
            });

            roomRepository.save(room);
        });

        return bookedRooms;
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
               List<String> bookingDetailsList = bookings.stream().filter(booking ->booking.getBookingDate().isEqual(date.atStartOfDay()))
                                                                  .map(booking -> "BOOKING DATE ="+ booking.getBookingDate()+" "+
                                                                                  "ORIGINAL PRICE ="+ booking.getOriginalPrice()+" "+
                                                                                  "DISCOUNTED PRICE ="+ booking.getDiscountPrice())
                                                                  .collect(Collectors.toList());
               return bookingDetailsList;
           }
           else
           {
               throw new BookingsNotFoundException(" Bookings not found ");
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

}
