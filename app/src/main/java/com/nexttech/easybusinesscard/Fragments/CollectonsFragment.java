package com.nexttech.easybusinesscard.Fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nexttech.easybusinesscard.Adapter.CardListAdapter;
import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.CollectionCardModel;
import com.nexttech.easybusinesscard.R;

import java.util.ArrayList;

public class CollectonsFragment extends Fragment {

    EditText edtSearch;
    RecyclerView rvCardList;

    BusinessCardDb businessCardDb;
    ArrayList<CollectionCardModel> cardCollections;

    CardListAdapter adapter;

    public CollectonsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collectons, container, false);
        edtSearch = view.findViewById(R.id.edt_search);
        rvCardList = view.findViewById(R.id.rv_card_list);
        businessCardDb = new BusinessCardDb(getContext());
        cardCollections = businessCardDb.getCardData();
        rvCardList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCardList.setHasFixedSize(true);
        adapter = new CardListAdapter(getContext(), cardCollections);
        rvCardList.setAdapter(adapter);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String search = edtSearch.getText().toString().toLowerCase();

                ArrayList<CollectionCardModel> cardSearchCollections = new ArrayList<>();
                for (CollectionCardModel model : cardCollections) {
                    if (model.getName().toLowerCase().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getDesignation().toLowerCase().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getMobile().toLowerCase().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getCompanyName().toLowerCase().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getEmail().toLowerCase().contains(search)){
                        cardSearchCollections.add(model);
                    }
                }

                adapter = new CardListAdapter(getContext(), cardSearchCollections);

                rvCardList.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

}
