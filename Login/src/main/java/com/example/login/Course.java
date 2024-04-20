package com.example.login;

import java.util.ArrayList;

public class Course {

    private String courseName;
    private int CRN;
    ArrayList<String> classlist = new ArrayList<>();

    Course(String courseName, int CRN){

        this.courseName = courseName;
        this.CRN = CRN;
    }

    String getCoursename(){
        return courseName;
    }

    int getCRN(){
        return CRN;
    }

    void addStudent(String name){
        classlist.add(name);
    }

}
