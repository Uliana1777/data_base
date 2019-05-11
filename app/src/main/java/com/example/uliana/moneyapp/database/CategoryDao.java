package com.example.uliana.moneyapp.database;

import com.example.uliana.moneyapp.model.Categories;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM `categories` ORDER BY ID")
    List<Categories> getCategoryList ();
    @Insert
    void insertCategory (Categories categories);
    @Update
    void updateCategory (Categories categories);
    @Delete
    void deleteCategory  (Categories categories);

    @Query("SELECT * FROM `categories` WHERE id = :id")
    Categories loadCategoryById(int id);
}
