package com.example.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadWrite {
    public static void addUser(String username, String email, String bNumber, String password, boolean isProf) throws IOException {
        // Creates sets userList.xml to the File userListFile and creates userList.xml if it doesn't exist
        File userListFile = new File("userList.xml");

        boolean fileCreated = userListFile.createNewFile();

        if (fileCreated) {
            FileWriter fw = new FileWriter(userListFile);
            System.out.println("File \"" + userListFile.getName() + "\" created.\n");
            fw.write("<userList>\n</userList>");
            fw.close();
        } else {
            System.out.println("File already exists.\n");
        }

        String userList = new String(Files.readAllBytes(Paths.get(userListFile.getPath())), StandardCharsets.UTF_8);
        System.out.println(userList + "uList printed #3");

        // truncates file!
        FileWriter fw = new FileWriter(userListFile);

        String newUser =
                "    <user>\n" +
                "        <userName>" + username + "</userName>\n" +
                "        <email>" + email + "</email>\n" +
                "        <bNumber>" + bNumber + "</bNumber>\n" +
                "        <password>" + password + "</password>\n" +
                "        <isProfessor>" + isProf + "</isProfessor>\n" +
                "    </user>";


        userList = userList.substring(0, userList.length() - 11) + newUser + "\n" + userList.substring(userList.length() - 11);
        System.out.println(userList);

        fw.write(userList);
        fw.close();
    }

    public static void addCourse(String courseName, String CRN) throws IOException {
        // Creates sets courseList.xml to the File courseListFile and creates courseList.xml if it doesn't exist
        // ending tag so we can subtract correct value later
        String endingTag = "</courseList>";
        File courseListFile = new File("courseList.xml");

        boolean fileCreated = courseListFile.createNewFile();

        if (fileCreated) {
            FileWriter fw = new FileWriter(courseListFile);
            System.out.println("File \"" + courseListFile.getName() + "\" created.\n");
            fw.write("<courseList>\n" + endingTag);
            fw.close();
        } else {
            System.out.println("File already exists.\n");
        }

        String courseList = new String(Files.readAllBytes(Paths.get(courseListFile.getPath())), StandardCharsets.UTF_8);
        System.out.println(courseList + "cList printed #3");

        // truncates file!
        FileWriter fw = new FileWriter(courseListFile);

        String newCourse =
                "    <course>\n" +
                        "        <courseName>" + courseName + "</courseName>\n" +
                        "        <CRN>" + CRN + "</CRN>\n" +
                        "    </course>";


        courseList = courseList.substring(0, courseList.length() - endingTag.length()) + newCourse + "\n" + courseList.substring(courseList.length() - endingTag.length());
        System.out.println(courseList);

        fw.write(courseList);
        fw.close();
    }

    public static void addUserCourse(String bNumber, String CRN) throws IOException {
        // Creates sets courseList.xml to the File courseListFile and creates courseList.xml if it doesn't exist
        // ending tag so we can subtract correct value later
        String endingTag = "</userCourse>";
        File userCourseFile = new File("userCourse.xml");

        boolean fileCreated = userCourseFile.createNewFile();

        if (fileCreated) {
            FileWriter fw = new FileWriter(userCourseFile);
            System.out.println("File \"" + userCourseFile.getName() + "\" created.\n");
            fw.write("<userCourse>\n" + endingTag);
            fw.close();
        } else {
            System.out.println("File already exists.\n");
        }

        String userCourse = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);
        System.out.println(userCourse + "cList printed #3");

        // truncates file!
        FileWriter fw = new FileWriter(userCourseFile);

        String newUserCourse =
                "    <userCourse>\n" +
                        "        <courseName>" + bNumber + "</courseName>\n" +
                        "        <CRN>" + CRN + "</CRN>\n" +
                        "    </userCourse>";


        userCourse = userCourse.substring(0, userCourse.length() - endingTag.length()) + newUserCourse + "\n" + userCourse.substring(userCourse.length() - endingTag.length());
        System.out.println(userCourse);

        fw.write(userCourse);
        fw.close();
    }

    
}
