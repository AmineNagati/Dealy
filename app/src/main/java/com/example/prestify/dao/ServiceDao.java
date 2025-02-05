package com.example.prestify.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.prestify.model.Service;
import java.util.List;

@Dao
public interface ServiceDao {
    @Insert
    void insert(Service service);

    @Update
    void update(Service service);

    @Query("SELECT * FROM services")
    List<Service> getAllServices();

    @Query("SELECT * FROM services WHERE category = :category")
    List<Service> getServicesByCategory(String category);

    @Query("SELECT * FROM services WHERE id = :serviceId")
    Service getServiceById(int serviceId);

    @Query("SELECT DISTINCT category FROM services")
    List<String> getAllCategories();

    @Query("SELECT * FROM services ORDER BY numberOfRequests DESC LIMIT 5")
    List<Service> getPopularServices();

    @Query("SELECT * FROM services WHERE isSpecialOffer = 1")
    List<Service> getSpecialOffers();
}