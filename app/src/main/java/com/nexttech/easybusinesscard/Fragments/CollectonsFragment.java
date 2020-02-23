package com.nexttech.easybusinesscard.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexttech.easybusinesscard.R;

public class CollectonsFragment extends Fragment {


    public CollectonsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collectons, container, false);

        return view;
    }

}
