package com.example.prestify.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.adapter.ServiceAdapter;
import com.example.prestify.database.AppDatabase;
import com.example.prestify.model.Service;

import java.util.List;

public class ServiceListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list_detail);

        category = getIntent().getStringExtra("CATEGORY");
        initializeViews();
        loadServices();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.servicesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView titleText = findViewById(R.id.titleText);
        titleText.setText(category);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void loadServices() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Service> services = db.serviceDao().getServicesByCategory(category);

            runOnUiThread(() -> {
                adapter = new ServiceAdapter(this, services);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}