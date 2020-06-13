package com.nexttech.easybusinesscard.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Fragments.CollectonsFragment;
import com.nexttech.easybusinesscard.Fragments.ScanFragment;
import com.nexttech.easybusinesscard.Fragments.TemplatesFragment;
import com.nexttech.easybusinesscard.Model.ContactModel;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    TextView titleview, tvUserName, tvUserDesignation, tvUserCompanyName;
    public static ImageView backbutton;
    public static ImageView navbutton;
    CardView home, privacy_policy, about, update;
    DrawerLayout mlayout;

    ScrollView scrollView;
    LinearLayout mainLayout;

    BusinessCardDb businessCardDb;
    UserInfoModel userData;

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
        about = findViewById(R.id.button_about_us);
        navbutton = toolbar.findViewById(R.id.navbutton);
        update = findViewById(R.id.button_updateinfo);

        tvUserName = findViewById(R.id.tv_user_name);
        tvUserDesignation = findViewById(R.id.tv_user_designation);
        tvUserCompanyName = findViewById(R.id.tv_user_company_name);

        fetchUserData();

        backbutton.setVisibility(View.GONE);


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("STATUS");


        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String valid_until = dataSnapshot.child("DATE").getValue().toString();
                String value = dataSnapshot.child("VALUE").getValue().toString();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                String finaldate = dateFormat.format(cal.getTime());
                Date today = new Date(finaldate);
                Date validuntil = new Date(valid_until);
                if (today.after(validuntil)) {
                    Toast.makeText(MainActivity.this, "invalid", Toast.LENGTH_SHORT).show();
                    Integer.parseInt(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemplatesFragment test = (TemplatesFragment) getSupportFragmentManager().findFragmentByTag("Templates");
                if (test != null && !test.isVisible()) {
                    openFragment(new TemplatesFragment(), "Templates");
                    mlayout.closeDrawer(GravityCompat.END);
                } else {
                    mlayout.closeDrawer(GravityCompat.END);
                }
            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PrivacyPolicy.class));
                mlayout.closeDrawer(GravityCompat.END);

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlayout.closeDrawer(GravityCompat.END);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(createContactUs());
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InformationActivity.class));
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
        openFragment(new TemplatesFragment(), "Templates");

    }

    private void fetchUserData() {
        businessCardDb = new BusinessCardDb(this);

        userData = businessCardDb.getUserData();

        if (userData != null) {
            tvUserName.setText(userData.getName());
            tvUserDesignation.setText(userData.getDesignation());
            tvUserCompanyName.setText(userData.getCompanyName());
        }
    }

    public void openFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.commit();
        titleview.setText(tag);

        if (tag.equals("Templates")) {
            navbutton.setVisibility(View.VISIBLE);
            backbutton.setVisibility(View.INVISIBLE);
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_card:
                            navbutton.setVisibility(View.VISIBLE);
                            backbutton.setVisibility(View.INVISIBLE);
                            openFragment(new TemplatesFragment(), "Templates");
                            return true;
                        case R.id.navigation_scan:
                            navbutton.setVisibility(View.GONE);
                            backbutton.setVisibility(View.GONE);
                            openFragment(new ScanFragment(MainActivity.this), "Scan");
                            return true;
                        case R.id.navigation_collections:
                            navbutton.setVisibility(View.VISIBLE);
                            backbutton.setVisibility(View.INVISIBLE);
                            openFragment(new CollectonsFragment(), "Collections");
                            return true;
                    }
                    return false;
                }
            };


    @Override
    public void onBackPressed() {
        TemplatesFragment test = (TemplatesFragment) getSupportFragmentManager().findFragmentByTag("Templates");
        if (test != null && test.isVisible()) {
            super.onBackPressed();
        } else {
            openFragment(new TemplatesFragment(), "Templates");
        }
    }

    private View createContactUs() {
        ArrayList<ContactModel> seniorDeveloper = new ArrayList<>();
        ArrayList<ContactModel> juniorDeveloper = new ArrayList<>();
        ArrayList<ContactModel> seniorDesign = new ArrayList<>();
        ArrayList<ContactModel> juniorDesign = new ArrayList<>();
        ArrayList<ContactModel> cardDesign = new ArrayList<>();
        ArrayList<ContactModel> logoDesign = new ArrayList<>();

        seniorDeveloper.add(new ContactModel("TW9oYS4gS2Fpc2VyIElxYmFs", "a2Fpc2VyaXFiYWwxMTFAZ21haWwuY29t", "MDE2ODQ0MjI1MjM=", "aHR0cHM6Ly9naXRodWIuY29tLzExMWthaXNlcg=="));
//        juniorDeveloper.add(new ContactModel("QWZyb3ogSG9zc2Fpbg==", "YWZyb3pob3NzYWluOTdAZ21haWwuY29t", "MDE3NjYyMjYyNjI=", "aHR0cHM6Ly9naXRodWIuY29tL2Fmcm96LW5lcm8="));
//        juniorDeveloper.add(new ContactModel("TmF5YW4gQ2hha3JhYmFydHk=", "bmF5YW5kY2M1QGdtYWlsLmNvbQ==", "MDE1MjEzODA5NzQ=", "aHR0cHM6Ly9naXRodWIuY29tL25heWFuY2hha3JhYmFydHk="));
//        juniorDeveloper.add(new ContactModel("UXVhemkgTWFoYWJ1YnVsIEhhc2Fu", "aHJpZG95aGFzYW4xNEBnbWFpbC5jb20=", "MDE5MTM2Mjg0MTA=", "aHR0cHM6Ly9naXRodWIuY29tL0hyaWRveUhhc2Fu"));
        juniorDeveloper.add(new ContactModel("U2hhaGFyaWFyIE5ld2F6IFRha2k=", "c2hhaGFyaWFybmF3c2hpbnRha2lAZ21haWwuY29t", "MDE2NzkxMzE0MTM=", "aHR0cHM6Ly9naXRodWIuY29tL3NoYWhhcmlhcnRha2k="));
        juniorDeveloper.add(new ContactModel("Ry5LLlNuaWdkaGE=", "Z2tzbmlnZGhhY3NlNDFAZ21haWwuY29t", "MDE3ODMwMzkyMzk=", "aHR0cHM6Ly9naXRodWIuY29tL0dLU25pZ2RoYQ=="));
        seniorDesign.add(new ContactModel("QWhhbWVkIFJpenZp", "cml6dmlhaGFtZWQxMDBAZ21haWwuY29t", "MDE2MTkxODc3NzA=", "aHR0cHM6Ly93d3cuYmVoYW5jZS5uZXQvcml6dmlhaGFtZWQ="));
        juniorDesign.add(new ContactModel("Uy4gTS4gUmFzaGlkdWwgSXNsYW0=", "bXJhc2hpZHVsMjAxMEBnbWFpbC5jb20=", "MDE3Nzg1NjIyMDA=", "aHR0cHM6Ly93d3cuYmVoYW5jZS5uZXQvcmFzaGlkdWw2Ng=="));
        cardDesign.add(new ContactModel("QWlmdXIgUmFobWFuIFNodXZv", "YXIuc2h1dm9AZ3JhcGhpYy1kZXNpZ25lci5jb20=", "MDE2ODgzMDE0Njk=", "aHR0cHM6Ly93d3cuYmVoYW5jZS5uZXQvYXJfc2h1dm8="));
        logoDesign.add(new ContactModel("QXNmYWsgTWFobXVk", "YXNmYWttYWhtdWRiZEBnbWFpbC5jb20=", "MDE3MzI5NDgxMDU=", "IA=="));


        scrollView = new ScrollView(this);
        LinearLayout.LayoutParams scrollParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setPadding(10, 10, 10, 10);
        scrollView.setLayoutParams(scrollParam);

        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams mainParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainLayout.setLayoutParams(mainParam);

        // Company Info

        LinearLayout companyInfo = new LinearLayout(this);
        companyInfo.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams companyParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        companyParam.setMargins(20,20,20,20);
        companyInfo.setLayoutParams(companyParam);

        LinearLayout companyLogo = new LinearLayout(this);
        companyLogo.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams companyLogoParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        companyLogo.setLayoutParams(companyLogoParam);

        companyLogo.setPadding(20, 20, 20, 20);

        LinearLayout.LayoutParams logoParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2);

        ImageView appleLogo = new ImageView(this);
        appleLogo.setLayoutParams(logoParam);
        appleLogo.setImageResource(R.drawable.apple_soft_logo);
        appleLogo.setAdjustViewBounds(true);

        View logoSpace = new View(this);
        LinearLayout.LayoutParams logoSpaceParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        logoSpace.setLayoutParams(logoSpaceParam);

        ImageView leadsLogo = new ImageView(this);
        leadsLogo.setLayoutParams(logoParam);
        leadsLogo.setImageResource(R.drawable.leads_logo);
        leadsLogo.setAdjustViewBounds(true);

        companyLogo.addView(appleLogo);
        companyLogo.addView(logoSpace);
        companyLogo.addView(leadsLogo);

        View view = new View(this);
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        viewParams.setMargins(0, 10, 0, 10);
        view.setLayoutParams(viewParams);
        view.setBackgroundColor(Color.GRAY);

        companyInfo.addView(companyLogo);
        companyInfo.addView(singleText("LEADS Training Ltd & Apple Soft IT JV", View.TEXT_ALIGNMENT_CENTER, 15));
        companyInfo.addView(view);

        // Company Info

        mainLayout.addView(singleText("Contact Us", View.TEXT_ALIGNMENT_CENTER, 24));
        mainLayout.addView(companyInfo);
        mainLayout.addView(addContact("Developer Coach", seniorDeveloper));
        mainLayout.addView(addContact("Developer", juniorDeveloper));
        mainLayout.addView(addContact("Design Coach", seniorDesign));
        mainLayout.addView(addContact("UI Design", juniorDesign));
        mainLayout.addView(addContact("Card Design", cardDesign));
        mainLayout.addView(addContact("Logo Design", logoDesign));

        scrollView.addView(mainLayout);

        return scrollView;
    }

    private TextView singleText(String text, int alignment, float size) {
        TextView tv = new TextView(this);
        tv.setText(text);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(tvParams);
        tv.setTextAlignment(alignment);
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(size);

        return tv;
    }

    private CardView addContact(String title, ArrayList<ContactModel> information) {
        CardView cardView = new CardView(this);

        LinearLayout.LayoutParams cardViewParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardViewParam.setMargins(20, 20, 20, 20);

        cardView.setLayoutParams(cardViewParam);
        cardView.setPadding(20, 20, 20, 20);
        cardView.setRadius(20);
        cardView.setCardBackgroundColor(Color.WHITE);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setPadding(20, 20, 20, 20);
        LinearLayout.LayoutParams linearParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linearParam);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(singleText(title, View.TEXT_ALIGNMENT_TEXT_START, 20));

        for (int i = 0; i < information.size(); i++) {
            ContactModel contactModel = information.get(i);

            View view = new View(this);
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
            viewParams.setMargins(0, 10, 0, 10);
            view.setLayoutParams(viewParams);
            view.setBackgroundColor(Color.GRAY);

            LinearLayout linearLayout1 = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout1.setLayoutParams(layoutParams);
            linearLayout1.setPadding(0, 10, 0, 0);
            linearLayout1.setOrientation(LinearLayout.VERTICAL);

            linearLayout1.addView(singleText(contactModel.getName(), View.TEXT_ALIGNMENT_TEXT_START, 18));
            linearLayout1.addView(singleText(contactModel.getContact(), View.TEXT_ALIGNMENT_TEXT_START, 16));
            linearLayout1.addView(singleText(contactModel.getEmail(), View.TEXT_ALIGNMENT_TEXT_START, 16));
            linearLayout1.addView(singleText(contactModel.getGithub(), View.TEXT_ALIGNMENT_TEXT_START, 16));

            linearLayout.addView(view);
            linearLayout.addView(linearLayout1);
        }

        cardView.addView(linearLayout);

        return cardView;
    }

}
