package com.example.dainty.superclass;

public class Student {

    private int stuId;
    private String sName ;
    private String sPassword;

    public Student() {
        super();
    }

    public Student(String name,String password){
        this.sName = name;
        this.sPassword = password;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return sName;
    }

    public void setName(String name) {
        this.sName = name;
    }

    public String getPassword() {
        return sPassword;
    }

    public void setPassword(String password) {
        this.sPassword = password;
    }
}
