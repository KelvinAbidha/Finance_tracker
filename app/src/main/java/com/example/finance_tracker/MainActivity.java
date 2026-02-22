package com.example.finance_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalBalance;
    private DebtDao debtDao;
    private DebtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvTotalBalance = findViewById(R.id.tvTotalBalance);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvTitle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // DB
        AppDatabase db = AppDatabase.getDatabase(this);
        debtDao = db.debtDao();

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewDebts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DebtAdapter(new ArrayList<>(), this::showMarkAsPaidDialog);
        recyclerView.setAdapter(adapter);

        // Observe debts
        debtDao.getAllDebts().observe(this, debts -> {
            if (debts != null) {
                adapter.setDebts(debts);

                double totalBalance = DebtCalculator.calculateTotalBalance(debts);
                String formatted = String.format("Total Balance: KES %.2f", totalBalance);
                tvTotalBalance.setText(formatted);
            }
        });

        // Add button
        findViewById(R.id.btnAddDebt).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddDebtActivity.class);
            startActivity(intent);
        });
    }

    // ========== Nesh: Settlement Logic ==========

    private void showMarkAsPaidDialog(debt d) {
        new AlertDialog.Builder(this)
                .setTitle("Mark as Paid")
                .setMessage("Mark " + d.getPersonName() + " (" + d.getFormattedAmount() + ") as fully paid?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    markDebtAsPaid(d);
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void markDebtAsPaid(debt d) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Option 1: direct query
            debtDao.markDebtAsPaid(d.getDebtId(), new Date());

            // Option 2 (if you prefer entity logic):
            // d.setSettled(true);
            // d.setAmountPaid(d.getAmount());
            // d.setDateUpdated(new Date());
            // debtDao.update(d);
        });
        
    }
}
