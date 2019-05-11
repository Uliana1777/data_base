package com.example.uliana.moneyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.uliana.moneyapp.database.AppExecutors;
import com.example.uliana.moneyapp.database.CategoriesDatabase;
import com.example.uliana.moneyapp.model.Categories;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity {
    EditText title;
    int categoryId;
    Intent intent;
    private CategoriesDatabase cDb;
    Button cancelButton;
    Button okButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_category);
        setTitle("Добавить категорию");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        cDb = CategoriesDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Categories_Id)) {

            categoryId = intent.getIntExtra(Constants.UPDATE_Transaction_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Categories category = cDb.categoryDao().loadCategoryById(categoryId);
                    populateUI(category);
                }
            });


        }

    }

    private void populateUI(Categories categories) {

        if (categories == null) {
            return;
        }

        title.setText(categories.getTitle());

    }
    private void initViews() {
        title = findViewById(R.id.editDialog);
        okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });

        cancelButton = findViewById(R.id.cancelButton);

    }

    public void onSaveButtonClicked() {
        final Categories categories = new Categories(
                title.getText().toString());


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Categories_Id)) {
                    cDb.categoryDao().insertCategory(categories);
                } else {
                    categories.setIdCategory(categoryId);
                    cDb.categoryDao().updateCategory(categories);
                }
                finish();
            }
        });
    }

}
