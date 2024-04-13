package com.example.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadWrite {
    public static void addUser(String username, String email, int bNumber, String password, boolean isProf) throws IOException {
        // Creates sets userList.xml to the File userListFile and creates userList.xml if it doesn't exist
        File userListFile = new File("userList.txt");
        boolean fileCreated = userListFile.createNewFile();
        FileWriter fw = new FileWriter(userListFile);
        Scanner scanner = new Scanner(userListFile);

        if (fileCreated) {
            System.out.println("File \"" + userListFile.getName() + "\" created.\n");
            fw.write("<userList>\n</userList>");
        } else {
            System.out.println("File already exists.\n");
        }
/*
        scanner.useDelimiter("\\Z");
        String userList = ""; //how to read in file to string?
        // should work
        System.out.println(scanner.hasNext());
        if (scanner.hasNext()) {
            userList = scanner.next();
        } else {
           userList = "";
        }
        System.out.println(userList + "uList printed here");

        String newUser = "    <user>\n" +
                "        <userName>" + username + "</userName>\n" +
                "        <email>" + email + "</email>\n" +
                "        <bNumber>" + bNumber + "</bNumber>\n" +
                "        <password>" + password + "</password>\n" +
                "        <isProfessor>" + isProf + "</isProfessor>\n" +
                "    </user>";


        userList = userList + newUser;
        System.out.println(userList);
        /*
        fw.write(userList);
*/
        scanner.close();
       fw.close();
    }
}
