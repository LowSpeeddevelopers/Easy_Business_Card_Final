package com.nexttech.easybusinesscard.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String MY_INFO_TABLE_NAME = "my_info";
    static final String MY_CARDS_TABLE_NAME = "my_cards";
    static final String COL_ID = "id";
    static final String COL_NAME = "name";
    static final String COL_DESIGNATION = "designation";
    static final String COL_PROJECT = "project";
    static final String COL_COMPANY_NAME = "company_name";
    static final String COL_EMAIL = "email";
    static final String COL_PHONE = "phone";
    static final String COL_FAX = "fax";
    static final String COL_MOBILE = "mobile";
    static final String COL_WEBSITE = "website";
    static final String COL_ADDRESS = "address";
    static final String COL_TEMPLATE = "template";


    DbHelper(Context context) {
        super(context, "business_card_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryUserCard = "CREATE TABLE IF NOT EXISTS "+MY_INFO_TABLE_NAME+" ("+ COL_NAME +" TEXT,"+ COL_DESIGNATION +" TEXT,"+ COL_PROJECT +" TEXT,"+ COL_COMPANY_NAME +" TEXT, "+ COL_EMAIL +" TEXT, "+ COL_PHONE +" TEXT,"+ COL_FAX +" TEXT,"+ COL_MOBILE +" TEXT,"+ COL_WEBSITE +" TEXT,"+ COL_ADDRESS +" TEXT)";
        String queryOthersCard = "CREATE TABLE IF NOT EXISTS "+MY_CARDS_TABLE_NAME+" ("+ COL_ID +" INTEGER PRIMARY KEY,"+ COL_NAME +" TEXT,"+ COL_DESIGNATION +" TEXT,"+ COL_PROJECT +" TEXT,"+ COL_COMPANY_NAME +" TEXT, "+ COL_EMAIL +" TEXT, "+ COL_PHONE +" TEXT,"+ COL_FAX +" TEXT,"+ COL_MOBILE +" TEXT,"+ COL_WEBSITE +" TEXT,"+ COL_ADDRESS +" TEXT," + COL_TEMPLATE +" TEXT)";

        db.execSQL(queryUserCard);
        db.execSQL(queryOthersCard);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}