package com.example.dainty.superclass;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends Activity {

    private MyDataBaseHelper myDataBaseHelper;

    public static final int UPDATE_PIC = 99;

    private Data data = new Data();

    private Button search;
    private Spinner semester;
    private Spinner teacher;
    private String sem;
    private String teacherName;
    private String security;



    //getCode
    private ImageView identifyCode;
    private Bitmap identifyPicture;



    private File classTable;

    //getSchedule
    private Handler handler = new Handler();
    private TextView textView1;
    private EditText securityCode;
    private List<List<String>> rowsList = new ArrayList<List<String>>();



    private Button insert;
    private TextView sName;
    private TextView sPassword;


    private Handler handler2 = new Handler(){

        public void handleMessage(Message msg){

            switch(msg.what){
                case UPDATE_PIC:
                    //更新验证码
                    identifyCode.setImageBitmap(identifyPicture);
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        PermissionUtils.verifyStoragePermissions(this);

        search = (Button)findViewById(R.id.toSearch);
//        insert = (Button)findViewById(R.id.insert);
//        sName = (TextView)findViewById(R.id.sname);
//        sPassword = (TextView)findViewById(R.id.spassword);


        semester = (Spinner)findViewById(R.id.semester);
        teacher = (Spinner)findViewById(R.id.teacher);

        identifyCode = (ImageView)findViewById(R.id.img_securitycode);

        getCookieAndValidateCode();

        securityCode = (EditText)findViewById(R.id.securitycode);

        //创建数据库
        myDataBaseHelper = new MyDataBaseHelper(this,"SuperClass.db",null,1);
        //myDataBaseHelper.getWritableDatabase();

//        //验证码图片
//        identifyPicture  = dataProcess.getCookieAndValidateCode(teacherName,sem);
//        identifyCode = (ImageView) findViewById(R.id.img_securitycode);
//        identifyCode.setImageBitmap(identifyPicture);

        //学期和教师名字选中
        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sem = (String) semester.getSelectedItem();
                Log.d("info","semester:"+sem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherName = (String) teacher.getSelectedItem();
                Log.d("info","teacher:"+teacherName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                try {
//                    getScheduleByTea(v);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                 security = securityCode.getText().toString();
                //跳转到下一页面，并将学期和教师数据传输给下一个页面
                Intent intent = new Intent(ClassActivity.this,CourseActivity.class);

                intent.putExtra("semester",sem);
                intent.putExtra("teacher",teacherName);
                intent.putExtra("yzm",security);

               // intent.putExtra("classTable",rowsList);

                startActivity(intent);

            }
        });

//        //数据库简单测试
//        insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put("sName","huang");
//                values.put("sPassword","123");
//                db.insert("Student",null,values);
//                Log.d("info","inset success");
//
//                Cursor cursor = db.query("Student",null,null,null,null,null,null);
//                if(cursor.moveToFirst()){
//
//                    do{
//                        String name = cursor.getString(cursor.getColumnIndex("sName"));
//                        String password = cursor.getString(cursor.getColumnIndex("sPassword"));
//                        sName.setText(name);
//                        sPassword.setText(password);
//                     }while (cursor.moveToNext());
//
//                }
//
//
//                cursor.close();
//
//            }
//        });


    }



    public void getCookieAndValidateCode() {



        new Thread() {
            public void run() {
                try{
                    String address = "http://121.248.70.120/jwweb/sys/ValidateCode.aspx?t=812";
                    URL url = new URL(address);
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("Referer", "http://121.248.70.120/jwweb/ZNPK/TeacherKBFB.aspx");
                    connection.setDoInput(true);
                    connection.setConnectTimeout(5000);
                    connection.connect();
                    String cookie = connection.getHeaderField("Set-Cookie");

                    cookie = cookie.split(";")[0];
                    SharedPreferences myconf=getSharedPreferences("mycookie", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=myconf.edit();
                    editor.putString("cookie",cookie);
                    editor.commit();

                    Log.i("bear",cookie+"cookie from validate82");
//                conf.setProperty("cookie", cookie);
//                conf.store(new FileOutputStream("conf.properties"), "comments");

                    byte[] buf = new byte[512];
                    InputStream inputStream = connection.getInputStream();
                    OutputStream outputStream = new FileOutputStream("/sdcard/k.png");
                    int len = 0;
                    while ((len = inputStream.read(buf)) != -1)

                    {
                        outputStream.write(buf, 0, len);
                        System.out.println(len);
                    }
                    inputStream.close();
                    outputStream.close();
                    handler2.sendEmptyMessage(1);
                    System.out.println("ok");
                    Log.i("bear",cookie+"cookie from validate");
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                identifyPicture= BitmapFactory.decodeFile("/sdcard/k.png");
                Message codeMessage = new Message();
                codeMessage.what = UPDATE_PIC;
                handler2.sendMessage(codeMessage);

            }}.start();

    }



//    public  void getScheduleByTea(View view)throws Exception{
//        new Thread(){
//            public void run(){
//                try{
//                    String yzm=securityCode.getText().toString();
//                    Log.i("bear",yzm+"->yzm");
//
//                    String cookie="";
//                    SharedPreferences sharedPreferences=getSharedPreferences("mycookie",Context.MODE_PRIVATE);
//                    cookie=sharedPreferences.getString("cookie","wrong!");
//                    Log.i("bear",cookie+"cookie"+"real using cookie");
//                    System.out.println(cookie);
//                    String referer="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB.aspx";
//                    String address="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB_rpt.aspx";
//                    URL url=new URL(address);
//                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
//                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
//                    connection.setRequestMethod("POST");
//                    connection.setRequestProperty("Cookie", cookie);
//                    connection.setRequestProperty("Referer", referer);
//                    StringBuilder builder=new StringBuilder();
//                    builder.append("Sel_XNXQ=20180&Sel_JS=0000315&type=1&txt_yzm="+yzm);
//                    OutputStream outputStream=connection.getOutputStream();
//                    outputStream.write(builder.toString().getBytes());
//                    int len=connection.getContentLength();
//                    System.out.println(len);
//                    byte[]buf=new byte[512];
//                    File file=new File("/sdcard/aa1.txt");
//
//                    InputStream inputStream=connection.getInputStream();
//                    OutputStream outputStream2=new FileOutputStream(file);
//                    while((len=inputStream.read(buf))!=-1)
//                    {
//                        outputStream2.write(buf, 0, len);
//
//                    }
//                    inputStream.close();
//                    outputStream2.close();
//                    handler.sendEmptyMessage(1);
//
//
//                   rowsList = data.parseHtml();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        }.start();}
}



