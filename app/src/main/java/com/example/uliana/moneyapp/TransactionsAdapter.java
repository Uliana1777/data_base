package com.example.uliana.moneyapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter <TransactionsAdapter.CardViewHolder> {
    List<Transaction> transactions;
    public TransactionsAdapter( List <Transaction> transactions) {
        this.transactions = transactions;
    }




    @NonNull
    @Override
    public TransactionsAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from (parent.getContext()).inflate(R.layout.list_items, parent,false);

        return new CardViewHolder(itemView) ;
    }



    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.CardViewHolder cardViewHolder, int position) {
        Transaction transaction = transactions.get(position);
        cardViewHolder.title.setText(transaction.getTitle());
        cardViewHolder.date.setText(transaction.getDate());
        cardViewHolder.sum.setText(transaction.getSum());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView date;
        protected TextView sum;

        public CardViewHolder( View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            sum = itemView.findViewById(R.id.sum);


        }
    }
}
