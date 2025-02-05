package com.example.prestify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prestify.R;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Service;
import com.example.prestify.model.ServiceRequest;
import com.example.prestify.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestDetailActivity extends AppCompatActivity {

    private TextView titleText, statusText, dateText, descriptionText;
    private TextView serviceNameText, servicePriceText;
    private TextView providerNameText;
    private Button actionButton;
    private ServiceRequest request;
    private Service service;
    private User provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        initializeViews();
        loadRequestDetails();
    }

    private void initializeViews() {
        titleText = findViewById(R.id.titleText);
        statusText = findViewById(R.id.statusText);
        dateText = findViewById(R.id.dateText);
        descriptionText = findViewById(R.id.descriptionText);
        serviceNameText = findViewById(R.id.serviceNameText);
        servicePriceText = findViewById(R.id.servicePriceText);
        providerNameText = findViewById(R.id.providerNameText);
        actionButton = findViewById(R.id.actionButton);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void loadRequestDetails() {
        int requestId = getIntent().getIntExtra("REQUEST_ID", -1);
        if (requestId == -1) {
            finish();
            return;
        }

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            request = db.serviceRequestDao().getRequestById(requestId);
            service = db.serviceDao().getServiceById(request.getServiceId());
            provider = request.getProviderId() != 0 ? db.userDao().getUserById(request.getProviderId()) : null;

            runOnUiThread(() -> updateUI());
        }).start();
    }

    private void updateUI() {
        titleText.setText("Demande #" + request.getId());
        statusText.setText(getStatusText(request.getStatus()));
        dateText.setText(formatDate(request.getTimestamp()));
        descriptionText.setText(request.getDescription());

        serviceNameText.setText(service.getName());
        servicePriceText.setText(String.format("%.2f €", service.getPrice()));

        if (provider != null) {
            providerNameText.setText(provider.getFirstName() + " " + provider.getLastName());
            providerNameText.setVisibility(View.VISIBLE);
        } else {
            providerNameText.setVisibility(View.GONE);
        }

        updateActionButton();
    }

    private void updateActionButton() {
        switch (request.getStatus()) {
            case "PENDING":
                actionButton.setText("Annuler la demande");
                actionButton.setOnClickListener(v -> cancelRequest());
                break;
            case "ACCEPTED":
                actionButton.setText("Voir la conversation");
                actionButton.setOnClickListener(v -> openChat());
                break;
            default:
                actionButton.setVisibility(View.GONE);
                break;
        }
    }

    private void cancelRequest() {
        new Thread(() -> {
            request.setStatus("CANCELLED");
            AppDatabase.getInstance(this).serviceRequestDao().update(request);
            runOnUiThread(this::finish);
        }).start();
    }

    private void openChat() {
        // Ouvrir l'activité de conversation avec le prestataire
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra("receiverId", request.getProviderId());
        startActivity(intent);
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

    private String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy 'à' HH:mm", Locale.FRENCH);
        return sdf.format(new Date(timestamp));
    }
}