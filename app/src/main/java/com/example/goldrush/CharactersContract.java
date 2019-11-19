package com.example.goldrush;

import android.provider.BaseColumns;

public class CharactersContract {

    private CharactersContract(){}

    public static class CharacterTABLE implements BaseColumns{
        public static final String TABLE_NAME = "characters";
        public static final String USER_NAME = "user_name";
        public static final String HIGHEST_SCORE = "score";

        public static final String CREATE_TABLE = "CREATE TABLE "
                + TABLE_NAME + " （"
                + USER_NAME + " TEXT PRIMARY KEY, "
                + HIGHEST_SCORE + " INTEGER ";
    }
}
