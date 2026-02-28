package com.example.finance_tracker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DebtDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_detail);

        TextView tvDetails = findViewById(R.id.tvDebtDetails);

        // Get data from Intent
        String name = getIntent().getStringExtra("name");
        double amount = getIntent().getDoubleExtra("amount", 0);

        tvDetails.setText("Name: " + name + "\nAmount: KES " + amount);
    }
}