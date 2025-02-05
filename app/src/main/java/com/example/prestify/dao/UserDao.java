package com.example.prestify.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.prestify.model.User;

@Dao
public interface UserDao {
    @Insert
    long insert(User user);  // Retourne l'ID de l'utilisateur créé

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);
}