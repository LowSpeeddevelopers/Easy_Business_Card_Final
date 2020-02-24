package com.nexttech.easybusinesscard.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nexttech.easybusinesscard.Adapter.CardListAdapter;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import java.util.ArrayList;

public class CollectonsFragment extends Fragment {

    EditText edtSearch;
    RecyclerView rvCardList;

    public CollectonsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collectons, container, false);

        edtSearch = view.findViewById(R.id.edt_search);
        rvCardList = view.findViewById(R.id.rv_card_list);

        rvCardList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCardList.setHasFixedSize(true);

        ArrayList<UserInfoModel> cardList = new ArrayList<>();

        cardList.add(new UserInfoModel("Afroz Hossain", "Nai", "Jani na", "haray gase", "afroz.nero@gmail.com", "nai", "no", "01766226262", "www.nothing.com", "1234567"));

        CardListAdapter adapter = new CardListAdapter(getContext(), cardList);

        rvCardList.setAdapter(adapter);

        return view;
    }

}
