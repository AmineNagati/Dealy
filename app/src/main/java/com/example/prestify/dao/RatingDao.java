package com.example.prestify.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.prestify.model.Rating;
import java.util.List;

@Dao
public interface RatingDao {
    @Insert
    void insert(Rating rating);

    @Query("SELECT * FROM ratings WHERE serviceId = :serviceId")
    List<Rating> getRatingsByService(int serviceId);

    @Query("SELECT AVG(rating) FROM ratings WHERE serviceId = :serviceId")
    double getAverageRatingByService(int serviceId);
}