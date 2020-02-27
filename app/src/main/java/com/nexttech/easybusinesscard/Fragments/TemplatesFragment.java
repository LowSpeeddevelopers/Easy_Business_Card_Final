package com.nexttech.easybusinesscard.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nexttech.easybusinesscard.Activity.MainActivity;
import com.nexttech.easybusinesscard.Activity.ViewActivity;
import com.nexttech.easybusinesscard.R;

public class TemplatesFragment extends Fragment {

    private LinearLayout temp1, temp2, temp3;


    public TemplatesFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_templates, container, false);

        temp1=view.findViewById(R.id.temp1);
        temp2=view.findViewById(R.id.temp2);
        temp3=view.findViewById(R.id.temp3);

        temp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), ViewActivity.class);
                i.putExtra("template","temp1");
                startActivity(i);
            }
        });

        temp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), ViewActivity.class);
                i.putExtra("template","temp2");
                startActivity(i);
            }
        });

        temp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), ViewActivity.class);
                i.putExtra("template","temp3");
                startActivity(i);
            }
        });


        return view;
    }

}
