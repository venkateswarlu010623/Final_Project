package com.PlanMyTrip.Repository;

import com.PlanMyTrip.Model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {

    Optional<Hotel> findByHotelName(String hotelName);

    Optional<Hotel> findByLocationAndHotelName(String location, String hotelName);

    Optional<List<Hotel>> findByRoomsRoomTypeAndRoomsSharingAndLocation(String roomType, String sharing, String location);

}
