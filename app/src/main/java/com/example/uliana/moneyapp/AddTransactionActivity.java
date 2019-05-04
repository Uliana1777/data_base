package com.example.uliana.moneyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;



import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {
    EditText sum, addinfo, category, date;
    int transactionId;
    Intent intent;
    Button button;
    private TransactionDatabase mDb;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        setTitle("Добавить трату");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = TransactionDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Transaction_Id)) {
            button.setText("Update");


            transactionId = intent.getIntExtra(Constants.UPDATE_Transaction_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Transaction person = mDb.transactionDao().loadPersonById(transactionId);
                    populateUI(person);
                }
            });


        }

    }

    private void populateUI(Transaction transaction) {

        if (transaction == null) {
            return;
        }

        sum.setText(transaction.getSum());
        addinfo.setText(transaction.getAddInfo());
        category.setText(transaction.getCategory());
        date.setText(transaction.getDate());

    }

    private void initViews() {
        sum = findViewById(R.id.edit_sum);
        addinfo = findViewById(R.id.edit_addinfo);
        category = findViewById(R.id.edit_category);
        date = findViewById(R.id.edit_date);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        final Transaction transaction = new Transaction(
                sum.getText().toString(),
                addinfo.getText().toString(),
                category.getText().toString(),
                date.getText().toString());


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Transaction_Id)) {
                    mDb.transactionDao().insertTransaction(transaction);
                } else {
                    transaction.setId(transactionId);
                    mDb.transactionDao().updateTransaction(transaction);
                }
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
