package com.franciscorivera.patitas.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MascotasDbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mascotas.db";

    public MascotasDbHelper(@Nullable Context contextn) {
        super(contextn, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists mascotas(name_mascota TEXT PRIMARY KEY," +
                "type_mascota TEXT NOT NULL,date_mascota TEXT NOT NULL," +
                "chip BOOLEAN NOT NULL,vacuna BOOLEAN,inscription BOOLEAN,tamnio TEXT NOT NULL" +
                ",peso REAL NOT NULL,name_duenio TEXT NOT NULL,email_duenio TEXT NOT NULL" +
                ",phone_duenio TEXT NOT NULL,dir_duenio TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
