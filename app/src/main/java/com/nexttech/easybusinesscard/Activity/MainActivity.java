package com.nexttech.easybusinesscard.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nexttech.easybusinesscard.Fragments.CollectonsFragment;
import com.nexttech.easybusinesscard.Fragments.ScanFragment;
import com.nexttech.easybusinesscard.Fragments.TemplatesFragment;
import com.nexttech.easybusinesscard.R;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    TextView titleview;
    public static ImageView backbutton;
    public static ImageView navbutton;
    CardView home, privacy_policy,help,about,update;
    DrawerLayout mlayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleview = toolbar.findViewById(R.id.titletext);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        backbutton = toolbar.findViewById(R.id.backbutton);
        home = findViewById(R.id.button_home);
        mlayout = findViewById(R.id.drawerlayout);

        privacy_policy = findViewById(R.id.button_privacy);
        help = findViewById(R.id.button_help);
        about = findViewById(R.id.button_about_us);
        navbutton = toolbar.findViewById(R.id.navbutton);
        update = findViewById(R.id.button_updateinfo);


//        if(!checkDataBase()){
//            startActivity(new Intent(this,IntroductionActivity.class));
//        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemplatesFragment test = (TemplatesFragment) getSupportFragmentManager().findFragmentByTag("Templates");
                if(test!=null && !test.isVisible()){
                    openFragment(new TemplatesFragment(),"Templates");
                    mlayout.closeDrawer(GravityCompat.END);
                }else {
                    mlayout.closeDrawer(GravityCompat.END);
                }
            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlayout.closeDrawer(GravityCompat.END);

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlayout.closeDrawer(GravityCompat.END);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlayout.closeDrawer(GravityCompat.END);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,InformationActivity.class));
                mlayout.closeDrawer(GravityCompat.END);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        navbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlayout.openDrawer(GravityCompat.END);
            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new TemplatesFragment(),"Templates");

    }

    public void openFragment(Fragment fragment,String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment,tag);
        transaction.commit();
        titleview.setText(tag);

        if(tag.equals("Templates")){
            navbutton.setVisibility(View.VISIBLE);
            backbutton.setVisibility(View.VISIBLE);
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_card:
                            navbutton.setVisibility(View.VISIBLE);
                            backbutton.setVisibility(View.VISIBLE);
                            openFragment(new TemplatesFragment(),"Templates");

                            return true;
                        case R.id.navigation_scan:
                            navbutton.setVisibility(View.GONE);
                            backbutton.setVisibility(View.GONE);
                            openFragment(new ScanFragment(MainActivity.this),"Scan");

                            return true;
                        case R.id.navigation_collections:
                            navbutton.setVisibility(View.VISIBLE);
                            backbutton.setVisibility(View.VISIBLE);
                            openFragment(new CollectonsFragment(),"Collections");
                            return true;
                    }
                    return false;
                }
            };

 
    @Override
    public void onBackPressed() {
        TemplatesFragment test = (TemplatesFragment) getSupportFragmentManager().findFragmentByTag("Templates");
        if(test!=null && test.isVisible()){
            super.onBackPressed();
        }else {
            openFragment(new TemplatesFragment(),"Templates");
        }
    }


    private boolean checkDataBase() {

        boolean isexists = false;
        try {
            SQLiteDatabase checkDB = SQLiteDatabase.openDatabase(this.getDatabasePath("business_card_db").getPath(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
            isexists = true;
        } catch (SQLiteException e) {
            // database doesn't exist yet.

        }
        return isexists;
    }

    public static Context getContext(){
        return getContext();
    }

}
