package com.example.uliana.moneyapp.menufragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.github.clans.fab.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uliana.moneyapp.AddTransactionActivity;
import com.example.uliana.moneyapp.R;
import com.example.uliana.moneyapp.Transaction;
import com.example.uliana.moneyapp.TransactionsAdapter;

import java.util.ArrayList;
import java.util.List;

public class Menu1 extends Fragment {
    private RecyclerView recyclerView;
    private TransactionsAdapter transactionsAdapter;
    List<Transaction> data = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_menu_1, container, false);
        List <Transaction> adapterData = getDataList();
        transactionsAdapter = new TransactionsAdapter( adapterData);

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

        return inflate;
    }
    private List <Transaction> getDataList(){
        data.add(new Transaction("telephone",200,"13.04.2019"));
        data.add(new Transaction("telephone",200,"13.04.2019"));
        data.add(new Transaction("telephone",200,"13.04.2019"));
        data.add(new Transaction("telephone",200,"13.04.2019"));
        data.add(new Transaction("telephone",200,"13.04.2019"));
        data.add(new Transaction("telephone",200,"13.04.2019"));

        return data;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Траты");
    }
}
