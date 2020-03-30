package com.example.mywallet.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.mywallet.core.MyWalletDbContract.*;
import static com.example.mywallet.core.MyWalletDbContract.ItemTable.SQL_CREATE_ITEMS_TABLE;


public class MyWalletDbHelper extends SQLiteOpenHelper {
    public MyWalletDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
