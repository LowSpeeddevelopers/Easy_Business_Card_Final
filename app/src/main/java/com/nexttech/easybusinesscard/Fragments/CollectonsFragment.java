package com.nexttech.easybusinesscard.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nexttech.easybusinesscard.Activity.MainActivity;
import com.nexttech.easybusinesscard.Adapter.CardListAdapter;
import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.CollectionCardModel;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import java.util.ArrayList;
import java.util.List;

public class CollectonsFragment extends Fragment {

    EditText edtSearch;
    RecyclerView rvCardList;

    BusinessCardDb businessCardDb;
    ArrayList<CollectionCardModel> cardCollections;

    CardListAdapter adapter;

    public CollectonsFragment() {
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            MainActivity.backbutton.setVisibility(View.GONE);
            MainActivity.navbutton.setVisibility(View.GONE);
        }

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

                String search = edtSearch.getText().toString();

                ArrayList<CollectionCardModel> cardSearchCollections = new ArrayList<>();
                for (CollectionCardModel model : cardCollections) {
                    if (model.getName().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getDesignation().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getMobile().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getCompanyName().contains(search)){
                        cardSearchCollections.add(model);
                    } else if (model.getEmail().contains(search)){
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
