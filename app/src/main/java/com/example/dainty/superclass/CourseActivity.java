package com.example.dainty.superclass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends Activity {

    private String teacherName;
    private String semesterTime;

    private static final int GETLIST = 70;

    private Handler handler = new Handler(){

        public void handleMessage(Message msg){

            switch (msg.what){
                case GETLIST:
                    Bundle bundle =msg.getData();
                    ArrayList list2 = new ArrayList();
                    list2 = bundle.getParcelableArrayList("list");
                    //rowsList = (List<List<String>>) list2.get(0);


                    Log.d("info","geiLIST"+rowsList.size());



                    line1Column1.setText(rowsList.get(0).get(0));
                    line1Column2.setText(rowsList.get(0).get(1));
                    line1Column3.setText(rowsList.get(0).get(2));
                    line1Column4.setText(rowsList.get(0).get(3));
                    line1Column5.setText(rowsList.get(0).get(4));
                    line1Column6.setText(rowsList.get(0).get(5));
                    line1Column7.setText(rowsList.get(0).get(6));

                    line2Column1.setText(rowsList.get(1).get(0));
                    line2Column2.setText(rowsList.get(1).get(1));
                    line2Column3.setText(rowsList.get(1).get(2));
                    line2Column4.setText(rowsList.get(1).get(3));
                    line2Column5.setText(rowsList.get(1).get(4));
                    line2Column6.setText(rowsList.get(1).get(5));
                    line2Column7.setText(rowsList.get(1).get(6));

                    line3Column1.setText(rowsList.get(2).get(0));
                    line3Column2.setText(rowsList.get(2).get(1));
                    line3Column3.setText(rowsList.get(2).get(2));
                    line3Column4.setText(rowsList.get(2).get(3));
                    line3Column5.setText(rowsList.get(2).get(4));
                    line3Column6.setText(rowsList.get(2).get(5));
                    line3Column7.setText(rowsList.get(2).get(6));


                    line4Column1.setText(rowsList.get(3).get(0));
                    line4Column2.setText(rowsList.get(3).get(1));
                    line4Column3.setText(rowsList.get(3).get(2));
                    line4Column4.setText(rowsList.get(3).get(3));
                    line4Column5.setText(rowsList.get(3).get(4));
                    line4Column6.setText(rowsList.get(3).get(5));
                    line4Column7.setText(rowsList.get(3).get(6));

                    line5Column1.setText(rowsList.get(4).get(0));
                    line5Column2.setText(rowsList.get(4).get(1));
                    line5Column3.setText(rowsList.get(4).get(2));
                    line5Column4.setText(rowsList.get(4).get(3));
                    line5Column5.setText(rowsList.get(4).get(4));
                    line5Column6.setText(rowsList.get(4).get(5));
                    line5Column7.setText(rowsList.get(4).get(6));

                    break;

            }

        }
    };

    private List<List<String>> rowsList = new ArrayList<List<String>>();
    private Data data = new Data();

    private String security;

    private TextView line1Column1;
    private TextView line1Column2;
    private TextView line1Column3;
    private TextView line1Column4;
    private TextView line1Column5;
    private TextView line1Column6;
    private TextView line1Column7;

    private TextView line2Column1;
    private TextView line2Column2;
    private TextView line2Column3;
    private TextView line2Column4;
    private TextView line2Column5;
    private TextView line2Column6;
    private TextView line2Column7;

    private TextView line3Column1;
    private TextView line3Column2;
    private TextView line3Column3;
    private TextView line3Column4;
    private TextView line3Column5;
    private TextView line3Column6;
    private TextView line3Column7;

    private TextView line4Column1;
    private TextView line4Column2;
    private TextView line4Column3;
    private TextView line4Column4;
    private TextView line4Column5;
    private TextView line4Column6;
    private TextView line4Column7;

    private TextView line5Column1;
    private TextView line5Column2;
    private TextView line5Column3;
    private TextView line5Column4;
    private TextView line5Column5;
    private TextView line5Column6;
    private TextView line5Column7;

    private TextView line6Column1;
    private TextView line6Column2;
    private TextView line6Column3;
    private TextView line6Column4;
    private TextView line6Column5;
    private TextView line6Column6;
    private TextView line6Column7;


    private TextView[][] lineColumn =new TextView[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        PermissionUtils.verifyStoragePermissions(this);

        try {
            getScheduleByTea();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        security = intent.getStringExtra("yzm");
        Log.d("info","Course get security"+security);


            line1Column1 = (TextView) findViewById(R.id.line1Column1);
            line1Column2 = (TextView) findViewById(R.id.line1Column2);
            line1Column3 = (TextView) findViewById(R.id.line1Column3);
            line1Column4 = (TextView) findViewById(R.id.line1Column4);
            line1Column5 = (TextView) findViewById(R.id.line1Column5);
            line1Column6 = (TextView) findViewById(R.id.line1Column6);
            line1Column7 = (TextView) findViewById(R.id.line1Column7);


            line2Column1 = (TextView) findViewById(R.id.line2Column1);
            line2Column2 = (TextView) findViewById(R.id.line2Column2);
            line2Column3 = (TextView) findViewById(R.id.line2Column3);
            line2Column4 = (TextView) findViewById(R.id.line2Column4);
            line2Column5 = (TextView) findViewById(R.id.line2Column5);
            line2Column6 = (TextView) findViewById(R.id.line2Column6);
            line2Column7 = (TextView) findViewById(R.id.line2Column7);

            line3Column1 = (TextView) findViewById(R.id.line3Column1);
            line3Column2 = (TextView) findViewById(R.id.line3Column2);
            line3Column3 = (TextView) findViewById(R.id.line3Column3);
            line3Column4 = (TextView) findViewById(R.id.line3Column4);
            line3Column5 = (TextView) findViewById(R.id.line3Column5);
            line3Column6 = (TextView) findViewById(R.id.line3Column6);
            line3Column7 = (TextView) findViewById(R.id.line3Column7);

            line4Column1 = (TextView) findViewById(R.id.line4Column1);
            line4Column2 = (TextView) findViewById(R.id.line4Column2);
            line4Column3 = (TextView) findViewById(R.id.line4Column3);
            line4Column4 = (TextView) findViewById(R.id.line4Column4);
            line4Column5 = (TextView) findViewById(R.id.line4Column5);
            line4Column6 = (TextView) findViewById(R.id.line4Column6);
            line4Column7 = (TextView) findViewById(R.id.line4Column7);

            line5Column1 = (TextView) findViewById(R.id.line5Column1);
            line5Column2 = (TextView) findViewById(R.id.line5Column2);
            line5Column3 = (TextView) findViewById(R.id.line5Column3);
            line5Column4 = (TextView) findViewById(R.id.line5Column4);
            line5Column5 = (TextView) findViewById(R.id.line5Column5);
            line5Column6 = (TextView) findViewById(R.id.line5Column6);
            line5Column7 = (TextView) findViewById(R.id.line5Column7);

            line6Column1 = (TextView) findViewById(R.id.line6Column1);
            line6Column2 = (TextView) findViewById(R.id.line6Column2);
            line6Column3 = (TextView) findViewById(R.id.line6Column3);
            line6Column4 = (TextView) findViewById(R.id.line6Column4);
            line6Column5 = (TextView) findViewById(R.id.line6Column5);
            line6Column6 = (TextView) findViewById(R.id.line6Column6);
            line6Column7 = (TextView) findViewById(R.id.line6Column7);






    }

    public  void getScheduleByTea()throws Exception{
        new Thread(){
            public void run(){
                try{
                    String yzm=security;
                    Log.i("bear",yzm+"->yzm");

                    String cookie="";
                    SharedPreferences sharedPreferences=getSharedPreferences("mycookie", Context.MODE_PRIVATE);
                    cookie=sharedPreferences.getString("cookie","wrong!");
                    Log.i("bear",cookie+"cookie"+"real using cookie");
                    System.out.println(cookie);
                    String referer="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB.aspx";
                    String address="http://121.248.70.120/jwweb/ZNPK/TeacherKBFB_rpt.aspx";
                    URL url=new URL(address);
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Cookie", cookie);
                    connection.setRequestProperty("Referer", referer);
                    StringBuilder builder=new StringBuilder();
                    builder.append("Sel_XNXQ=20180&Sel_JS=0000315&type=1&txt_yzm="+yzm);
                    OutputStream outputStream=connection.getOutputStream();
                    outputStream.write(builder.toString().getBytes());
                    int len=connection.getContentLength();
                    System.out.println(len);
                    byte[]buf=new byte[512];
                    File file=new File("/sdcard/aa1.txt");

                    InputStream inputStream=connection.getInputStream();
                    OutputStream outputStream2=new FileOutputStream(file);
                    while((len=inputStream.read(buf))!=-1)
                    {
                        outputStream2.write(buf, 0, len);

                    }
                    inputStream.close();
                    outputStream2.close();
                    //handler.sendEmptyMessage(1);


                    rowsList = data.parseHtml();
                    Log.d("info","size"+rowsList.size());
                    Message message = new Message();
                    message.what = GETLIST;
                    ArrayList list = new ArrayList();
                    list.add(rowsList);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("list",list);
                    handler.sendMessage(message);


                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();}



}
