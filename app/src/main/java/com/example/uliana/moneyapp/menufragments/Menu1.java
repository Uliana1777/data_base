package com.example.uliana.moneyapp.menufragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uliana.moneyapp.AddTransactionActivity;
import com.example.uliana.moneyapp.database.AppExecutors;
import com.example.uliana.moneyapp.R;
import com.example.uliana.moneyapp.model.Transaction;
import com.example.uliana.moneyapp.database.TransactionDatabase;
import com.example.uliana.moneyapp.TransactionsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Menu1 extends Fragment {
    protected RecyclerView recyclerView;
    private TransactionsAdapter transactionsAdapter;
    private TransactionDatabase transactionDatabase;
    List<Transaction> data = new ArrayList<>();
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_menu_1, container, false);
        getActivity().setTitle("Траты");


        recyclerView = inflate.findViewById(R.id.list_item);
        FloatingActionButton floatingActionButton = inflate.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTransactionActivity.class);
                getActivity().startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        /* recyclerView.setHasFixedSize(true);*/


        recyclerView.setAdapter(transactionsAdapter);

        // Initialize the adapter and attach it to the RecyclerView
        transactionsAdapter = new TransactionsAdapter(inflate.getContext());
        recyclerView.setAdapter(transactionsAdapter);
        transactionDatabase = TransactionDatabase.getInstance(inflate.getContext());
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Transaction> tasks = transactionsAdapter.getTasks();
                        transactionDatabase.transactionDao().deleteTransaction(tasks.get(position));
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);
return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Transaction> transactions = transactionDatabase.transactionDao().getTransactionList();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        transactionsAdapter.setTasks(transactions);
                    }
                });
            }
        });


}

}
