package com.example.dainty.superclass;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/11/3.
 */

public class TeacherAdapter extends ArrayAdapter {

    private List<Teacher> data;
    private int resource;

    public TeacherAdapter(Context context, int resource, List<Teacher> data) {
        super(context, resource, data);
        this.data=data;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater
                .from(getContext())
                .inflate(R.layout.teacher_item,parent,false);
//        View view= LayoutInflater
//                .from(getContext())
//                .inflate(resource,null);

        Teacher t= data.get(position);
//        TextView teacherName=view.findViewById(R.id.teacherName);
        TextView teacherRealName=view.findViewById(R.id.teacherRealName);
//        teacherName.setText(t.getTeacherName());
        teacherRealName.setText(t.getTeacherRealName());
        if (convertView != null) {
            if (t.getIsInDatabase()==0)
                convertView.setBackgroundColor(Color.rgb(200, 200, 200));
            else
                convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater
                .from(getContext())
                .inflate(R.layout.teacher_item,parent,false);
//        View view= LayoutInflater
//                .from(getContext())
//                .inflate(resource,null);

        Teacher t= data.get(position);
//        TextView teacherName=view.findViewById(R.id.teacherName);
        TextView teacherRealName=view.findViewById(R.id.teacherRealName);
//        teacherName.setText(t.getTeacherName());
        teacherRealName.setText(t.getTeacherRealName());
        if (convertView != null) {
            if (t.getIsInDatabase()==0)
                convertView.setBackgroundColor(Color.rgb(200, 200, 200));
            else
                convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return view;
    }
}
