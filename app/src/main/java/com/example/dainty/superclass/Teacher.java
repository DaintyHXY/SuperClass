package com.example.dainty.superclass;

public class Teacher {

    private String teacherName;
    private String teacherRealName;
    private int isInDatabase=0;

    public Teacher(String teacherName, String teacherRealName) {
        this.teacherName = teacherName;
        this.teacherRealName = teacherRealName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherRealName() {
        return teacherRealName;
    }

    public void setTeacherRealName(String teacherRealName) {
        this.teacherRealName = teacherRealName;
    }

    public int getIsInDatabase() {
        return isInDatabase;
    }

    public void setIsInDatabase(int isInDatabase) {
        this.isInDatabase = isInDatabase;
    }
}
