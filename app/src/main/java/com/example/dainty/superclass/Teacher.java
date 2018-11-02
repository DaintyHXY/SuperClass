package com.example.dainty.superclass;

public class Teacher {

    private int teacherId;
    private String tName;
    private String tSex;

    public Teacher() {
        super();
    }
    public Teacher(String name ,String sex){
        this.tName = name ;
        this.tSex = sex;
    }

    public int gettId() {
        return teacherId;
    }

    public void settId(int tId) {
        this.teacherId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettSex() {
        return tSex;
    }

    public void settSex(String tSex) {
        this.tSex = tSex;
    }
}
