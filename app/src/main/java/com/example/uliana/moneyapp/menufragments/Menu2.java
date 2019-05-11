package com.example.uliana.moneyapp.menufragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uliana.moneyapp.AddCategoryActivity;
import com.example.uliana.moneyapp.database.AppExecutors;
import com.example.uliana.moneyapp.model.Categories;
import com.example.uliana.moneyapp.adapter.CategoriesAdapter;
import com.example.uliana.moneyapp.database.CategoriesDatabase;
import com.example.uliana.moneyapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Menu2 extends Fragment {
    List<Categories> data = new ArrayList<>();
    private CategoriesAdapter categoriesAdapter;
    private CategoriesDatabase categoriesDatabase;
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_menu_2, container, false);
        getActivity().setTitle("Категории");
        recyclerView = inflate.findViewById(R.id.list_categories);
        FloatingActionButton floatingActionButton = inflate.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                getActivity().startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(categoriesAdapter);

        categoriesAdapter = new CategoriesAdapter(inflate.getContext());
        recyclerView.setAdapter(categoriesAdapter);
        categoriesDatabase = CategoriesDatabase.getInstance(inflate.getContext());
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
                        List<Categories> tasks = categoriesAdapter.getTasks();
                        categoriesDatabase.categoryDao().deleteCategory(tasks.get(position));
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
                final List<Categories> categories = categoriesDatabase.categoryDao().getCategoryList();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        categoriesAdapter.setTasks(categories);
                    }
                });
            }
        });


    }
}

