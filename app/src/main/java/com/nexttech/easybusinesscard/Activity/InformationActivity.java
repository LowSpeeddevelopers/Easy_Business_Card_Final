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


                if(name.equals("") || name.equals(null)) {
                    edtName.setError("Username can't be empty");
                    return;

                } else if (designation.equals("") || designation.equals(null)){
                    edtDesignation.setError("Please enter a designation");
                    return;

                } else if (project.equals("") || project.equals(null)) {
                    edtDivision.setError("Please enter a Division");
                    return;

                } else if (companyName.equals("") || companyName.equals(null)) {
                    edtCompanyName.setError("Please enter a Company Name");
                    return;

                }else if (email.equals("") || email.equals(null)||!email.matches(emailPattern)) {
                    edtEmail.setError("Please enter right email Address");
                    return;

                }
                else if (phone.equals("") || phone.equals(null)) {
                    edtPhone.setError("Please enter a valid phone number");
                    return;

                }else if (fax.equals("") || fax.equals(null)) {
                    edtFax.setError("Please enter a fax number");
                    return;

                }else if (mobile.equals("") || mobile.equals(null)) {
                    edtMobile.setError("Enter a valid mobile number");
                    return;

                }else if (web.equals("") || web.equals(null)) {
                    edtWebAddress.setError("Please enter a Web Address");
                    return;

                }else if (address.equals("") || address.equals(null)) {
                    edtAddress.setError("Please enter a Address");
                    return;
                } else {
                    UserInfoModel model = new UserInfoModel(name, designation, project, companyName, email, phone, fax, mobile, web, address);

                    businessCardDb.insertUserData(model);

                    Toast.makeText(InformationActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(InformationActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}
