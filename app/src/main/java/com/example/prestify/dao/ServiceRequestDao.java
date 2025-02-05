package com.example.prestify.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.prestify.model.ServiceRequest;
import com.example.prestify.model.ServiceRequestWithService;

import java.util.List;

@Dao
public interface ServiceRequestDao {
    @Insert
    long insert(ServiceRequest request);

    @Update
    void update(ServiceRequest request);

    @Query("SELECT * FROM service_requests WHERE userId = :userId ORDER BY timestamp DESC")
    List<ServiceRequest> getRequestsByUser(int userId);

    @Query("SELECT * FROM service_requests WHERE status = :status ORDER BY timestamp DESC")
    List<ServiceRequest> getRequestsByStatus(String status);

    @Query("SELECT * FROM service_requests WHERE id = :requestId")
    ServiceRequest getRequestById(int requestId);

    @Query("SELECT * FROM service_requests WHERE serviceId = :serviceId")
    List<ServiceRequest> getRequestsByService(int serviceId);

    // Pour obtenir les demandes avec les infos du service
    @Query("SELECT sr.*, s.name as serviceName, s.price as servicePrice " +
            "FROM service_requests sr " +
            "INNER JOIN services s ON sr.serviceId = s.id " +
            "WHERE sr.userId = :userId")
    List<ServiceRequestWithService> getRequestsWithServiceByUser(int userId);
}