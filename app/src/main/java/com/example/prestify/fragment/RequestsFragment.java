package com.example.prestify.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prestify.R;
import com.example.prestify.activity.ServiceRequestActivity;
import com.example.prestify.adapter.RequestAdapter;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.ServiceRequest;
import com.example.prestify.util.SessionManager;

import java.util.List;

public class RequestsFragment extends Fragment {
    private View emptyStateContainer;
    private RecyclerView requestsRecyclerView;
    private RequestAdapter requestAdapter;
    private Button requestServiceButton;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        initializeViews(view);
        setupClickListeners();
        loadRequests();

        return view;
    }

    private void initializeViews(View view) {
        sessionManager = new SessionManager(requireContext());
        emptyStateContainer = view.findViewById(R.id.emptyStateContainer);
        requestsRecyclerView = view.findViewById(R.id.requestsRecyclerView);
        requestServiceButton = view.findViewById(R.id.requestServiceButton);

        // Configuration du RecyclerView
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestAdapter = new RequestAdapter();
        requestsRecyclerView.setAdapter(requestAdapter);
    }

    private void setupClickListeners() {
        requestServiceButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ServiceRequestActivity.class);
            startActivity(intent);
        });
    }

    private void loadRequests() {
        int userId = sessionManager.getUserId();

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            List<ServiceRequest> requests = db.serviceRequestDao().getRequestsByUser(userId);

            requireActivity().runOnUiThread(() -> {
                if (requests.isEmpty()) {
                    showEmptyState();
                } else {
                    showRequests(requests);
                }
            });
        }).start();
    }

    private void showEmptyState() {
        emptyStateContainer.setVisibility(View.VISIBLE);
        requestsRecyclerView.setVisibility(View.GONE);
    }

    private void showRequests(List<ServiceRequest> requests) {
        emptyStateContainer.setVisibility(View.GONE);
        requestsRecyclerView.setVisibility(View.VISIBLE);
        requestAdapter.setRequests(requests);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recharger les demandes à chaque fois que le fragment devient visible
        loadRequests();
    }
}