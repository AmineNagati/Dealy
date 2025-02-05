package com.example.prestify.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "service_requests",
        foreignKeys = {
                @ForeignKey(
                        entity = Service.class,
                        parentColumns = "id",
                        childColumns = "serviceId"
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId"
                ),
                @ForeignKey(
                        entity = ServiceProvider.class,
                        parentColumns = "id",
                        childColumns = "providerId",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {
                @Index("serviceId"),
                @Index("userId"),
                @Index("providerId")
        }
)
public class ServiceRequest {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int serviceId;
    private int providerId;  // ID du prestataire assigné à cette demande
    private String description;
    private String status;
    private long timestamp;

    // Constructeur par défaut
    public ServiceRequest() {
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getProviderId() { return providerId; }
    public void setProviderId(int providerId) { this.providerId = providerId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}