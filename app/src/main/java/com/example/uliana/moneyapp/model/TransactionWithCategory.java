package com.example.uliana.moneyapp.model;

import com.example.uliana.moneyapp.model.Categories;
import com.example.uliana.moneyapp.model.Transaction;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TransactionWithCategory {
    @Embedded
     private Transaction transaction;
     @Relation(parentColumn ="id", entityColumn = "category_id", entity = Categories.class)
     private List<Categories> categories;
}
