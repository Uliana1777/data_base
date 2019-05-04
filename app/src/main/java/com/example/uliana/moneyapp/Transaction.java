package com.example.uliana.moneyapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "transaction")
public class Transaction  {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name = "sum")
    private String sum;
    @ColumnInfo(name = "addinfo")
    private String addInfo;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "date")
    private String date;

    public Transaction(int id, String sum, String addInfo, String category, String date) {
        this.id = id;
        this.sum = sum;
        this.addInfo = addInfo;
        this.category = category;
        this.date = date;
    }
@Ignore
    public Transaction(String sum, String addInfo, String category, String date) {
        this.sum = sum;
        this.addInfo = addInfo;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
