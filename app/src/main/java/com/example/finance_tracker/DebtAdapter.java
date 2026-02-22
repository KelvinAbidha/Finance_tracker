package com.example.finance_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.DebtViewHolder> {

    private List<debt> debtList;

    // Kerry's Bridge: This constructor takes Adrian's list of debts
    public DebtAdapter(List<debt> debtList) {
        this.debtList = debtList;
    }

    // NEW: This method allows MainActivity to update the list with real data from Cynthia's Database
    public void setDebts(List<debt> debts) {
        this.debtList = debts;
        notifyDataSetChanged(); // Refreshes the UI when data changes
    }

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Kerry connects to the "item_debt" layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_debt, parent, false);
        return new DebtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder holder, int position) {
        // Kerry takes one debt from the list
        debt currentDebt = debtList.get(position);

        // Kerry fills the "slots" using Adrian's methods
        holder.nameText.setText(currentDebt.getPersonName());

        // Using Adrian's built-in formatting logic for the currency
        holder.amountText.setText(currentDebt.getFormattedAmount());
    }

    @Override
    public int getItemCount() {
        return debtList != null ? debtList.size() : 0;
    }

    public static class DebtViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView amountText;

        public DebtViewHolder(@NonNull View itemView) {
            super(itemView);
            // Linking the Java variables to the IDs in your item_debt.xml
            nameText = itemView.findViewById(R.id.tv_person_name);
            amountText = itemView.findViewById(R.id.tv_amount);
        }
    }
}