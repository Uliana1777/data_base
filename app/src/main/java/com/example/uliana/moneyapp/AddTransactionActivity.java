package com.example.uliana.moneyapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.uliana.moneyapp.adapter.CategoriesAdapter;
import com.example.uliana.moneyapp.database.AppExecutors;
import com.example.uliana.moneyapp.database.CategoriesDatabase;
import com.example.uliana.moneyapp.database.TransactionDatabase;
import com.example.uliana.moneyapp.model.Categories;
import com.example.uliana.moneyapp.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText sum, addinfo,etToday;
    private Button btnDatePicker;
    Spinner spinner;
    int transactionId;
    int categoryId;
    Intent intent;
    Button button;
    List<Categories> categories = new ArrayList<>();
    private CategoriesAdapter categoriesAdapter;
    private CategoriesDatabase categoriesDatabase;
    private int mYear, mMonth, mDay;
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
                    Transaction person = mDb.transactionDao().loadTransactionById(transactionId);
                    populateUI(person);
                }
            });


        }
        categoriesAdapter = new CategoriesAdapter(getApplicationContext());
        categoriesDatabase = CategoriesDatabase.getInstance(getApplicationContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                List<Categories> tasks = categoriesDatabase.categoryDao().getCategoryList();
                initSpinner(tasks);
                retrieveTasks();
            }
        });


        /*categories = categoriesDatabase.categoryDao().getCategoryList();
        String[] items = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            items[i] = categories.get(i).title;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
*/
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Categories> categories = categoriesDatabase.categoryDao().getCategoryList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        categoriesAdapter.setTasks(categories);

                    }
                });
            }
        });


    }

    private void populateUI(Transaction transaction) {

        if (transaction == null) {
            return;
        }

        sum.setText(Float.toString(transaction.getSum()));
        addinfo.setText(transaction.getAddInfo());
        etToday.setText(transaction.getDate());

    }
    private void initSpinner(List<Categories> tasks){
        if (tasks == null) {
            return;
        }
        String[] items = new String[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            items[i] = tasks.get(i).title;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initViews() {
        sum = findViewById(R.id.edit_sum);
        addinfo = findViewById(R.id.edit_addinfo);
        /*category = findViewById(R.id.edit_category);*/
        etToday = findViewById(R.id.etToday);
        btnDatePicker = findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(this);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
        spinner = findViewById(R.id.spinner_category);
    }



    public void onSaveButtonClicked() {
        final Transaction transaction = new Transaction(
                sum.getText().toString(),
                addinfo.getText().toString(),
                etToday.getText().toString());


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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_date:
                // вызываем диалог с выбором даты
                callDatePicker();
                break;}

        }
    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        etToday.setText(editTextDateParam);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private SelectedDate listener;
        private int year, month, day;
        public interface SelectedDate {
            void getDate(int year, int month, int day);
        }

        public static DatePickerFragment getNewInstance(long timeInMillis, SelectedDate listener) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.listener = listener;

            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(timeInMillis);
            datePickerFragment.year = calendar.get(Calendar.YEAR);
            datePickerFragment.month = calendar.get(Calendar.MONTH);
            datePickerFragment.day = calendar.get(Calendar.DAY_OF_MONTH);

            return datePickerFragment;
        }

        public static DatePickerFragment getNewInstance(int year, int monthOfYear, int dayOfMonth, SelectedDate listener) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.listener = listener;
            datePickerFragment.year = year;
            datePickerFragment.month = monthOfYear;
            datePickerFragment.day = dayOfMonth;

            return datePickerFragment;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            listener.getDate(year, month, day);
        }
    }




}
