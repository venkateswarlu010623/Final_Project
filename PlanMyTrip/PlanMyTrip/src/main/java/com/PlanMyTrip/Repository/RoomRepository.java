package com.PlanMyTrip.Repository;

import com.PlanMyTrip.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    Optional<Room> findByRoomTypeAndSharing(String roomType, String sharing);
}
