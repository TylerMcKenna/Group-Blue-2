package com.example.login;

import java.util.ArrayList;

public class Course {

    private String courseName;
    private String CRN;

    Course(String courseName, String CRN){

        this.courseName = courseName;
        this.CRN = CRN;
    }

    public String getCourseName(){
        return courseName;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", CRN='" + CRN + '\'' +
                '}';
    }

    public String getCRN(){
        return CRN;
    }

}
