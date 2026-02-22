package com.example.finance_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.DebtViewHolder> {

    public interface OnDebtClickListener {
        void onDebtClick(debt d);
    }

    private List<debt> debtList;
    private final OnDebtClickListener listener;

    // Kerry's Bridge + Nesh click listener
    public DebtAdapter(List<debt> debtList, OnDebtClickListener listener) {
        this.debtList = debtList;
        this.listener = listener;
    }

    // Allow MainActivity to update list with real data always
    public void setDebts(List<debt> debts) {
        this.debtList = debts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_debt, parent, false);
        return new DebtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder holder, int position) {
        debt currentDebt = debtList.get(position);

        holder.nameText.setText(currentDebt.getPersonName());
        holder.amountText.setText(currentDebt.getFormattedAmount());

        // Optional: visual cue for settled debts (optional)
        if (currentDebt.isSettled()) {
            holder.itemView.setAlpha(0.5f);
        } else {
            holder.itemView.setAlpha(1.0f);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDebtClick(currentDebt);
            }
        });
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
            nameText = itemView.findViewById(R.id.tv_person_name);
            amountText = itemView.findViewById(R.id.tv_amount);
        }
    }
}
