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
}
