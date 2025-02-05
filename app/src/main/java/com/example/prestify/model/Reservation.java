package com.example.prestify.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;


import androidx.room.Index;
import androidx.room.ForeignKey;

@Entity(tableName = "reservations")
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int serviceId;
    private String date;
    private String time;

    @Ignore
    public Reservation(int id, int userId, int serviceId, String date, String time) {
        this.id = id;
        this.userId = userId;
        this.serviceId = serviceId;
        this.date = date;
        this.time = time;
    }

    public Reservation() {

    }


    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}