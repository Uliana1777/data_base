package com.example.uliana.moneyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uliana.moneyapp.R;
import com.example.uliana.moneyapp.database.CategoriesDatabase;
import com.example.uliana.moneyapp.model.Categories;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CardViewHolder>{
    List<Categories> categories;
    private Context context;

    public CategoriesAdapter (Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categories, parent, false);

        return new CardViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.CardViewHolder myViewHolder, int position) {
        Categories category = categories.get(position);
        myViewHolder.title.setText(category.getTitle());

    }
    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    public void setTasks(List<Categories> categoriesList) {
        categories = categoriesList;
        notifyDataSetChanged();
    }

    public List<Categories> getTasks() {

        return categories;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CategoriesDatabase cDb;

        CardViewHolder(@NonNull final View itemView) {
            super(itemView);
            cDb = CategoriesDatabase.getInstance(context);
            title = itemView.findViewById(R.id.textViewCategories);

        }



    }
}
