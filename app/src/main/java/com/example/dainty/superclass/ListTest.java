package com.example.dainty.superclass;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListTest {
    public static final List<Teacher> teacherList = new ArrayList<Teacher>();
    private static Properties conf = new Properties();

    public static void parseTeacherList() {
        File input = new File("/sdcard/teacher.txt");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            Elements els = doc.getElementsByTag("option");
            for (Element el : els) {
                String text = el.text();
                String id = el.attr("value");
//			System.out.println(text);
                teacherList.add(new Teacher(id, text));
            }
        }

//        public static void main (String[]args) throws Exception {
//
//            parseTeacherList();
////		System.out.println(teacherList.toString());
//        }
    }
}

