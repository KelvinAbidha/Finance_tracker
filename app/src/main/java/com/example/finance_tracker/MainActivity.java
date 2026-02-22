package com.example.finance_tracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// KERRY'S VERIFIED IMPORTS
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List; // Added this to support Cynthia's Database results

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalBalance; //Calculator variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvTotalBalance = findViewById(R.id.tvTotalBalance);

        // Daniel's XML uses tvTitle as the top-level ID for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvTitle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ==========================================
        // KERRY'S CONNECTION LOGIC
        // ==========================================

        // 1. Setup the RecyclerView (Daniel's UI)
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDebts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Kerry's adapter with an empty list first
        final DebtAdapter adapter = new DebtAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // 2. Connect to the Database (Cynthia's Logic)
        AppDatabase db = AppDatabase.getDatabase(this);

        // Use Cynthia's getAllDebts() to watch for changes in real-time
        db.debtDao().getAllDebts().observe(this, debts -> {
            if (debts != null) {
                // Kerry's bridge updates the list whenever Deqow saves data
                adapter.setDebts(debts);

                //Mercy: Implementing the DebtCalculator
                double totalBalance = DebtCalculator.calculateTotalBalance(debts);

                String formatted = String.format("Total Balance: KES %.2f", totalBalance);
                tvTotalBalance.setText(formatted);
            }
        });

        // 3. Connect the "Add" Button (Deqow's Logic)
        findViewById(R.id.btnAddDebt).setOnClickListener(v -> {
            // This starts the activity Deqow created to take user input
            Intent intent = new Intent(MainActivity.this, AddDebtActivity.class);
            startActivity(intent);
        });

        // ==========================================



    }
}