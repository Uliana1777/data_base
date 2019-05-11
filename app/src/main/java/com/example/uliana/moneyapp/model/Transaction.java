package com.example.uliana.moneyapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "transaction")
public class Transaction  {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name = "sum")
    private float sum;
    @ColumnInfo(name = "addinfo")
    private String addInfo;

    @ColumnInfo(name = "date")
    private String date;

    /*@ColumnInfo (name = "category_id")
    private int category_id;*//*
    @ColumnInfo(name = "category")
    private Categories categories;*/

    public Transaction(int id, float sum, String addInfo, String date) {
        this.id = id;
        this.sum = sum;
        this.addInfo = addInfo;
        this.date = date;

    }
@Ignore
    public Transaction(float sum, String addInfo, String date) {
        this.sum = sum;
        this.addInfo = addInfo;
        this.date = date;
    }
    @Ignore
    public Transaction(String sum, String addInfo, String date) {

        this.sum = Integer.valueOf(sum);
        this.addInfo = addInfo;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
