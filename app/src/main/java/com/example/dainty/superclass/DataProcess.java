package com.example.dainty.superclass;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

public interface DataProcess {

    public Bitmap getCookieAndValidateCode(String teacherName,String semester);

    public File getScheduleByte();

    public List<List<String>> parseHtml();

}
