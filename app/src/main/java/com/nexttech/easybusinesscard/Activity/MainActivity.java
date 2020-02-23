package com.nexttech.easybusinesscard.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        titleview = toolbar.findViewById(R.id.titletext);

        bottomNavigation = findViewById(R.id.bottom_navigation);

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
                            openFragment(new TemplatesFragment());
                            titleview.setText("Templates");
                            return true;
                        case R.id.navigation_scan:
                            openFragment(new ScanFragment());
                            titleview.setText("Scan");
                            return true;
                        case R.id.navigation_collections:
                            openFragment(new CollectonsFragment());
                            titleview.setText("Collections");
                            return true;
                    }
                    return false;
                }
            };
}
