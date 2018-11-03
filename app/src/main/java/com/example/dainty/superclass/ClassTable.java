package com.example.dainty.superclass;

public class ClassTable {
    //课表编号
    private int classTableId;
    //课表行
    private int classLine;
    //课表列
    private int classColumn;
    //课表老师
    private String teacherName;
    //课表学期
    private String semester;
    //课程内容
    private String inClass;

    public ClassTable(int classTableId) {
        this.classTableId = classTableId;
    }

    public ClassTable(String teacherName, String semster){
        this.teacherName = teacherName;
        this.semester = semster;
    }

    public int getClassTableId() {
        return classTableId;
    }

    public void setClassTableId(int classTableId) {
        classTableId = classTableId;
    }

    public int getLine() {
        return classLine;
    }

    public void setLine(int line) {
        this.classLine = line;
    }

    public int getColumn() {
        return classColumn;
    }

    public void setColumn(int column) {
        this.classColumn = column;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSemster() {
        return semester;
    }

    public void setSemster(String semster) {
        this.semester = semster;
    }

    public String getInClass() {
        return inClass;
    }

    public void setInClass(String inClass) {
        this.inClass = inClass;
    }
}
