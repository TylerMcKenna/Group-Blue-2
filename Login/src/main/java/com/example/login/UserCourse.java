package com.example.login;
/*
    Basic course class, holds course information for converting to and from xml to a Course object.

    Made by Holt.
*/
public class UserCourse {
    private String bNumber;
    private String CRN;

    public UserCourse(String bNumber, String CRN) {
        this.bNumber = bNumber;
        this.CRN = CRN;
    }

    public String getbNumber() {
        return bNumber;
    }

    public String getCRN() {
        return CRN;
    }

    @Override
    public String toString() {
        return "UserCourse{" +
                "bNumber='" + bNumber + '\'' +
                ", CRN='" + CRN + '\'' +
                '}';
    }
}
