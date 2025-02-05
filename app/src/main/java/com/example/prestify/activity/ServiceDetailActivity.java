package com.example.prestify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prestify.R;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Reservation;
import com.example.prestify.model.Service;

public class ServiceDetailActivity extends AppCompatActivity {
    private TextView serviceName, serviceDescription, servicePrice;
    private Button reserveButton;
    private Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        // Récupérer le service sélectionné depuis l'intent
        service = (Service) getIntent().getSerializableExtra("SERVICE");

        // Initialiser les vues
        serviceName = findViewById(R.id.serviceName);
        serviceDescription = findViewById(R.id.serviceDescription);
        servicePrice = findViewById(R.id.servicePrice);
        reserveButton = findViewById(R.id.reserveButton);

        // Afficher les détails du service
        if (service != null) {
            serviceName.setText(service.getName());
            serviceDescription.setText(service.getDescription());
            servicePrice.setText(String.format("$%.2f", service.getPrice()));
        }

        // Gérer la réservation
        reserveButton.setOnClickListener(v -> {
            // Simuler une réservation (vous pouvez ajouter une logique de sélection de date/heure ici)
            Reservation reservation = new Reservation();
            reservation.setServiceId(service.getId());
            reservation.setUserId(1); // Remplacez par l'ID de l'utilisateur connecté
            reservation.setDate("2023-10-15"); // Exemple de date
            reservation.setTime("10:00 AM"); // Exemple d'heure

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(ServiceDetailActivity.this);
                db.reservationDao().insert(reservation);
                runOnUiThread(() -> {
                    Toast.makeText(ServiceDetailActivity.this, "Reservation successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ServiceDetailActivity.this, MainActivity.class));
                });
            }).start();
        });
    }
}