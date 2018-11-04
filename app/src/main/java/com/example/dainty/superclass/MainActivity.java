package com.example.dainty.superclass;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button next;
    private MyDataBaseHelper myDataBaseHelper;
    private TeacherProcess teacherProcess;
    private List<Teacher> teachers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtils.verifyStoragePermissions(this);

        myDataBaseHelper = new MyDataBaseHelper(this,"SuperClass.db",null,10);
        myDataBaseHelper.getWritableDatabase();



        next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ClassActivity.class);
                startActivity(intent);
            }
        });



    }


}
