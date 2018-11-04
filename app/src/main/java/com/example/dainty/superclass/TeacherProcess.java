package com.example.dainty.superclass;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TeacherProcess {

    public static final List<Teacher> teacherList= new ArrayList<Teacher>();
    private static Properties conf = new Properties();

    public static List<Teacher> parseTeacherList() {
        File input = new File("/sdcard/teacher.txt");
        Document doc = null;
        try {
            doc = Jsoup.parse(input,"UTF-8","http://example.com/");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements els =  doc.getElementsByTag("option");
        for(Element el :els) {
            String text = el.text();
            String id = el.attr("value");
//			System.out.println(text);
            Teacher t = new Teacher(id,text);
            t.setIsInDatabase(0);
            teacherList.add(t);

        }

        return teacherList;
    }

}
