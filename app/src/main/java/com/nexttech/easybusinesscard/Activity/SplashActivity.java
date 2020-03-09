package com.nexttech.easybusinesscard.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

public class SplashActivity extends AppCompatActivity {
    ImageView imageView;

    TextView textView;

    BusinessCardDb businessCardDb;
    UserInfoModel userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.imageview);
        textView = findViewById(R.id.text);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                businessCardDb = new BusinessCardDb(SplashActivity.this);

                userData = businessCardDb.getUserData();

                Activity context;
                Intent intent;

                if (userData!=null){
                    context = new MainActivity();
                } else {
                    context = new InformationActivity();
                }

                //startActivity(intentNext);

                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                if (pref.getBoolean("activity_executed", false)){
                    intent = new Intent(SplashActivity.this,context.getClass());
                } else {
                    intent = new Intent(SplashActivity.this,AppIntroActivity.class);

                    SharedPreferences.Editor ed = pref.edit();
                    ed.putBoolean("activity_executed", true);
                    ed.commit();

                }

                startActivity(intent);
                finish();
            }
        },5000);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.goup);
        myanim.setFillAfter(true);

        myanim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                textView.setVisibility(View.VISIBLE);


                Animation anim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.mysplashanimation);
                textView.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.startAnimation(myanim);



    }


}
