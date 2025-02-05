package com.example.prestify.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prestify.R;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Service;
import com.example.prestify.model.ServiceRequest;
import com.example.prestify.util.SessionManager;
import java.util.List;

public class ServiceRequestActivity extends AppCompatActivity {

    private EditText descriptionInput;
    private Spinner serviceSpinner;
    private Button submitButton;
    private SessionManager sessionManager;
    private List<Service> availableServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        sessionManager = new SessionManager(this);

        initializeViews();
        loadServices();

        submitButton.setOnClickListener(v -> submitRequest());
    }

    private void initializeViews() {
        descriptionInput = findViewById(R.id.descriptionInput);
        serviceSpinner = findViewById(R.id.serviceSpinner);
        submitButton = findViewById(R.id.submitButton);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void loadServices() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            availableServices = db.serviceDao().getAllServices();

            runOnUiThread(() -> {
                // Créer un adapter pour le spinner avec les noms des services
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        availableServices.stream()
                                .map(Service::getName)
                                .toArray(String[]::new)
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                serviceSpinner.setAdapter(adapter);
            });
        }).start();
    }

    private void submitRequest() {
        String description = descriptionInput.getText().toString().trim();
        int selectedPosition = serviceSpinner.getSelectedItemPosition();

        if (description.isEmpty()) {
            descriptionInput.setError("Veuillez décrire votre demande");
            return;
        }

        if (selectedPosition < 0 || selectedPosition >= availableServices.size()) {
            Toast.makeText(this, "Veuillez sélectionner un service", Toast.LENGTH_SHORT).show();
            return;
        }

        Service selectedService = availableServices.get(selectedPosition);

        // Créer l'objet ServiceRequest
        ServiceRequest request = new ServiceRequest();
        request.setUserId(sessionManager.getUserId());
        request.setServiceId(selectedService.getId());
        request.setDescription(description);
        request.setStatus("PENDING");
        request.setTimestamp(System.currentTimeMillis());

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.serviceRequestDao().insert(request);

            runOnUiThread(() -> {
                Toast.makeText(this, "Demande envoyée avec succès", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}