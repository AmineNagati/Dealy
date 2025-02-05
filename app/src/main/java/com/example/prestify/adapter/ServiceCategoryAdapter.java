package com.example.prestify.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.activity.ServiceListActivity;

import java.util.List;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.CategoryViewHolder> {
    private final List<String> categories;
    private final Context context;

    public ServiceCategoryAdapter(Context context, List<String> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = categories.get(position);
        holder.categoryName.setText(getCategoryDisplayName(category));
        holder.categoryIcon.setImageResource(getCategoryIcon(category));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServiceListActivity.class);
            intent.putExtra("CATEGORY", category);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private String getCategoryDisplayName(String category) {
        switch (category) {
            case "MENAGE":
                return "Ménage";
            case "BRICOLAGE":
                return "Bricolage";
            case "JARDINAGE":
                return "Jardinage";
            case "DEMENAGEMENT":
                return "Déménagement";
            case "ENFANTS":
                return "Enfants";
            case "ANIMAUX":
                return "Animaux";
            case "INFORMATIQUE":
                return "Informatique";
            case "AIDE_DOMICILE":
                return "Aide à domicile";
            case "COURS":
                return "Cours particuliers";
            default:
                return category;
        }
    }

    private int getCategoryIcon(String category) {
        switch (category) {
            case "MENAGE":
                return R.drawable.ic_menage;
            case "BRICOLAGE":
                return R.drawable.ic_bricolage;
            case "JARDINAGE":
                return R.drawable.ic_jardinage;
            case "DEMENAGEMENT":
                return R.drawable.ic_demenagement;
            case "ENFANTS":
                return R.drawable.ic_enfants;
            case "ANIMAUX":
                return R.drawable.ic_animaux;
            case "INFORMATIQUE":
                return R.drawable.ic_informatique;
            case "AIDE_DOMICILE":
                return R.drawable.ic_aide_domicile;
            case "COURS":
                return R.drawable.ic_cours;
            default:
                return R.drawable.ic_service_default;
        }
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIcon;
        TextView categoryName;

        CategoryViewHolder(View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}