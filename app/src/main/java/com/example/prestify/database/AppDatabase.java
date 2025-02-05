package com.example.prestify.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.example.prestify.dao.MessageDao;
import com.example.prestify.dao.RatingDao;
import com.example.prestify.dao.ReservationDao;
import com.example.prestify.dao.ServiceDao;
import com.example.prestify.dao.ServiceProviderDao;
import com.example.prestify.dao.ServiceRequestDao;
import com.example.prestify.dao.UserDao;
import com.example.prestify.model.Message;
import com.example.prestify.model.Rating;
import com.example.prestify.model.Reservation;
import com.example.prestify.model.Service;
import com.example.prestify.model.ServiceProvider;
import com.example.prestify.model.ServiceRequest;
import com.example.prestify.model.User;

@Database(entities = {
        User.class,
        ServiceProvider.class,
        Service.class,
        Reservation.class,
        Message.class,
        Rating.class,
        ServiceRequest.class
}, version = 3,  // Augmenté à 3 pour les nouveaux champs
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract ServiceProviderDao serviceProviderDao();
    public abstract ServiceDao serviceDao();
    public abstract ReservationDao reservationDao();
    public abstract MessageDao messageDao();
    public abstract RatingDao ratingDao();
    public abstract ServiceRequestDao serviceRequestDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "prestify_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}