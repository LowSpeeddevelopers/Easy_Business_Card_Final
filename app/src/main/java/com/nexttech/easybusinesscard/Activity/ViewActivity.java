package com.nexttech.easybusinesscard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nexttech.easybusinesscard.R;

public class ViewActivity extends AppCompatActivity {

    private ImageView imageFront, imageBack;
    Button btnSave,btnShare;

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

        imageFront.setImageDrawable(imageResourceFront);
        imageBack.setImageDrawable(imageResourceBack);

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
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
}
