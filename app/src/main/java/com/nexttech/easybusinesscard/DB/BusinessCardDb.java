package com.nexttech.easybusinesscard.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.nexttech.easybusinesscard.Model.UserInfoModel;
import java.util.ArrayList;

public class BusinessCardDb extends DbHelper {

    public BusinessCardDb(Context context) {
        super(context);
    }

    public void insertUserData(UserInfoModel model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MY_INFO_COL_NAME, model.getName());
        contentValues.put(MY_INFO_COL_DESIGNATION, model.getDesignation());
        contentValues.put(MY_INFO_COL_PROJECT, model.getProject());
        contentValues.put(MY_INFO_COL_COMPANY_NAME, model.getCompanyName());
        contentValues.put(MY_INFO_COL_EMAIL, model.getEmail());
        contentValues.put(MY_INFO_COL_PHONE, model.getPhone());
        contentValues.put(MY_INFO_COL_FAX, model.getFax());
        contentValues.put(MY_INFO_COL_MOBILE, model.getMobile());
        contentValues.put(MY_INFO_COL_WEBSITE, model.getWebsite());
        contentValues.put(MY_INFO_COL_ADDRESS, model.getAddress());

        try {
            db.insert(DbHelper.MY_INFO_TABLE_NAME,null,contentValues);
        } catch (SQLException e)
        {
            Log.e("Insert Eroor", e.toString());
        }

        db.close();
    }

    public UserInfoModel getUserData(){
        UserInfoModel model = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_INFO_TABLE_NAME,null,null,null,null,null,null);
        while (c.moveToNext()){

            String name = c.getString(c.getColumnIndex(MY_INFO_COL_NAME));
            String designation = c.getString(c.getColumnIndex(MY_INFO_COL_DESIGNATION));
            String project = c.getString(c.getColumnIndex(MY_INFO_COL_PROJECT));
            String company_name = c.getString(c.getColumnIndex(MY_INFO_COL_COMPANY_NAME));
            String email = c.getString(c.getColumnIndex(MY_INFO_COL_EMAIL));
            String phone = c.getString(c.getColumnIndex(MY_INFO_COL_PHONE));
            String fax = c.getString(c.getColumnIndex(MY_INFO_COL_FAX));
            String mobile = c.getString(c.getColumnIndex(MY_INFO_COL_MOBILE));
            String website = c.getString(c.getColumnIndex(MY_INFO_COL_WEBSITE));
            String address = c.getString(c.getColumnIndex(MY_INFO_COL_ADDRESS));

            model = new UserInfoModel(name, designation, project, company_name, email, phone, fax, mobile, website, address);
        }

        db.close();
        c.close();
        return model;
    }

}