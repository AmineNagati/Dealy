package com.example.prestify.model;

import androidx.room.Embedded;

public class ServiceRequestWithService {
    @Embedded
    public ServiceRequest request;

    public String serviceName;
    public double servicePrice;
}