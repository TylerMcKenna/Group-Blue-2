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
                        "        <bNumber>" + bNumber + "</bNumber>\n" +
                        "        <userName>" + username + "</userName>\n" +
                        "        <email>" + email + "</email>\n" +
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
                        "        <CRN>" + CRN + "</CRN>\n" +
                        "        <courseName>" + courseName + "</courseName>\n" +
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
    public static ArrayList<Course> getUserClasses(String bNumber) throws IOException {
        // userCourse file
        File userCourseFile = new File("UserCourse.xml");
        String userCourse = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);
        ArrayList<Course> courseList = new ArrayList<Course>();

        // gets location of crn in list
        ArrayList<Integer> bNumLocationList = locateStrings(userCourse, bNumber);

        if (bNumLocationList != null && bNumLocationList.size() > 0) {
            // gets usercourse element of classes found
            ArrayList<UserCourse> userCourseList = new ArrayList<UserCourse>();
            for (int i = 0; i < bNumLocationList.size(); i++) {
                // Subtracting 31 takes us to the start tag of the whole UserCourse element, which is what getUserCourseElement demands
                System.out.println(ReadWrite.getUserCourseElement(bNumLocationList.get(i) - 31).toString());
                userCourseList.add(ReadWrite.getUserCourseElement(bNumLocationList.get(i) - 31));
            }

            // userFile
            File courseFile = new File("courseList.xml");
            String users = new String(Files.readAllBytes(Paths.get(courseFile.getPath())), StandardCharsets.UTF_8);
            courseList = new ArrayList<Course>();

            //Assumes xml is always in a certain order!
            for (int i = 0; i < userCourseList.size(); i++) {
                // index of current users bNum
                int indexOfCourse = users.indexOf(userCourseList.get(i).getCRN());

                String CRN = userCourseList.get(i).getCRN();
                String courseName = getValBetweenTags("<courseName>", "</courseName>", users, indexOfCourse);

                courseList.add(new Course(courseName, CRN));
                System.out.println(courseList.get(i));
            }
        }
        return courseList;
    }


    // Returns a list of every user in a class, searching by CRN
    public static ArrayList<User> getClassUsers(String CRN) throws IOException {
        // userCourse file
        File userCourseFile = new File("UserCourse.xml");
        String userCourse = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);
        ArrayList<User> userList =  new ArrayList<User>();;

        // gets location of crn in list
        ArrayList<Integer> crnLocationList = locateStrings(userCourse, CRN);

        if (crnLocationList != null && crnLocationList.size() > 0) {
            // gets usercourse element of classes found
            ArrayList<UserCourse> userCourseList = new ArrayList<UserCourse>();
            for (int i = 0; i < crnLocationList.size(); i++) {
                // Subtracting 57 takes us to the start tag of the whole UserCourse element, which is what getUserCourseElement demands
                userCourseList.add(ReadWrite.getUserCourseElement(crnLocationList.get(i) - 57));
            }

            // userFile
            File userFile = new File("userList.xml");
            String users = new String(Files.readAllBytes(Paths.get(userFile.getPath())), StandardCharsets.UTF_8);
            userList = new ArrayList<User>();

            //Assumes xml is always in a certain order!
            for (int i = 0; i < userCourseList.size(); i++) {
                // index of current users bNum
                int indexOfUser = users.indexOf(userCourseList.get(i).getbNumber());

                String bNumber = userCourseList.get(i).getbNumber();
                String username = getValBetweenTags("<userName>", "</userName>", users, indexOfUser);
                String email = getValBetweenTags("<email>", "</email>", users, indexOfUser);
                String password = getValBetweenTags("<password>", "</password>", users, indexOfUser);
                boolean isProfessor = Boolean.getBoolean(getValBetweenTags("<isProfessor>", "</isProfessor>", users, indexOfUser));

                userList.add(new User(bNumber, username, password, email, isProfessor));
            }
        }
        return userList;
    }


    // Finds all instances of the specified "locateString" within the "searchString" and returns their indexes
    // In my use "searchString" should be my entire XML file, and locateString should be the XML element content I want to find
    // W O R K S
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
        } else {
            System.out.println("found no instances of the specified string in the search string");
            return null;
        }
    }

    // Should be passed the index where the UserCourse start tag begins, <userCourseList> will parse the xml element based off of this

    // W O R K S assuming actually passed correct index (where the UserCourse start tag begins)

    // Functionality could definitely be turned more abstract; Perhaps one method to get users, courses, and userCourses.
    private static UserCourse getUserCourseElement(Integer index) throws IOException {
        String endingTag = "</UserCourse>";

        File userCourseFile = new File("UserCourse.xml");
        String userCourseList = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);

        // Gets userCourseElement
        Integer indexEnd = userCourseList.indexOf(endingTag, index);


        // endingTag.length - 1 = startTag.length
        String userCourseElement = null;
        if (indexEnd != -1) {
            userCourseElement = userCourseList.substring(index - 9, indexEnd); //+ (endingTag.length() - 1)
        } else {
            return null;
        }

        int indexOfStartTag = userCourseElement.indexOf("<bNumber>");

        if (indexOfStartTag == -1) {
            return null;
        }
        int indexOfEndTag = userCourseElement.indexOf("</bNumber>");
        String bNumber = userCourseElement.substring(indexOfStartTag + 9, indexOfEndTag);

        indexOfStartTag = userCourseElement.indexOf("<CRN>");
        indexOfEndTag = userCourseElement.indexOf("</CRN>");
        String CRN = userCourseElement.substring(indexOfStartTag + 5, indexOfEndTag);

        return new UserCourse(bNumber, CRN);
    }

    public static String getValBetweenTags(String tag, String endTag, String fileToLocateVal, int fromIndex) {
        int indexOfTag = fileToLocateVal.indexOf(tag, fromIndex);;
        int indexOfEndTag = fileToLocateVal.indexOf(endTag, fromIndex);

        if (indexOfTag == -1 || indexOfEndTag == -1) {
            System.out.println("could not find value between tags \"" + tag + " and " + endTag + "\"");
            System.exit(1);
        }

        return fileToLocateVal.substring(indexOfTag + tag.length(), indexOfEndTag);
    }

    // Returns user if login successful, returns null otherwise
    public static User checkLogin(String email, String password) throws IOException {
        File userFile = new File("userList.xml");
        String users = new String(Files.readAllBytes(Paths.get(userFile.getPath())), StandardCharsets.UTF_8);
        ArrayList<User> userList = new ArrayList<User>();

        int indexOfEmail = users.indexOf(email);
        if (indexOfEmail == -1) return null;
        if (!users.substring(indexOfEmail-7, indexOfEmail).equals("<email>")) return null;

        int indexOfUserStart = users.lastIndexOf("<user>", indexOfEmail);

        String bNumber = getValBetweenTags("<bNumber>","</bNumber>",users,indexOfUserStart);
        String username = getValBetweenTags("<userName>","</userName>",users,indexOfUserStart);
        String userEmail = getValBetweenTags("<email>","</email>",users,indexOfUserStart);
        String userPassword = getValBetweenTags("<password>","</password>",users,indexOfUserStart);
        boolean isProfessor = Boolean.parseBoolean(getValBetweenTags("<isProfessor>","</isProfessor>",users,indexOfUserStart));

        if (!userPassword.equals(password)) return null;

        return new User(bNumber,username,password,email,isProfessor);
    }

    // Leaves a blank line where the UserCourse was, needs to be fixed
    public static void deleteUserCourse(String bNumber, String CRN) throws IOException {
        File userCourseFile = new File("userCourse.xml");

        String userCourse = new String(Files.readAllBytes(Paths.get(userCourseFile.getPath())), StandardCharsets.UTF_8);

        // truncates file!
        FileWriter fw = new FileWriter(userCourseFile);

        // Course to be deleted
        String userCourseDelete =
                "    <UserCourse>\n" +
                        "        <bNumber>" + bNumber + "</bNumber>\n" +
                        "        <CRN>" + CRN + "</CRN>\n" +
                        "    </UserCourse>";

        // Start index of where to delete this john
        int indexOfDeleteCourse = userCourse.indexOf(userCourseDelete);

        // Appends the userCourse xml list from (0 - the new user) with the userCourse xml file from (the new user + new user length)
        userCourse = userCourse.substring(0, indexOfDeleteCourse) + userCourse.substring(indexOfDeleteCourse + userCourseDelete.length());

        fw.write(userCourse);
        fw.close();
    }
}




















// base database
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