package com.example.tareas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DE_DATOS = "tareas",
            NOMBRE_TABLA_TAREAS = "tareas";
    private static final int VERSION_BASE_DE_DATOS = 1;

    public Helper (Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, nombre text, numero int)", NOMBRE_TABLA_TAREAS));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
