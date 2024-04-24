package com.example.login;

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
