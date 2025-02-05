package com.example.prestify.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.prestify.R;
import com.example.prestify.adapter.ServiceCategoryAdapter;
import com.example.prestify.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ServiceCategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ServiceCategoryAdapter adapter;
    private EditText searchBar;
    private List<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        initializeViews();
        setupSearchBar();
        loadCategories();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.categoriesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchBar = findViewById(R.id.searchBar);

        ImageButton backButton = findViewById(R.id.backButton);
        TextView cancelButton = findViewById(R.id.cancelButton);

        backButton.setOnClickListener(v -> finish());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void setupSearchBar() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Implémentation de la recherche à venir
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadCategories() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<String> categoryList = db.serviceDao().getAllCategories();

            runOnUiThread(() -> {
                categories.clear();
                categories.addAll(categoryList);
                if (adapter == null) {
                    adapter = new ServiceCategoryAdapter(this, categories);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            });
        }).start();
    }
}