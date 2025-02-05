package com.example.prestify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.model.ServiceProvider;
import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewHolder> {
    private final Context context;
    private final List<ServiceProvider> providers;

    public ProviderAdapter(Context context, List<ServiceProvider> providers) {
        this.context = context;
        this.providers = providers;
    }

    @NonNull
    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_provider, parent, false);
        return new ProviderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, int position) {
        ServiceProvider provider = providers.get(position);

        holder.providerName.setText(provider.getName());
        holder.providerSpecialty.setText(provider.getSpecialty());
        holder.ratingBar.setRating(provider.getRating());
        holder.reviewCount.setText(String.format("(%d avis)", provider.getReviewCount()));

        // Pour l'instant, on utilise une image par défaut
        holder.providerImage.setImageResource(R.drawable.default_profile);

        holder.itemView.setOnClickListener(v -> {
            // TODO: Implémenter la navigation vers le profil du prestataire
        });
    }

    @Override
    public int getItemCount() {
        return providers.size();
    }

    static class ProviderViewHolder extends RecyclerView.ViewHolder {
        ImageView providerImage;
        TextView providerName, providerSpecialty, reviewCount;
        RatingBar ratingBar;

        ProviderViewHolder(View itemView) {
            super(itemView);
            providerImage = itemView.findViewById(R.id.providerImage);
            providerName = itemView.findViewById(R.id.providerName);
            providerSpecialty = itemView.findViewById(R.id.providerSpecialty);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            reviewCount = itemView.findViewById(R.id.reviewCount);
        }
    }
}