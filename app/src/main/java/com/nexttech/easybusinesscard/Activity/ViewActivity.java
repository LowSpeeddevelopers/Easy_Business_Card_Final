package com.nexttech.easybusinesscard.Activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;


public class ViewActivity extends AppCompatActivity {


    Button btnSave,btnShare;

    Drawable imageResourceFront, imageResourceBack;

    ImageView cardFrontBackground, cardBackBackground;
    TextView cardName, cardDesignation, cardMobile, cardAddress;

    BusinessCardDb businessCardDb;
    UserInfoModel userData;

    View dialogueView;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        businessCardDb = new BusinessCardDb(ViewActivity.this);
        userData = businessCardDb.getUserData();

        btnSave=findViewById(R.id.btnSave);
        btnShare=findViewById(R.id.btnShare);
        builder = new AlertDialog.Builder(ViewActivity.this);

        String temp=getIntent().getStringExtra("template");

        assert temp != null;
        switch (temp) {
            case "temp1":
                imageResourceFront = getResources().getDrawable(R.drawable.temp1_front);
                imageResourceBack = getResources().getDrawable(R.drawable.temp1_back);
                break;
            case "temp2":
                imageResourceFront = getResources().getDrawable(R.drawable.temp2_front);
                imageResourceBack = getResources().getDrawable(R.drawable.temp2_back);
                break;
            case "temp3":
                imageResourceFront = getResources().getDrawable(R.drawable.temp3_front);
                imageResourceBack = getResources().getDrawable(R.drawable.temp3_back);
                break;
        }


        View vi = LayoutInflater.from(this).inflate(R.layout.business_card_front,null);

        cardFrontBackground = vi.findViewById(R.id.card_front_background);
        cardBackBackground = vi.findViewById(R.id.card_back_background);
        cardName = vi.findViewById(R.id.card_name);
        cardDesignation = vi.findViewById(R.id.card_designation);
        cardMobile = vi.findViewById(R.id.card_mobile);
        cardAddress = vi.findViewById(R.id.card_address);

        cardFrontBackground.setImageDrawable(imageResourceFront);
        cardBackBackground.setImageDrawable(imageResourceBack);

        cardName.setText(userData.getName());
        cardDesignation.setText(userData.getDesignation());
        cardMobile.setText(userData.getMobile());
        cardAddress.setText(userData.getAddress());

        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        if(vi.getParent() != null) {
            ((ViewGroup)vi.getParent()).removeView(vi); // <- fix
        }
        linearLayout.addView(vi);





        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewActivity.this, "Save", Toast.LENGTH_SHORT).show();
                dialogueView = getLayoutInflater().inflate(R.layout.share_dialoguebox, null);
                TextView savediologue, card,qrcode, cancel;
                savediologue = dialogueView.findViewById(R.id.tv_save_dialog);
                card= dialogueView.findViewById(R.id.tv_card);
                qrcode = dialogueView.findViewById(R.id.tv_qrcode);
                cancel = dialogueView.findViewById(R.id.tv_cancel);

                builder.setView(null);
                builder.setView(dialogueView);
                alertDialog=builder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialogDismiss();
                alertDialog.show();
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
    private void alertDialogDismiss(){
        if (alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }
}

