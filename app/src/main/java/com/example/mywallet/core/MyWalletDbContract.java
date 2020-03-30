package com.example.mywallet.core;

import android.provider.BaseColumns;

public class MyWalletDbContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyWalletDatabase";

    private MyWalletDbContract() {
    }

    public static class ItemTable implements BaseColumns {

        public static final String TABLE_NAME = "item";


        public static final String TITLE = "title";
        public static final String EXPENSE = "expense";

        public static final String SQL_CREATE_ITEMS_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        TITLE + " TEXT," +
                        EXPENSE + " INTEGER);";



    }

}
