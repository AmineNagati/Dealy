package com.example.prestify.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.prestify.model.ServiceProvider;
import java.util.List;

@Dao
public interface ServiceProviderDao {
    @Insert
    void insert(ServiceProvider serviceProvider);

    @Query("SELECT * FROM service_providers")
    List<ServiceProvider> getAllProviders();

    @Query("SELECT * FROM service_providers WHERE id = :providerId")
    ServiceProvider getProviderById(int providerId);

    @Query("SELECT * FROM service_providers WHERE serviceType = :serviceType")
    List<ServiceProvider> getProvidersByServiceType(String serviceType);

    @Query("SELECT * FROM service_providers ORDER BY rating DESC LIMIT 5")
    List<ServiceProvider> getTopProviders();
}