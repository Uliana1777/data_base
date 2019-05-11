package com.example.uliana.moneyapp.database;

import com.example.uliana.moneyapp.model.Transaction;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `TRANSACTION` ORDER BY ID")
    List <Transaction> getTransactionList ();
    @Insert
    void insertTransaction (Transaction transaction);
    @Update
    void updateTransaction (Transaction transaction);
    @Delete
    void deleteTransaction (Transaction transaction);

    @Query("SELECT * FROM `transaction` WHERE id = :id")
    Transaction loadTransactionById(int id);




}
