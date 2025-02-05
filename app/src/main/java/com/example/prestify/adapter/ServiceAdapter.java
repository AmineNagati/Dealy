package com.example.prestify.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.activity.ServiceDetailActivity;
import com.example.prestify.model.Service;
import java.util.List;
import java.util.Locale;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private final List<Service> services;
    private final Context context;
    private final boolean isHorizontal;

    public ServiceAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
        this.isHorizontal = false;
    }

    public ServiceAdapter(Context context, List<Service> services, boolean isHorizontal) {
        this.context = context;
        this.services = services;
        this.isHorizontal = isHorizontal;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isHorizontal ? R.layout.item_service_horizontal : R.layout.item_service;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = services.get(position);
        holder.serviceName.setText(service.getName());
        holder.serviceDescription.setText(service.getDescription());
        holder.servicePrice.setText(String.format(Locale.FRANCE, "%.2f €/h", service.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServiceDetailActivity.class);
            intent.putExtra("SERVICE_ID", service.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName, serviceDescription, servicePrice;

        ServiceViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            servicePrice = itemView.findViewById(R.id.servicePrice);
        }
    }
}