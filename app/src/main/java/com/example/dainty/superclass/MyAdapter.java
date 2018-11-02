//package com.example.dainty.superclass;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class MyAdapter extends ArrayAdapter {
//
//    private List<Teacher> data;
//    public MyAdapter(Context context, int resource, List<Teacher> data) {
//        super(context, resource, data);
//        this.data=data;
//    }
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view= LayoutInflater
//                .from(getContext())
//                .inflate(R.layout.teacher_layout,parent,false);
//
//        Teacher teacher= data.get(position);
////        Log.i("john",teacher.getClass().getName());
//
//        TextView name=view.findViewById(R.id.name);
////        TextView id=view.findViewById(R.id.id);
//        name.setText(teacher.getName());
//
//        if (convertView != null) {
//            if (Hundler.isDataReady(teacher.getName()))
//                convertView.setBackgroundColor(Color.rgb(200, 200, 200));
//            else
//                convertView.setBackgroundColor(Color.TRANSPARENT);
//        }
////        id.setText(teacher.getTeacherId());
//        return  view;
//    }
//
//}
