package com.example.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadWrite {
    // Creates an entry in the user table "userList.xml"
    public static void addUser(String username, String email, String bNumber, String password, boolean isProf) throws IOException {
        // Creates sets userList.xml to the File userListFile and creates userList.xml if it doesn't exist
        File userListFile = new File("userList.xml");

        boolean fileCreated = userListFile.createNewFile();

        if (fileCreated) {
            FileWriter fw = new FileWriter(userListFile);
            System.out.println("File \"" + userListFile.getName() + "\" created.\n");
            fw.write("<userList>\n</userList>");
            fw.close();
        }

        String userList = new String(Files.readAllBytes(Paths.get(userListFile.getPath())), StandardCharsets.UTF_8);

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

        fw.write(userList);
        fw.close();
    }

    // Creates an entry in the course table "courseList.xml"
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
        }

        String courseList = new String(Files.readAllBytes(Paths.get(courseListFile.getPath())), StandardCharsets.UTF_8);

        // truncates file!
        FileWriter fw = new FileWriter(courseListFile);

        String newCourse =
                "    <course>\n" +
                        "        <courseName>" + courseName + "</courseName>\n" +
                        "        <CRN>" + CRN + "</CRN>\n" +
                        "    </course>";


        courseList = courseList.substring(0, courseList.length() - endingTag.length()) + newCourse + "\n" + courseList.substring(courseList.length() - endingTag.length());

        fw.write(courseList);
        fw.close();
    }

    // Creates an entry in the user-course junction table "UserCourse.xml"
    public static void addUserCourse(String bNumber, String CRN) throws IOException {
        // Creates sets courseList.xml to the File courseListFile and creates courseList.xml if it doesn't exist
        String endingTag = "</userCourseList>";
        File userCourseFile = new File("userCourse.xml");

        boolean fileCreated = userCourseFile.createNewFile();

        if (fileCreated) {
            FileWriter fw = new FileWriter(userCourseFile);
            System.out.println("File \"" + userCourseFile.getName() + "\" created.\n");
            fw.write("<userCourseList>\n" + endingTag);
            fw.close();
        }

        String userCourse = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);

        // truncates file!
        FileWriter fw = new FileWriter(userCourseFile);

        // If element tag "UserCourse" is changed, change it in getUserCourseElement as well
        String newUserCourse =
                "    <UserCourse>\n" +
                        "        <bNumber>" + bNumber + "</bNumber>\n" +
                        "        <CRN>" + CRN + "</CRN>\n" +
                        "    </UserCourse>";


        userCourse = userCourse.substring(0, userCourse.length() - endingTag.length()) + newUserCourse + "\n" + userCourse.substring(userCourse.length() - endingTag.length());

        fw.write(userCourse);
        fw.close();
    }








    // Returns a list of every class a user has, searching by B#
    public static ArrayList<UserCourse> getUserClasses(String bNumber) throws IOException {
        File userCourseFile = new File("UserCourse.xml");
        String userCourse = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);

        System.out.println("UserCourse string list: " + userCourse);
        System.out.println("List length: " + userCourse.length());

        ArrayList<Integer> bNumberLocationList = locateStrings(userCourse,bNumber);

        System.out.println("bNumberLocationList: " + bNumberLocationList);

        ArrayList<UserCourse> userCourseList = new ArrayList<UserCourse>();
        for (int i = 0; i < bNumberLocationList.size(); i++) {
            userCourseList.add(ReadWrite.getUserCourseElement(bNumberLocationList.get(i)));
        }
        return userCourseList;
    }

/*
    // Returns a list of every user in a class, searching by CRN
    public static ArrayList<UserCourse> getClassUsers(String CRN) throws IOException {
        File userCourseFile = new File("UserCourse.xml");
        String userCourse = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);

        ArrayList<Integer> crnLocationList = locateStrings(userCourse,CRN);

        ArrayList<UserCourse> userCourseList = new ArrayList<UserCourse>();
        for (int i = 0; i < crnLocationList.size(); i++) {
            userCourseList.add(ReadWrite.getUserCourseElement(crnLocationList.get(i)));
        }
        return userCourseList;
    }
*/
    // Finds all instances of the specified "locateString" within the "searchString" and returns their indexes
    // In my use "searchString" should be my entire XML file, and locateString should be the XML element content I want to find
    // Should be private!!!!!!!!!!!!
    private static ArrayList<Integer> locateStrings(String searchString, String locateString) {
        int currentIndex = 0;
        ArrayList<Integer> indexList = new ArrayList<Integer>();

        while (currentIndex != -1) {
            currentIndex = searchString.indexOf(locateString, currentIndex);
            if (currentIndex != -1) {
                indexList.add(currentIndex);
                currentIndex++;
            }
        }

        if (indexList.size() != 0) {
            return indexList;
        }
        else {
            System.out.println("found no instances of the specified string in the search string");
            return null;
        }
    }

    // Should be passed the index where the UserCourse start tag begins, <userCourseList> will parse the xml element based off of this
    // If not passed this index, the code will not function as planned
    // Functionality could definitely be turned more abstract; Perhaps one method to get users, courses, and userCourses.
    private static UserCourse getUserCourseElement(Integer index) throws IOException {
        String endingTag = "</UserCourse>";

        File userCourseFile = new File("UserCourse.xml");
        String userCourseList = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);

        // Gets userCourseElement
        Integer indexEnd = userCourseList.indexOf(endingTag, index);

        System.out.println("indexEnd: " + indexEnd);

        // endingTag.length - 1 = startTag.length
        String userCourseElement = null;
        if (indexEnd != -1) {
            userCourseElement = userCourseList.substring(index - 9, indexEnd); //+ (endingTag.length() - 1)
        } else {
            return null;
        }

        System.out.println(userCourseElement);

        int indexOfStartTag = userCourseElement.indexOf("<bNumber>");

        System.out.println("indexofstarttag: " + indexOfStartTag);

        if (indexOfStartTag == -1) {
            return null;
        }
        int indexOfEndTag =  userCourseElement.indexOf("</bNumber>");
        String bNumber = userCourseElement.substring(indexOfStartTag + 9, indexOfEndTag);

        System.out.println("bNumber: " + bNumber);

        indexOfStartTag = userCourseElement.indexOf("<CRN>");
        indexOfEndTag = userCourseElement.indexOf("</CRN>");
        String CRN = userCourseElement.substring(indexOfStartTag + 5, indexOfEndTag);

        System.out.println("crn: " + CRN);

        return new UserCourse(bNumber,CRN);
    }
}

/*
        ReadWrite.addCourse("Business Ethics", "000112");
        ReadWrite.addCourse("Programming 2", "927461");
        ReadWrite.addUser("tyler", "tmckenna@my.okcu.edu", "00116151", "tyler100", false);
        ReadWrite.addUser("jeff", "jmaxwell@okcu.edu", "22003242", "jeff100", true);
        ReadWrite.addUserCourse("22003242", "000112");
        ReadWrite.addUserCourse("22003242", "927461");
        ReadWrite.addUserCourse("00116151", "000112");



        System.out.println(ReadWrite.getUserClasses("22003242"));
*/