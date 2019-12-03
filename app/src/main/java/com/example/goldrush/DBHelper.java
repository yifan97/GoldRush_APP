package com.example.goldrush;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "user.db";


    public DBHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersContract.UserTABLE.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            db.execSQL(UsersContract.UserTABLE.CREATE_TABLE);
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + UsersContract.UserTABLE.TABLE_NAME);
        onCreate(db);
    }
}
