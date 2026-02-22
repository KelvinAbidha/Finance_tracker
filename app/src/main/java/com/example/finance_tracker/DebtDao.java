package com.example.finance_tracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface DebtDao {

    @Insert
    void insert(debt debt);

    @Update
    void update(debt debt);

    @Delete
    void delete(debt debt);

    @Query("SELECT * FROM debts ORDER BY date_created DESC")
    LiveData<List<debt>> getAllDebts();

    // Nesh: mark as fully paid
    @Query("UPDATE debts " +
            "SET is_settled = 1, " +
            "    status = 'PAID', " +
            "    amount_paid = amount, " +
            "    date_updated = :now " +
            "WHERE debt_id = :debtId")
    void markDebtAsPaid(int debtId, Date now);
}
// Nesh logic implemented