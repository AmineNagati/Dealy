package com.example.prestify.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prestify.R;
import com.example.prestify.activity.ServiceCategoryActivity;
import com.example.prestify.adapter.ServiceAdapter;
import com.example.prestify.adapter.ServiceCategoryAdapter;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Service;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView popularServicesRecyclerView;
    private RecyclerView categoriesRecyclerView;
    private RecyclerView specialOffersRecyclerView;

    private ServiceAdapter popularServiceAdapter;
    private ServiceCategoryAdapter categoryAdapter;
    private ServiceAdapter specialOffersAdapter;

    private List<Service> popularServices = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private List<Service> specialOffers = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(view);
        setupRecyclerViews();
        loadData();

        return view;
    }

    private void initializeViews(View view) {
        popularServicesRecyclerView = view.findViewById(R.id.popularServicesRecyclerView);
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        specialOffersRecyclerView = view.findViewById(R.id.specialOffersRecyclerView);

        EditText searchBar = view.findViewById(R.id.searchBar);

        searchBar.setFocusable(false);
        searchBar.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ServiceCategoryActivity.class));
        });
    }

    private void setupRecyclerViews() {
        // Services populaires
        if (popularServicesRecyclerView != null) {
            popularServicesRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            popularServiceAdapter = new ServiceAdapter(requireContext(), popularServices);
            popularServicesRecyclerView.setAdapter(popularServiceAdapter);
        }

        // Catégories
        if (categoriesRecyclerView != null) {
            categoriesRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            categoryAdapter = new ServiceCategoryAdapter(requireContext(), categories);
            categoriesRecyclerView.setAdapter(categoryAdapter);
        }

        // Offres spéciales
        if (specialOffersRecyclerView != null) {
            specialOffersRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            specialOffersAdapter = new ServiceAdapter(requireContext(), specialOffers);
            specialOffersRecyclerView.setAdapter(specialOffersAdapter);
        }
    }

    private void loadData() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());

            // Charger les catégories
            List<String> categoryList = db.serviceDao().getAllCategories();

            // Charger les services populaires
            List<Service> popularServicesList = db.serviceDao().getPopularServices();

            // Charger les offres spéciales
            List<Service> specialOffersList = db.serviceDao().getSpecialOffers();

            requireActivity().runOnUiThread(() -> {
                // Mettre à jour les catégories
                categories.clear();
                categories.addAll(categoryList);
                if (categoryAdapter != null) {
                    categoryAdapter.notifyDataSetChanged();
                }

                // Mettre à jour les services populaires
                popularServices.clear();
                popularServices.addAll(popularServicesList);
                if (popularServiceAdapter != null) {
                    popularServiceAdapter.notifyDataSetChanged();
                }

                // Mettre à jour les offres spéciales
                specialOffers.clear();
                specialOffers.addAll(specialOffersList);
                if (specialOffersAdapter != null) {
                    specialOffersAdapter.notifyDataSetChanged();
                }
            });
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}