package com.example.prestify.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.prestify.model.Reservation;
import java.util.List;

@Dao
public interface ReservationDao {
    @Insert
    void insert(Reservation reservation);

    @Query("SELECT * FROM reservations WHERE userId = :userId")
    List<Reservation> getReservationsByUser(int userId);

    @Query("SELECT * FROM reservations WHERE serviceId = :serviceId")
    List<Reservation> getReservationsByService(int serviceId);
}