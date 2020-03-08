package com.nexttech.easybusinesscard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.nexttech.easybusinesscard.Activity.MainActivity;
import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

public class AppIntroActivity extends AppIntro {

    BusinessCardDb businessCardDb;
    UserInfoModel userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_intro);

        addSlide(AppIntroFragment.newInstance("First App Intro","Fist App Intro Details",
                R.drawable.ic_card, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("First App Intro","Fist App Intro Details",
                R.drawable.ic_card, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("First App Intro","Fist App Intro Details",
                R.drawable.ic_card, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadNextScreen();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadNextScreen();
    }

    private void loadNextScreen(){
        businessCardDb = new BusinessCardDb(AppIntroActivity.this);

        userData = businessCardDb.getUserData();

        Intent intent;

        if (userData!=null){
            intent=new Intent(getApplicationContext(), MainActivity.class);
        } else {
            intent=new Intent(getApplicationContext(), InformationActivity.class);
        }

        startActivity(intent);

        finish();
    }

}
