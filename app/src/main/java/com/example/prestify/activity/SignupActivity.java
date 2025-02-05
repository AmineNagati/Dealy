package com.example.prestify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prestify.R;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.ServiceProvider;
import com.example.prestify.model.User;

import java.util.Arrays;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    private EditText firstNameInput, lastNameInput, emailInput, passwordInput;
    private EditText specialtyInput, availabilityInput;
    private Spinner serviceTypeSpinner;
    private RadioGroup accountTypeGroup;
    private LinearLayout providerFieldsContainer;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeViews();
        setupServiceTypeSpinner();
        setupAccountTypeListener();
    }

    private void initializeViews() {
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        specialtyInput = findViewById(R.id.specialtyInput);
        availabilityInput = findViewById(R.id.availabilityInput);
        serviceTypeSpinner = findViewById(R.id.serviceTypeSpinner);
        accountTypeGroup = findViewById(R.id.accountTypeGroup);
        providerFieldsContainer = findViewById(R.id.providerFieldsContainer);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(v -> handleSignup());
    }

    private void setupServiceTypeSpinner() {
        List<String> serviceTypes = Arrays.asList(
                "MENAGE", "BRICOLAGE", "JARDINAGE", "DEMENAGEMENT",
                "ENFANTS", "ANIMAUX", "INFORMATIQUE", "AIDE_DOMICILE", "COURS"
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, serviceTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceTypeSpinner.setAdapter(adapter);
    }

    private void setupAccountTypeListener() {
        accountTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            providerFieldsContainer.setVisibility(
                    checkedId == R.id.providerRadio ? View.VISIBLE : View.GONE
            );
        });
    }

    private void handleSignup() {
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        boolean isProvider = accountTypeGroup.getCheckedRadioButtonId() == R.id.providerRadio;

        if (!validateInputs(firstName, lastName, email, password)) {
            return;
        }

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);

            // Créer l'utilisateur
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setProvider(isProvider);

            long userId = db.userDao().insert(user);

            // Si c'est un prestataire, créer aussi un ServiceProvider
            if (isProvider) {
                ServiceProvider provider = new ServiceProvider();
                provider.setName(firstName + " " + lastName);
                provider.setServiceType(serviceTypeSpinner.getSelectedItem().toString());
                provider.setSpecialty(specialtyInput.getText().toString());
                provider.setAvailability(availabilityInput.getText().toString());
                db.serviceProviderDao().insert(provider);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            });
        }).start();
    }

    private boolean validateInputs(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean isProvider = accountTypeGroup.getCheckedRadioButtonId() == R.id.providerRadio;
        if (isProvider) {
            String specialty = specialtyInput.getText().toString();
            String availability = availabilityInput.getText().toString();
            if (specialty.isEmpty() || availability.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs prestataire", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}