package com.example.login;
    public class Person {
        private String name;
        private String password;
        private String email;
        private boolean isProfessor;

        public Person(String name, String password, String email, boolean isProfessor) {
            this.name = name;
            this.password = password;
            this.email = email;
            this.isProfessor = isProfessor;
        }

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isProfessor() {
            return isProfessor;
        }

        public void setProfessor(boolean professor) {
            isProfessor = professor;
        }
    }
// string B number
