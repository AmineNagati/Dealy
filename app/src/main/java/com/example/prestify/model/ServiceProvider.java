package com.example.prestify.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "service_providers")
public class ServiceProvider {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String serviceType;
    private String availability;
    private String photoUrl;    // URL ou chemin de la photo de profil
    private float rating;       // Note moyenne
    private int reviewCount;    // Nombre total d'avis
    private String specialty;   // Spécialité principale

    public ServiceProvider() {
        // Constructeur par défaut requis par Room
    }

   /* public ServiceProvider(int id, String name, String serviceType, String availability) {
        this.id = id;
        this.name = name;
        this.serviceType = serviceType;
        this.availability = availability;
        this.rating = 0;
        this.reviewCount = 0;
    }*/

    // Getters existants
    public int getId() { return id; }
    public String getName() { return name; }
    public String getServiceType() { return serviceType; }
    public String getAvailability() { return availability; }

    // Nouveaux getters
    public String getPhotoUrl() { return photoUrl; }
    public float getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public String getSpecialty() { return specialty; }

    // Setters existants
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public void setAvailability(String availability) { this.availability = availability; }

    // Nouveaux setters
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    public void setRating(float rating) { this.rating = rating; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}