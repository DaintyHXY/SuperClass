package com.example.dainty.superclass;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
    private GridAdaptor ga;
    private String[][] contents;
    private List<String> dataList;
    private GridView gv ;
    private static final int GETLIST = 70;
    private static final int Error_Valicode = 69;

    private MyDataBaseHelper myDataBaseHelper;

    private Handler handler = new Handler(){

        public void handleMessage(Message msg){

            switch (msg.what){
                case Error_Valicode:
                    Toast.makeText(CourseActivity.this,"wrong code",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CourseActivity.this,ClassActivity.class);

                    //sem = "20180";
                    teacherName = "0000315";



                    // intent.putExtra("classTable",rowsList);

                    startActivity(intent);
//                case GETLIST:
//                    Bundle bundle =msg.getData();
//                    ArrayList list2 = new ArrayList();
//                    list2 = bundle.getParcelableArrayList("list");
//                    //rowsList = (List<List<String>>) list2.get(0);
//                    Log.d("info","geiLIST"+rowsList.size());
//
//                    break;
//
            }

        }
    };

    private List<List<String>> rowsList = new ArrayList<List<String>>();
    private Data data = new Data();

    private String security;


    private TextView[][] lineColumn =new TextView[5][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);


        PermissionUtils.verifyStoragePermissions(this);

        Intent intent = getIntent();
        security = intent.getStringExtra("yzm");
        teacherName = intent.getStringExtra("teacher");
        semesterTime = intent.getStringExtra("semester");
        Log.d("info2","Course get security"+security+"-----teacher:"+teacherName+"-----semester:"+semesterTime);

//        try {
//            getScheduleByTea();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

       // line1Column1 = (TextView) findViewById(R.id.line1Column1);
        myDataBaseHelper = new MyDataBaseHelper(this,"SuperClass.db",null,12);
        SQLiteDatabase db =  myDataBaseHelper.getWritableDatabase();

        Cursor cursor = db.query("ClassTable", null, null, null, null, null, null);

        ArrayList<ClassTable> list = new ArrayList<ClassTable>();
        List<String> forAdaptorList=new ArrayList<String>();
        int j =0;
        String namei="";
        String semesteri = "";

        ContentValues contentValues = new ContentValues();
        contentValues.put("isInDatabase","1");
        db.update("Teacher",contentValues,"teacherName=?",new String[]{teacherName});

        //首先在本地SQLite服务器里查找数据
        if (cursor.moveToFirst()) {

            do {
                namei = cursor.getString(cursor.getColumnIndex("teacherName"));
                semesteri = cursor.getString(cursor.getColumnIndex("semester"));

                if(teacherName.equals(namei)&&semesterTime.equals(semesteri)){
                   Log.d("database","get data");
                    int classLine = cursor.getInt(cursor.getColumnIndex("classLine"));
                    int classColumn = cursor.getInt(cursor.getColumnIndex("classColumn"));
                    String classTable = cursor.getString(cursor.getColumnIndex("inClass"));
                    //String teacherName = cursor.getString(cursor.getColumnIndex("teacherName"));
                    //String semester = cursor.getString(cursor.getColumnIndex("semester"));

                    ClassTable classTable1 = new ClassTable(teacherName,semesterTime);
                    classTable1.setColumn(classColumn);
                    classTable1.setLine(classLine);
                    classTable1.setInClass(classTable);
                    list.add(classTable1);

                }

                //line1Column1.setText(classTable);
                //Log.d("list","line+++"+list.get(j).getLine()+"Column--------"+list.get(j).getColumn()+"table-------"+list.get(j).getInClass());
                j++;

            } while (cursor.moveToNext());
            Log.d("course list","list size:++++++"+list.size());

        }

        //当SQLite里面没有需要的数据时，去云服务器查找需要的数据
        if(list.size()==0){}

        for(int i=0;i<42;i++){

            forAdaptorList.add(new String(" "));
        }
        //forAdaptorList.set(0,"anceasjfbhkahf");
       int adCol=0,adRow=0;
        String adString="";
        for(int i=0;i<list.size();i++){
            adRow=list.get(i).getLine();
            adCol=list.get(i).getColumn();
            adString=list.get(i).getInClass();
            forAdaptorList.set(adRow*7+adCol,adString);
            Log.d("aaa",""+forAdaptorList.get(0));

        }
        gv = (GridView) findViewById(R.id.courceDetail);

Log.i("test",forAdaptorList.size()+"");
        ga = new GridAdaptor(this,forAdaptorList);
        gv.setAdapter(ga);

       cursor.close();


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
                    builder.append("Sel_XNXQ="+semesterTime+"&Sel_JS="+teacherName+"&type=1&txt_yzm="+yzm);
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


                    rowsList = data.parseHtml();
                    //判断rowsList传来的信息是什么
                    //1.空表  那就啥也不干  直接进界面
                    //2.验证码错误  这个时候list的size==1

                    if(rowsList.size()!=0)
                    {
                        if(rowsList.get(0).get(0).equals("error_code")){
                            //todo   通知验证码错误
                           handler.sendEmptyMessage(Error_Valicode);
                        }

                        //第三种情况  真的有表
                        else{


                            for (int i = 0; i < rowsList.size(); i++) {
                                for (int j = 0; j < rowsList.get(i).size(); j++) {
                                    if (rowsList.get(i).get(j).equals("")) System.out.print("无课");
                                    else {

                                        String classtable = rowsList.get(i).get(j);
                                        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put("classLine", i);
                                        values.put("classColumn",j );
                                        values.put("teacherName","0000315");
                                        values.put("semester","20180");
                                        values.put("inClass",classtable);
                                        db.insert("ClassTable", null, values);

                                        Log.d("course",""+rowsList.get(i).get(j));
                                        System.out.print(rowsList.get(i).get(j));
                                    }
                                    System.out.print(" ");

                                }
                                System.out.println("");

                            }
                            Message message = new Message();
                            message.what = GETLIST;
                            ArrayList list = new ArrayList();
                            list.add(rowsList);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("list",list);
                            handler.sendMessage(message);
                        }

                    }
                    else {
                        Message message = new Message();
                        message.what = GETLIST;
                        ArrayList list = new ArrayList();
                        list.add(rowsList);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("list",list);
                        handler.sendMessage(message);

                    }
                    //rowlist存入数据库


//                    Log.d("info","size"+rowsList.size());



                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();}





}
