package com.example.prestify.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.prestify.R;
import com.example.prestify.fragment.AccountFragment;
import com.example.prestify.fragment.HomeFragment;
import com.example.prestify.fragment.MessagingFragment;
import com.example.prestify.fragment.RequestsFragment;
import com.example.prestify.model.ServiceInitializer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabAddService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceInitializer.initializeServices(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fabAddService = findViewById(R.id.fabAddService);

        // Configuration du BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.nav_requests) {
                loadFragment(new RequestsFragment());
                return true;
            } else if (itemId == R.id.nav_messaging) {
                loadFragment(new MessagingFragment());
                return true;
            } else if (itemId == R.id.nav_account) {
                loadFragment(new AccountFragment());
                return true;
            }
            return false;
        });

        // Configuration du FAB - Modifié pour ouvrir ServiceCategoryActivity
        fabAddService.setOnClickListener(v -> {
            Intent intent = new Intent(this, ServiceCategoryActivity.class);
            startActivity(intent);
        });

        // Charger le fragment par défaut
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}