package com.nexttech.easybusinesscard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexttech.easybusinesscard.DB.BusinessCardDb;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import com.nexttech.easybusinesscard.R;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class InformationActivity extends AppCompatActivity {

    EditText edtName, edtDesignation, edtDivision, edtCompanyName, edtEmail, edtPhone, edtFax, edtMobile, edtWebAddress, edtAddress;
    Button btnEnter;

    BusinessCardDb businessCardDb;
    ImageView back,option;

    TextView titleview;

    UserInfoModel userData;

    boolean registration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infromation);

        businessCardDb = new BusinessCardDb(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleview = toolbar.findViewById(R.id.titletext);
        back=toolbar.findViewById(R.id.backbutton);
        option = toolbar.findViewById(R.id.navbutton);
        back.setVisibility(View.GONE);
        option.setVisibility(View.GONE);
        titleview.setText("User Information");


        edtName=findViewById(R.id.edt_name);
        edtDesignation=findViewById(R.id.edt_designation);
        edtDivision=findViewById(R.id.edt_division);
        edtCompanyName=findViewById(R.id.edt_company_name);
        edtEmail=findViewById(R.id.edt_email);
        edtPhone=findViewById(R.id.edt_phone);
        edtFax=findViewById(R.id.edt_fax);
        edtMobile=findViewById(R.id.edt_mobile);
        edtWebAddress=findViewById(R.id.edt_web_address);
        edtAddress=findViewById(R.id.edt_address);
        btnEnter=findViewById(R.id.btn_enter);


        userData = businessCardDb.getUserData();


        if (userData!=null){
            edtName.setText(userData.getName());
            edtDesignation.setText(userData.getDesignation());
            edtDivision.setText(userData.getProject());
            edtCompanyName.setText(userData.getCompanyName());
            edtEmail.setText(userData.getEmail());
            edtPhone.setText(userData.getPhone());
            edtFax.setText(userData.getFax());
            edtMobile.setText(userData.getMobile());
            edtWebAddress.setText(userData.getWebsite());
            edtAddress.setText(userData.getAddress());
            registration = true;
        } else {
            registration = false;
        }

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String designation = edtDesignation.getText().toString();
                String project = edtDivision.getText().toString();
                String companyName = edtCompanyName.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String fax = edtFax.getText().toString();
                String mobile = edtMobile.getText().toString();
                String web = edtWebAddress.getText().toString();
                String address = edtAddress.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if(TextUtils.isEmpty(name)) {
                    edtName.setError("Name can't be empty");
                    edtName.requestFocus();
                    edtName.setCursorVisible(true);

                } else if (TextUtils.isEmpty(designation)){
                    edtDesignation.setError("Please enter a designation");
                    edtDesignation.requestFocus();
                    edtDesignation.setCursorVisible(true);

                } else if (TextUtils.isEmpty(project)) {
                    edtDivision.setError("Please enter a Division");
                    edtDivision.requestFocus();
                    edtDivision.setCursorVisible(true);

                } else if (TextUtils.isEmpty(companyName)) {
                    edtCompanyName.setError("Please enter a Company Name");
                    edtCompanyName.requestFocus();
                    edtCompanyName.setCursorVisible(true);

                }else if (TextUtils.isEmpty(email)||!email.matches(emailPattern)) {
                    edtEmail.setError("Please enter right email Address");
                    edtEmail.requestFocus();
                    edtEmail.setCursorVisible(true);

                }else if (TextUtils.isEmpty(mobile)) {
                    edtMobile.setError("mobile number is missing");

                }else if (mobile.length() != 11){
                    edtMobile.setError("mobile number must be 11 digit");

                }else if (!(mobile.startsWith("017") || mobile.startsWith("013") || mobile.startsWith("014") || mobile.startsWith("016") || mobile.startsWith("018") || mobile.startsWith("019") || mobile.startsWith("015"))){
                    edtMobile.setError("Please enter a valid mobile number");

                }else if (TextUtils.isEmpty(web)) {
                    edtWebAddress.setError("Please enter a Web Address");

                }else if (TextUtils.isEmpty(address)) {
                    edtAddress.setError("Please enter a Address");
                } else {
                    UserInfoModel model = new UserInfoModel(name, designation, project, companyName, email, phone, fax, mobile, web, address);

                    if (registration){
                        businessCardDb.updateUserData(model);
                    } else {
                        businessCardDb.insertUserData(model);
                    }

                    Intent i = new Intent(InformationActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}
