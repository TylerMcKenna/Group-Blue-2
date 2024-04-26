package com.example.login;
/*
    Basic course class, holds course information for converting to and from xml to a Course object.

    Made by Holt.
*/
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

    public void setCourseName(String newName) {
        courseName = newName;
    }
    public void setCRN(String newNum) {
        CRN = newNum;
    }

}
