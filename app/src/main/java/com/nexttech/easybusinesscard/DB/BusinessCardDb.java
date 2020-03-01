package com.nexttech.easybusinesscard.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.nexttech.easybusinesscard.Model.CollectionCardModel;
import com.nexttech.easybusinesscard.Model.UserInfoModel;

import java.util.ArrayList;

public class BusinessCardDb extends DbHelper {

    public BusinessCardDb(Context context) {
        super(context);
    }

    public void insertUserData(UserInfoModel model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, model.getName());
        contentValues.put(COL_DESIGNATION, model.getDesignation());
        contentValues.put(COL_PROJECT, model.getProject());
        contentValues.put(COL_COMPANY_NAME, model.getCompanyName());
        contentValues.put(COL_EMAIL, model.getEmail());
        contentValues.put(COL_PHONE, model.getPhone());
        contentValues.put(COL_FAX, model.getFax());
        contentValues.put(COL_MOBILE, model.getMobile());
        contentValues.put(COL_WEBSITE, model.getWebsite());
        contentValues.put(COL_ADDRESS, model.getAddress());

        try {
            db.insert(DbHelper.MY_INFO_TABLE_NAME,null,contentValues);
        } catch (SQLException e)
        {
            Log.e("Insert Error", e.toString());
        }

        db.close();
    }

    public int updateUserData(UserInfoModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.COL_NAME, model.getName());
        contentValues.put(DbHelper.COL_DESIGNATION, model.getDesignation());
        contentValues.put(DbHelper.COL_PROJECT, model.getProject());
        contentValues.put(DbHelper.COL_COMPANY_NAME, model.getCompanyName() );
        contentValues.put(DbHelper.COL_EMAIL, model.getEmail());
        contentValues.put(DbHelper.COL_PHONE, model.getPhone());
        contentValues.put(DbHelper.COL_FAX, model.getFax());
        contentValues.put(DbHelper.COL_MOBILE, model.getMobile());
        contentValues.put(DbHelper.COL_WEBSITE, model.getWebsite());
        contentValues.put(DbHelper.COL_ADDRESS, model.getAddress());
        return db.update(DbHelper.MY_INFO_TABLE_NAME,contentValues,null,null);
    }

    public UserInfoModel getUserData(){
        UserInfoModel model = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_INFO_TABLE_NAME,null,null,null,null,null,null);
        while (c.moveToNext()){

            String name = c.getString(c.getColumnIndex(COL_NAME));
            String designation = c.getString(c.getColumnIndex(COL_DESIGNATION));
            String project = c.getString(c.getColumnIndex(COL_PROJECT));
            String company_name = c.getString(c.getColumnIndex(COL_COMPANY_NAME));
            String email = c.getString(c.getColumnIndex(COL_EMAIL));
            String phone = c.getString(c.getColumnIndex(COL_PHONE));
            String fax = c.getString(c.getColumnIndex(COL_FAX));
            String mobile = c.getString(c.getColumnIndex(COL_MOBILE));
            String website = c.getString(c.getColumnIndex(COL_WEBSITE));
            String address = c.getString(c.getColumnIndex(COL_ADDRESS));

            model = new UserInfoModel(name, designation, project, company_name, email, phone, fax, mobile, website, address);
        }

        db.close();
        c.close();
        return model;
    }

    public void insertOthersCardData(CollectionCardModel model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, model.getName());
        contentValues.put(COL_DESIGNATION, model.getDesignation());
        contentValues.put(COL_PROJECT, model.getProject());
        contentValues.put(COL_COMPANY_NAME, model.getCompanyName());
        contentValues.put(COL_EMAIL, model.getEmail());
        contentValues.put(COL_PHONE, model.getPhone());
        contentValues.put(COL_FAX, model.getFax());
        contentValues.put(COL_MOBILE, model.getMobile());
        contentValues.put(COL_WEBSITE, model.getWebsite());
        contentValues.put(COL_ADDRESS, model.getAddress());
        contentValues.put(COL_TEMPLATE, model.getCardTemplate());

        try {
            db.insert(DbHelper.MY_CARDS_TABLE_NAME,null,contentValues);
        } catch (SQLException e)
        {
            Log.e("Insert Error", e.toString());
        }

        db.close();
    }

    public ArrayList<CollectionCardModel> getCardData(){
        ArrayList<CollectionCardModel> cardData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_CARDS_TABLE_NAME,null,null,null,null,null,null);
        while (c.moveToNext()){

            String id = c.getString(c.getColumnIndex(COL_ID));
            String name = c.getString(c.getColumnIndex(COL_NAME));
            String designation = c.getString(c.getColumnIndex(COL_DESIGNATION));
            String project = c.getString(c.getColumnIndex(COL_PROJECT));
            String company_name = c.getString(c.getColumnIndex(COL_COMPANY_NAME));
            String email = c.getString(c.getColumnIndex(COL_EMAIL));
            String phone = c.getString(c.getColumnIndex(COL_PHONE));
            String fax = c.getString(c.getColumnIndex(COL_FAX));
            String mobile = c.getString(c.getColumnIndex(COL_MOBILE));
            String website = c.getString(c.getColumnIndex(COL_WEBSITE));
            String address = c.getString(c.getColumnIndex(COL_ADDRESS));
            String template = c.getString(c.getColumnIndex(COL_TEMPLATE));

            cardData.add(new CollectionCardModel(id, name, designation, project, company_name, email, phone, fax, mobile, website, address, template));
        }

        db.close();
        c.close();
        return cardData;
    }

    public CollectionCardModel getSingleCardData(String userId){
        CollectionCardModel singleCardData = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + DbHelper.MY_CARDS_TABLE_NAME+ " where "+ COL_ID+"="+ userId +"";
        Cursor c = db.rawQuery(query, null);

        while (c.moveToNext()){
            String id = c.getString(c.getColumnIndex(COL_ID));
            String name = c.getString(c.getColumnIndex(COL_NAME));
            String designation = c.getString(c.getColumnIndex(COL_DESIGNATION));
            String project = c.getString(c.getColumnIndex(COL_PROJECT));
            String company_name = c.getString(c.getColumnIndex(COL_COMPANY_NAME));
            String email = c.getString(c.getColumnIndex(COL_EMAIL));
            String phone = c.getString(c.getColumnIndex(COL_PHONE));
            String fax = c.getString(c.getColumnIndex(COL_FAX));
            String mobile = c.getString(c.getColumnIndex(COL_MOBILE));
            String website = c.getString(c.getColumnIndex(COL_WEBSITE));
            String address = c.getString(c.getColumnIndex(COL_ADDRESS));
            String template = c.getString(c.getColumnIndex(COL_TEMPLATE));

            singleCardData = new CollectionCardModel(id, name, designation, project, company_name, email, phone, fax, mobile, website, address, template);
        }

        db.close();
        c.close();
        return singleCardData;
    }

    public boolean getCardstatus(String mobile){
        Boolean status;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + MY_CARDS_TABLE_NAME+ " where "+COL_MOBILE+"=\""+ mobile +"\"";
        Cursor c = db.rawQuery(query, null);

        if(c != null && c.getCount()>0)
            status = true;
        else
            status = false;

        c.close();
        db.close();
        return status;
    }

    public void deleteCardData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbHelper.MY_CARDS_TABLE_NAME, DbHelper.COL_ID + "=" + id, null);

    }

}