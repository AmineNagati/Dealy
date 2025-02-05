package com.example.prestify.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.prestify.R;

public class PaymentActivity extends AppCompatActivity {
    private Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        payButton = findViewById(R.id.payButton);

        payButton.setOnClickListener(v -> {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}