package com.example.dainty.superclass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.aware.PublishConfig;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String CREATE_STUDENT = "create table Student ("
            + "stuId integer primary key autoincrement,"
            + "sName text,"
            + "sPassword text)";

    public static final String CREATE_CLASSTABLE = "create table ClassTable("
            + "classTableId integer primary key autoincrement,"
            + "classLine integer,"
            + "classColumn integer,"
            + "teacherName text,"
            + "semester text,"
            + "inClass text)";

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_CLASSTABLE);
       // Toast.makeText(mContext,"create successed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists Student");
        db.execSQL("drop table if exists ClassTable");

        onCreate(db);

    }
}
