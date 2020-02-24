package com.nexttech.easybusinesscard.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    static final String MY_INFO_TABLE_NAME = "my_info";
    static final String MY_CARDS_TABLE_NAME = "my_cards";
    static final String MY_INFO_COL_NAME = "name";
    static final String MY_INFO_COL_DESIGNATION = "designation";
    static final String MY_INFO_COL_PROJECT = "project";
    static final String MY_INFO_COL_COMPANY_NAME = "company_name";
    static final String MY_INFO_COL_EMAIL = "email";
    static final String MY_INFO_COL_PHONE = "phone";
    static final String MY_INFO_COL_FAX = "fax";
    static final String MY_INFO_COL_MOBILE = "mobile";
    static final String MY_INFO_COL_WEBSITE = "website";
    static final String MY_INFO_COL_ADDRESS = "address";


    DbHelper(Context context) {
        super(context, "business_card_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE IF NOT EXISTS "+MY_INFO_TABLE_NAME+" ("+MY_INFO_COL_NAME+" TEXT,"+MY_INFO_COL_DESIGNATION+" TEXT,"+MY_INFO_COL_PROJECT+" TEXT,"+MY_INFO_COL_COMPANY_NAME+" TEXT, "+MY_INFO_COL_EMAIL+" TEXT, "+MY_INFO_COL_PHONE+" TEXT,"+MY_INFO_COL_FAX+" TEXT,"+MY_INFO_COL_MOBILE+" TEXT,"+MY_INFO_COL_WEBSITE+" TEXT,"+MY_INFO_COL_ADDRESS+" TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}