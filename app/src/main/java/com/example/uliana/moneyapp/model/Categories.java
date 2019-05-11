package com.example.uliana.moneyapp.model;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Categories {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    public String title;

    public Categories(int id, String title) {
        this.id = id;
        this.title = title;
    }
    @Ignore
    public Categories(String title) {
        this.title = title;
    }

    public int getIdCategory() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdCategory(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
