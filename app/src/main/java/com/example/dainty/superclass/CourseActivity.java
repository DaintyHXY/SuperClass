package com.example.dainty.superclass;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class CourseActivity extends Activity {

    private String teacherName;
    private String semesterTime;

    private TextView line1Column1;

    private TextView[][] lineColumn =new TextView[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        PermissionUtils.verifyStoragePermissions(this);

        line1Column1 = (TextView)findViewById(R.id.line1Column1);
        line1Column1.setText("aaa");

        for(int i=0;i<5;i++){

            lineColumn[4][i] = (TextView)findViewById(R.id.line1Column1);

        }






    }



}
