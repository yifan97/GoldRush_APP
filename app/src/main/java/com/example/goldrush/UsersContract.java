package com.example.goldrush;

import android.provider.BaseColumns;

public class UsersContract {

    private UsersContract(){}

    public static class UserTABLE implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String USER_NAME = "user_name";
        public static final String HIGHEST_SCORE = "score";

        public static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " （"
                + USER_NAME + " TEXT PRIMARY KEY, "
                + HIGHEST_SCORE + " String)";
    }
}
