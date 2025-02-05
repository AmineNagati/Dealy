package com.example.prestify.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prestify.R;
import com.example.prestify.activity.RequestDetailActivity;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Service;
import com.example.prestify.model.ServiceRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {
    private List<ServiceRequest> requests;
    private Context context;

    public RequestAdapter() {
        this.requests = new ArrayList<>();
    }

    public void setRequests(List<ServiceRequest> newRequests) {
        if (requests.isEmpty()) {
            requests = newRequests;
            notifyItemRangeInserted(0, newRequests.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return requests.size();
                }

                @Override
                public int getNewListSize() {
                    return newRequests.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return requests.get(oldItemPosition).getId() ==
                            newRequests.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ServiceRequest oldRequest = requests.get(oldItemPosition);
                    ServiceRequest newRequest = newRequests.get(newItemPosition);
                    return oldRequest.getStatus().equals(newRequest.getStatus()) &&
                            oldRequest.getDescription().equals(newRequest.getDescription());
                }
            });

            requests = newRequests;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    public void addRequest(ServiceRequest request) {
        requests.add(request);
        notifyItemInserted(requests.size() - 1);
    }

    public void updateRequest(ServiceRequest request) {
        int position = -1;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId() == request.getId()) {
                position = i;
                break;
            }
        }
        if (position != -1) {
            requests.set(position, request);
            notifyItemChanged(position);
        }
    }

    public void removeRequest(ServiceRequest request) {
        int position = requests.indexOf(request);
        if (position != -1) {
            requests.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        ServiceRequest request = requests.get(position);

        new Thread(() -> {
            Service service = AppDatabase.getInstance(context).serviceDao().getServiceById(request.getServiceId());

            ((Activity) context).runOnUiThread(() -> {
                holder.requestTitle.setText(service.getName());
                holder.requestDescription.setText(request.getDescription());
                holder.requestDate.setText(formatDate(request.getTimestamp()));
                holder.requestPrice.setText(String.format(Locale.FRANCE, "%.2f €", service.getPrice()));

                holder.requestStatus.setText(getStatusText(request.getStatus()));
                holder.requestStatus.setBackgroundTintList(
                        ContextCompat.getColorStateList(context, getStatusColor(request.getStatus()))
                );
            });
        }).start();

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RequestDetailActivity.class);
            intent.putExtra("REQUEST_ID", request.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView requestTitle, requestDescription, requestStatus, requestDate, requestPrice;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            requestTitle = itemView.findViewById(R.id.requestTitle);
            requestDescription = itemView.findViewById(R.id.requestDescription);
            requestStatus = itemView.findViewById(R.id.requestStatus);
            requestDate = itemView.findViewById(R.id.requestDate);
            requestPrice = itemView.findViewById(R.id.requestPrice);
        }
    }

    private String getStatusText(String status) {
        switch (status) {
            case "PENDING":
                return "En attente";
            case "ACCEPTED":
                return "En cours";
            case "COMPLETED":
                return "Terminé";
            case "CANCELLED":
                return "Annulé";
            default:
                return status;
        }
    }

    private int getStatusColor(String status) {
        switch (status) {
            case "PENDING":
                return R.color.status_pending;
            case "ACCEPTED":
                return R.color.status_accepted;
            case "COMPLETED":
                return R.color.status_completed;
            case "CANCELLED":
                return R.color.status_cancelled;
            default:
                return R.color.primary;
        }
    }

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy 'à' HH:mm", Locale.FRENCH);
        return sdf.format(new Date(timestamp));
    }
}