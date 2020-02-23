package com.nexttech.easybusinesscard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nexttech.easybusinesscard.R;

import java.util.PrimitiveIterator;

public class InfromationActivity extends AppCompatActivity {

    private
    EditText name, designation, division, company_name, email, phone, fax, mobile, web_address, address;
    Button enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infromation);
        name=findViewById(R.id.edi_name);
        designation=findViewById(R.id.edi_designation);
        division=findViewById(R.id.edi_division);
        company_name=findViewById(R.id.edi_company_name);
        email=findViewById(R.id.edi_email);
        phone=findViewById(R.id.edi_phone);
        fax=findViewById(R.id.edi_fax);
        mobile=findViewById(R.id.edi_mobile);
        web_address=findViewById(R.id.edi_web_address);
        address=findViewById(R.id.edi_address);
        enter=findViewById(R.id.edi_btn_enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Designation = designation.getText().toString();
                String Division = division.getText().toString();
                String Comany = company_name.getText().toString();
                String Email = email.getText().toString();
                String Phone = phone.getText().toString();
                String Fax = fax.getText().toString();
                String Mobile = mobile.getText().toString();
                String Web =web_address.getText().toString();
                String Address = address.getText().toString();




                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String mobilepattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";

                if(Name.equals("") || Name.equals(null)) {
                    name.setError("Username can't be empty");
                    return;

                } else if (Designation.equals("") || Designation.equals(null)){
                    designation.setError("Please enter a designation");
                    return;

                } else if (Division.equals("") || Division.equals(null)) {
                    division.setError("Please enter a Division");
                    return;

                } else if (Comany.equals("") || Comany.equals(null)) {
                    company_name.setError("Please enter a Company Name");
                    return;

                }else if (Email.equals("") || Email.equals(null)||!Email.matches(emailPattern)) {
                    email.setError("Please enter right email Address");
                    return;

                }
                else if (Phone.equals("") || Phone.equals(null)) {
                    phone.setError("Please enter a valid phone number");
                    return;

                }else if (Fax.equals("") || Fax.equals(null)) {
                    fax.setError("Please enter a fax number");
                    return;

                }else if (Mobile.equals("") || Mobile.equals(null)||Mobile.matches(mobilepattern)) {
                    mobile.setError("Enter a valid mobile number");
                    return;

                }else if (Web.equals("") || Web.equals(null)) {
                    web_address.setError("Please enter a Web Address");
                    return;

                }else if (Address.equals("") || Address.equals(null)) {
                    address.setError("Please enter a Address");
                    return;
                }

            }
        });
    }
}
