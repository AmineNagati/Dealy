package com.example.prestify.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.prestify.model.Message;
import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insert(Message message);

    @Query("SELECT * FROM messages WHERE senderId = :userId OR receiverId = :userId")
    List<Message> getMessagesByUser(int userId);

    @Query("SELECT * FROM messages WHERE (senderId = :userId1 AND receiverId = :userId2) OR (senderId = :userId2 AND receiverId = :userId1)")
    List<Message> getMessagesBetweenUsers(int userId1, int userId2);
}