package com.example.uliana.moneyapp;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.uliana.moneyapp.database.TransactionDatabase;
import com.example.uliana.moneyapp.model.Transaction;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter <TransactionsAdapter.MyViewHolder> {
    List<Transaction> transactions;
    protected Context context;
    protected int lastPosition = -1;


    public TransactionsAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.MyViewHolder myViewHolder, int position) {
        Transaction transaction = transactions.get(position);
        myViewHolder.title.setText(transaction.getAddInfo());
        myViewHolder.date.setText(transaction.getDate());
        myViewHolder.sum.setText(Float.toString(transaction.getSum()));

        /*myViewHolder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);*/
        setAnimation(myViewHolder.cardView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        if (transactions == null) {
            return 0;
        }
        return transactions.size();
    }


    public void setTasks(List<Transaction> transactionList) {
        transactions = transactionList;
        notifyDataSetChanged();
    }

    public List<Transaction> getTasks() {

        return transactions;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, sum;
        TransactionDatabase mDb;
        protected View selectedOverlay;
        protected CardView cardView;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = TransactionDatabase.getInstance(context);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            sum = itemView.findViewById(R.id.sum);
            cardView = itemView.findViewById(R.id.cardView);

        }


    }
}
