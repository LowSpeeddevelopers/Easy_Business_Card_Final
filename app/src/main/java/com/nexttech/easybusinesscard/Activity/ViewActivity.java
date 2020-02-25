package com.nexttech.easybusinesscard.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.VisibilityAwareImageButton;
import com.nexttech.easybusinesscard.R;

import java.util.zip.Inflater;

public class ViewActivity extends AppCompatActivity {


    Button btnSave,btnShare;

    View dialogueView;

    Drawable imageResourceFront, imageResourceBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);


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



        //View vi = getLayoutInflater().inflate(R.layout.business_card_front,null);
        View vi = LayoutInflater.from(this).inflate(R.layout.business_card_front,null);

        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        if(vi.getParent() != null) {
            ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
        }
        linearLayout.addView(vi);





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
