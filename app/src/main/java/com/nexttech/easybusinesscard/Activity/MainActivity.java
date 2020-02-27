package com.nexttech.easybusinesscard.Activity;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nexttech.easybusinesscard.Fragments.CollectonsFragment;
import com.nexttech.easybusinesscard.Fragments.ScanFragment;
import com.nexttech.easybusinesscard.Fragments.TemplatesFragment;
import com.nexttech.easybusinesscard.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    TextView titleview;
    public static ImageView backbutton;
    public static ImageView navbutton;
    CardView home,setting, privacy_policy,help,about;

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
        setting = findViewById(R.id.button_setting);
        privacy_policy = findViewById(R.id.button_privacy);
        help = findViewById(R.id.button_help);
        about = findViewById(R.id.button_about_us);
        navbutton = toolbar.findViewById(R.id.navbutton);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,InformationActivity.class));
            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new TemplatesFragment());
       titleview.setText("Templates");
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_card:
                            navbutton.setVisibility(View.VISIBLE);
                            backbutton.setVisibility(View.VISIBLE);
                            openFragment(new TemplatesFragment());
                            titleview.setText("Templates");
                            return true;
                        case R.id.navigation_scan:
                            openFragment(new ScanFragment(MainActivity.this));
                            titleview.setText("Scan");
                            return true;
                        case R.id.navigation_collections:
                            navbutton.setVisibility(View.GONE);
                            backbutton.setVisibility(View.GONE);
                            openFragment(new CollectonsFragment());
                            titleview.setText("Collections");
                            return true;
                    }
                    return false;
                }
            };

}
