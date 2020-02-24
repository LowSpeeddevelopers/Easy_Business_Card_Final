package com.nexttech.easybusinesscard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nexttech.easybusinesscard.R;

public class ViewActivity extends AppCompatActivity {
    private ImageView imageFront, imageBack;
    Button btnSave,btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        imageFront=findViewById(R.id.image_front);
        imageBack=findViewById(R.id.image_back);
        btnSave=findViewById(R.id.btnSave);
        btnShare=findViewById(R.id.btnShare);

        String temp=getIntent().getStringExtra("template");

        if (temp.equals("temp1")){
            imageFront.setImageDrawable(getResources().getDrawable(R.drawable.temp1_front));
            imageBack.setImageDrawable(getResources().getDrawable(R.drawable.temp1_back));
        } else if (temp.equals("temp2")){
            imageFront.setImageDrawable(getResources().getDrawable(R.drawable.temp2_front));
            imageBack.setImageDrawable(getResources().getDrawable(R.drawable.temp2_back));
        } else if (temp.equals("temp3")){
            imageFront.setImageDrawable(getResources().getDrawable(R.drawable.temp3_front));
            imageBack.setImageDrawable(getResources().getDrawable(R.drawable.temp3_back));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewActivity.this, "Save", Toast.LENGTH_SHORT).show();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewActivity.this, "Share", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
