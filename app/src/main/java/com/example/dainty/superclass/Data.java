package com.example.dainty.superclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Data implements DataProcess {


    @Override
    public Bitmap getCookieAndValidateCode(String teacherName, String semester) {

        return null;
    }

    @Override
    public File getScheduleByte() {
        return null;
    }

    @Override
    public List<List<String>> parseHtml() {
        File file = new File("/sdcard/aa1.txt");
        String string = "";
        String encoding = "GB2312";
        Long filelength = file.length();
        byte[] filecontent = new byte[(filelength.intValue())];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(filecontent);
            inputStream.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        try {
            string = new String(filecontent, encoding);

        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println(">>>>>");
        System.out.println(string);
        Document document = Jsoup.parse(string);
        Elements table = document.getElementsByTag("table");
        System.out.println(table.size());
        Element rows = table.get(3);
        int start = 2, end = 8;
        //rows是我真正需要的那个table   共6行9列
        List<List<String>> rowsList = new ArrayList<List<String>>();
        Elements trs = rows.getElementsByTag("tr");
        //开始   5行9列添数据
        for (int i = 1; i < 6; i++) {
            if (i == 1 || i == 3) {
                start = 2;

            } else start = 1;

            List<String> aRow = new ArrayList<String>();
            //分配行,得到一行中所有的列
            Elements td = trs.get(i).getElementsByTag("td");
            end = td.size();
            for (int j = start; j < end; j++) {
                //拿到列块 添加进list
                aRow.add(td.get(j).text());

            }
            //行List添加进总List
            rowsList.add(aRow);

        }
        System.out.println("\n\n\n\n..");
        //观察list状况
        for (int i = 0; i < rowsList.size(); i++) {
            for (int j = 0; j < rowsList.get(i).size(); j++) {
                if (rowsList.get(i).get(j).equals("")) System.out.print("无课");
                else {
                    System.out.print(rowsList.get(i).get(j));
                }
                System.out.print(" ");

            }
            System.out.println("");

        }

        return rowsList;
    }


}

