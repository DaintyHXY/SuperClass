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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
    public static final int AUTO_CODE=2;
    private static final int Error_Valicode = 69;

    private Data data = new Data();
    private Handler doInsertHandler;

    private Button search;
    private Spinner semester;
    private Spinner teacher;
    private String sem;
    private String teacherName="";
    private String security;
    private List<ClassTable> doInsertList=new ArrayList<ClassTable>();



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

    List<Teacher> teacherList = new ArrayList<Teacher>();

    private Handler handler2 = new Handler(){

        public void handleMessage(Message msg){

            switch(msg.what){
                case UPDATE_PIC:
                    //更新验证码
                    identifyCode.setImageBitmap(identifyPicture);
                    autoIdentity();
                    break;
                case AUTO_CODE:
                    securityCode=findViewById(R.id.securitycode);
                    securityCode.setText(msg.getData().get("autocode").toString());
            }

        }

    };
    private Handler handler3=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           if(msg.what==Error_Valicode){
               Toast.makeText(ClassActivity.this,"nidacuole",Toast.LENGTH_SHORT).show();
               getCookieAndValidateCode();
           }
           else if(msg.what==3){
               //进下一个
               //跳转到下一页面，并将学期和教师数据传输给下一个页面
                Intent intent = new Intent(ClassActivity.this,CourseActivity.class);

               //sem = "20180";
//               teacherName = "0000315";

               intent.putExtra("semester",sem);
               intent.putExtra("teacher",teacherName);
                intent.putExtra("yzm",security);

                //intent.putExtra("classTable",rowsList);

               startActivity(intent);

           }
        }
    };

    private void autoIdentity() {
        new Thread(){

            public void run(){


                String username="qq563088";
                String password="lzx1314520";
                String softid="1e4318d5bf6608c2a403f97ea97d52bc";
                String codetype="1902";
                String cj_filePath="/sdcard/k.png";
                String len_min="0";
                String use_Code="";
                String returnCode=new ChaoJiYing().PostPic(username,password,softid,codetype,len_min,cj_filePath);//解析下Json
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(returnCode);
                    use_Code=jsonObject.getString("pic_str");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("bear",use_Code+"  use ");
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("autocode",use_Code);
                msg.setData(bundle);
                msg.what=2;
                handler2.sendMessage(msg);
            }



        }.start();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        PermissionUtils.verifyStoragePermissions(this);



        myDataBaseHelper = new MyDataBaseHelper(this,"SuperClass.db",null,12);

        search = (Button)findViewById(R.id.toSearch);

        semester = (Spinner)findViewById(R.id.semester);
        teacher = (Spinner)findViewById(R.id.teacher);

        Log.i("teacher","OKK");
       //teacherList= TeacherProcess.parseTeacherList();

        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();

        //获得teacherlist
        Cursor cursor = db.query("Teacher",null,null,null,null,null,null);
        if (cursor.moveToFirst()) {

            do {

                    Log.d("database","get teacherList");
                    String fTeacherName = cursor.getString(cursor.getColumnIndex("teacherName"));
                    String fTeacherRealName = cursor.getString(cursor.getColumnIndex("teacherRealName"));
                    int isInDatabase = cursor.getInt(cursor.getColumnIndex("isInDatabase"));

                    Teacher teacher1 = new Teacher(fTeacherName,fTeacherRealName);
                    teacher1.setIsInDatabase(isInDatabase);
                    teacherList.add(teacher1);
                    if(isInDatabase==1)
                        Log.d("11111",""+teacher1.getTeacherRealName());

            } while (cursor.moveToNext());
        }

        final TeacherAdapter teacherAdapter = new TeacherAdapter(this,android.R.layout.simple_list_item_1,teacherList);
        teacher.setAdapter(teacherAdapter);
        teacherAdapter.notifyDataSetChanged();
        Log.d("teacherlist",""+teacherList.get(3).getTeacherName());

        identifyCode = (ImageView)findViewById(R.id.img_securitycode);
        identifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCookieAndValidateCode();
            }
        });

        getCookieAndValidateCode();

        securityCode = (EditText)findViewById(R.id.securitycode);

        //创建数据库

        //myDataBaseHelper.getWritableDatabase();


        //学期和教师名字选中
        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sem = (String) semester.getSelectedItem();
                sem = semesterProcess(sem);
                Log.d("info","semester:"+sem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                teacherAdapter.notifyDataSetChanged();

            }
        });

        teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacherName = teacherList.get(position).getTeacherName();
//                teacherName="";
                Log.d("info","teacher:"+teacherName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {


                 security = securityCode.getText().toString();
                                          try {

                                              //查询本机数据库，得到list如果  list不为空 进course
                                              Log.d("shunxu","local database search");
                                              List queryFromLocal_List=getFromLocal();
                                             if(queryFromLocal_List.size()>0){

                                                 Intent intent = new Intent(ClassActivity.this,CourseActivity.class);

                                                 //sem = "20180";
//               teacherName = "0000315";

                                                 intent.putExtra("semester",sem);
                                                 intent.putExtra("teacher",teacherName);
                                                 intent.putExtra("yzm",security);

                                                 //intent.putExtra("classTable",rowsList);

                                                 startActivity(intent);
                                              }
                                              else {
                                                 Log.d("shunxu","remote database search");
                                                getListFromRemote();
                                                 //如果远端有数据  存入本地库
                                               doInsertHandler=new Handler(){
                                                   @Override
                                                   public void handleMessage(Message msg) {
                                                      if(msg.what==1){
                                                          if(doInsertList.size()>0){
                                                              //把远端的数据插入数据库

                                                              doInsert(doInsertList);
                                                              handler3.sendEmptyMessage(3);
                                                          }
                                                          else {
                                                              Log.i("bear","weibi qu cha le ?");
                                                              try {
                                                                  Log.d("shunxu","in to p");
                                                                  getScheduleByTea();
                                                              } catch (Exception e) {
                                                                  e.printStackTrace();
                                                              }
                                                          }

                                                      }
                                                   }
                                               };

                                             }

                                          } catch (Exception e) {
                                              e.printStackTrace();
                                          }
                                          //跳转到下一页面，并将学期和教师数据传输给下一个页面
                                         // Intent intent = new Intent(ClassActivity.this,CourseActivity.class);

                                          //sem = "20180";
//                                          teacherName = "0000315";

                                         // intent.putExtra("semester",sem);
                                         // intent.putExtra("teacher",teacherName);
                                         // intent.putExtra("yzm",security);

                                          // intent.putExtra("classTable",rowsList);

                                          //startActivity(intent);

            }
        });



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

    //近端访问数据库
    public List<ClassTable> getFromLocal(){
  //todo   近端数据库查询
        List<ClassTable> reList = new ArrayList<ClassTable>();
        String namei="";
        String semesteri="";
        int j = 0;
        ArrayList<ClassTable> list = new ArrayList<ClassTable>();
        //首先在本地SQLite服务器里查找数据
        SQLiteDatabase db =  myDataBaseHelper.getWritableDatabase();
        Cursor cursor = db.query("ClassTable", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                namei = cursor.getString(cursor.getColumnIndex("teacherName"));
                semesteri = cursor.getString(cursor.getColumnIndex("semester"));

                if(teacherName.equals(namei)&&sem.equals(semesteri)){
                    Log.d("local search","get data");
                    int classLine = cursor.getInt(cursor.getColumnIndex("classLine"));
                    int classColumn = cursor.getInt(cursor.getColumnIndex("classColumn"));
                    String classTable = cursor.getString(cursor.getColumnIndex("inClass"));
                    //String teacherName = cursor.getString(cursor.getColumnIndex("teacherName"));
                    //String semester = cursor.getString(cursor.getColumnIndex("semester"));

                    ClassTable classTable1 = new ClassTable(teacherName,sem);
                    classTable1.setColumn(classColumn);
                    classTable1.setLine(classLine);
                    classTable1.setInClass(classTable);
                    list.add(classTable1);

                }

                //line1Column1.setText(classTable);
                //Log.d("list","line+++"+list.get(j).getLine()+"Column--------"+list.get(j).getColumn()+"table-------"+list.get(j).getInClass());
                j++;

            } while (cursor.moveToNext());

        }
        reList = list;
        Log.d("local search finish","lisr size:--------"+list.size());

        return  reList;


    }


    //从远端访问数据库
public  List getListFromRemote(){
//todo  从远端得到数据



    new Thread(){

        @Override
        public void run() {
            List<ClassTable>geter=MysqImpl.selectClassTableByTeacherName(teacherName,sem);
            Log.d("remote","teacherName----"+teacherName+"----seme"+sem);
//            Log.i("bear",geter.get(0).getSemster()+"   ->semes");
            Log.i("bear","list:---r---"+geter.size());
//             Intent intent = new Intent(ClassActivity.this,MainActivity.class);
//
//
//
//
//
//            startActivity(intent);
            doInsertList=geter;
            doInsertHandler.sendEmptyMessage(1);
            Log.i("bear","i come here");

        }
    }.start();

    return  new ArrayList();
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
                    builder.append("Sel_XNXQ="+sem+"&Sel_JS="+teacherName+"&type=1&txt_yzm="+yzm);
                    OutputStream outputStream=connection.getOutputStream();
                    outputStream.write(builder.toString().getBytes());
                    int len=connection.getContentLength();
                    System.out.println(len);
                    byte[]buf=new byte[512];
                    File file=new File("/sdcard/aa1.txt");
                    List<ClassTable> forReometeList=new ArrayList<ClassTable>();
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
                            handler3.sendEmptyMessage(Error_Valicode);
                        }

                        //第三种情况  真的有表
                        else{
                            SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();
                            //修改易查询老师的表
                            //先往近端写

                            //Cursor cursor = db.rawQuery("SELECT * from Teacher where teacherName=?",new String[]{teacherName});
//                            ContentValues contentValues = new ContentValues();
//                            contentValues.put("inInDatabase","1");
//                            db.update("Teacher",contentValues,"teacherName=?",new String[]{teacherName});

                            Log.d("databasein","start into database");
                            for (int i = 0; i < rowsList.size(); i++) {
                                for (int j = 0; j < rowsList.get(i).size(); j++) {
                                    if (rowsList.get(i).get(j).equals("")) System.out.print("无课");
                                    else {
//填入近端所需数据
                                        String classtable = rowsList.get(i).get(j);

                                        ContentValues values = new ContentValues();
                                        values.put("classLine", i);
                                        values.put("classColumn",j );
                                        values.put("teacherName",teacherName);
                                        values.put("semester",sem);
                                        values.put("inClass",classtable);
                                        db.insert("ClassTable", null, values);
//填入远端所需数据
                                        ClassTable c=new ClassTable(teacherName,sem);
                                        c.setLine(i);
                                        c.setColumn(j);
                                        c.setInClass(classtable);
                                        forReometeList.add(c);

                                        Log.d("course",""+rowsList.get(i).get(j));
                                        System.out.print(rowsList.get(i).get(j));
                                    }
                                    System.out.print(" ");

                                }
                                System.out.println("");

                            }

                            //往远端写
                            for(ClassTable c : forReometeList){
                                MysqImpl.addClassTable(c);
                            }
                           //进下一个
                            handler3.sendEmptyMessage(3);



                        }

                    }
                    else {
                        //进下一个
                        handler3.sendEmptyMessage(3);

                    }
                    //rowlist存入数据库


//                    Log.d("info","size"+rowsList.size());



                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();}

        //数据库操作  把list存入数据库
public void doInsert(List<ClassTable> fromRemoteList){

        //todo
        SQLiteDatabase db = myDataBaseHelper.getWritableDatabase();

        for (ClassTable c :fromRemoteList){

        ContentValues values = new ContentValues();
        values.put("classLine", c.getLine());
        values.put("classColumn",c.getColumn() );
        values.put("teacherName",c.getTeacherName());
        values.put("semester",c.getSemster());
        values.put("inClass",c.getInClass());
        db.insert("ClassTable", null, values);
    }
    Log.d("remote insert","remote class table insert finish");


}
    public String semesterProcess(String semester){

        String year="";

        for (int i=0;i<4;i++){
            year += semester.charAt(i);
        }

        String seme="";
        seme += semester.charAt(12);
        if(seme.equals("一"))
            seme = "0";
        else if(seme.equals("二"))
                seme = "1";
        else Log.d("semesterProcess","process error:"+seme);

        return year+seme;

    }


}



