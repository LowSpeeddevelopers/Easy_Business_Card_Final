package com.nexttech.easybusinesscard.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CollectonsFragment extends Fragment {

    EditText edtSearch;
    RecyclerView rvCardList;

    BusinessCardDb businessCardDb;
    ArrayList<CollectionCardModel> cardCollections;

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

        CardListAdapter adapter = new CardListAdapter(getContext(), cardCollections);

        rvCardList.setAdapter(adapter);

        return view;
    }

}
