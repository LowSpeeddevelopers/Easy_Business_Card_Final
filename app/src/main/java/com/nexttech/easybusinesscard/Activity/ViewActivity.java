package com.nexttech.easybusinesscard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexttech.easybusinesscard.R;

import java.util.zip.Inflater;

public class ViewActivity extends AppCompatActivity {

    private ImageView imageFront, imageBack;
    Button btnSave,btnShare;

    View dialogueView;

    Drawable imageResourceFront, imageResourceBack;

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
            imageResourceFront = getResources().getDrawable(R.drawable.temp1_front);
            imageResourceBack = getResources().getDrawable(R.drawable.temp1_back);
        } else if (temp.equals("temp2")){
            imageResourceFront = getResources().getDrawable(R.drawable.temp2_front);
            imageResourceBack = getResources().getDrawable(R.drawable.temp2_back);
        } else if (temp.equals("temp3")){
            imageResourceFront = getResources().getDrawable(R.drawable.temp3_front);
            imageResourceBack = getResources().getDrawable(R.drawable.temp3_back);
        }


        dialogueView =getLayoutInflater().inflate(R.layout.activity_view, null);
//        ImageView cardBackgroundFront;
//        TextView cardName, cardDesignation, cardMobile;
//        cardBackgroundFront = dialogueView.findViewById(R.id.card_front_background);
//        cardName= dialogueView.findViewById(R.id.card_name);
//        cardDesignation= dialogueView.findViewById(R.id.card_designation);
//        cardMobile= dialogueView.findViewById(R.id.card_mobile);
//
//        cardBackgroundFront.setImageDrawable(imageResourceFront);
//
//        cardName.setText("Abcd");
//        cardDesignation.setText("efgh");
//        cardMobile.setText("23456");


        dialogueView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                dialogueView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                dialogueView.getHeight(); //height is ready

                Log.e("something","something");
                Log.e("height",String.valueOf(dialogueView.getHeight()));
                Log.e("width",String.valueOf(dialogueView.getWidth()));
                imageFront.setImageBitmap(loadBitmapFromView(dialogueView));
            }
        });



        imageBack.setImageDrawable(imageResourceBack);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);

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

    public Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
}
