package com.example.prestify.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "services")
public class Service {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int numberOfRequests;    // Pour suivre la popularité
    private boolean isSpecialOffer;  // Pour les offres spéciales

    // Constructeur par défaut nécessaire pour Room
    public Service() {
    }

    // Constructeur avec paramètres
    public Service(String name, String description, double price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.numberOfRequests = 0;
        this.isSpecialOffer = false;

    }
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getNumberOfRequests() { return numberOfRequests; }
    public boolean isSpecialOffer() { return isSpecialOffer; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setNumberOfRequests(int numberOfRequests) { this.numberOfRequests = numberOfRequests; }
    public void setSpecialOffer(boolean specialOffer) { isSpecialOffer = specialOffer; }
}