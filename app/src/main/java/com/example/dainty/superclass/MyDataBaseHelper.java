package com.example.dainty.superclass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.aware.PublishConfig;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static volatile MyDataBaseHelper myDataBaseHelper = null;

    private Context mContext;

    private TeacherProcess teacherProcess;

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

    private static final String CREATE_TEACHER = "create table Teacher("
            +"teacherName text primary key,"
            +"teacherRealName text,"
            +"isInDatabase integer)";


    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_CLASSTABLE);
        //db.execSQL(CREATE_TEACHER);
       // Toast.makeText(mContext,"create successed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion){

            case 3:
                Log.d("case 3","success");
                db.execSQL(CREATE_TEACHER);

            case 4:
                db.execSQL("alter table Teacher add column isInDatabase integer");

            case 8:

                db.delete("Teacher",null,null);
                Log.d("delete","success");

            case 11:
                db.execSQL(CREATE_TEACHER);
                Log.d("create success","1");
                List<Teacher> teachers = teacherProcess.parseTeacherList();
                for (Teacher t :teachers){

                    ContentValues values = new ContentValues();
                    values.put("teacherName", t.getTeacherName());
                    values.put("teacherRealName",t.getTeacherRealName() );
                    values.put("isInDatabase",0);
                    db.insert("Teacher", null, values);
                }
                Log.d("teachertable","insert");



            default:
        }

    }

    public static List<Teacher> getTeacherList(SQLiteDatabase db,String name){
        String tname = "%"+name+"%";
       Cursor cursor = db.rawQuery("select * from Teacher where techerRealName Like "+tname,null);
       List<Teacher> teachers = new ArrayList<Teacher>();

        if(cursor.moveToFirst()){
            do{
                String teacherName=cursor.getString(cursor.getColumnIndex("teacherName"));
                String teacherRealName = cursor.getString(cursor.getColumnIndex("teacherRealName"));
                int isInDataBase = cursor.getInt(cursor.getColumnIndex("isInDatabase"));
                Log.d("teacher","teacherName:---"+teacherName);
                Log.d("teacher","teacherRealName----"+teacherRealName);
                Log.d("teacher","isInDataBase----"+isInDataBase);
                Teacher teacher = new Teacher(teacherName,teacherRealName);
                teacher.setIsInDatabase(isInDataBase);
                teachers.add(teacher);
            }while (cursor.moveToNext());
        }
        return teachers;

    }

    public void insertTeacher(){



        //teacher存入数据库
        //myDataBaseHelper = new MyDataBaseHelper(mContext,"SuperClass.db",null,6);
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
        Log.d("create success","1");
        List<Teacher> teachers = teacherProcess.parseTeacherList();
        for (Teacher t :teachers){

            ContentValues values = new ContentValues();
            values.put("teacherName", t.getTeacherName());
            values.put("teacherRealName",t.getTeacherRealName() );
            values.put("isInDatabase",0);
            db.insert("Teacher", null, values);
        }
        Log.d("teachertable","insert");

    }


}
