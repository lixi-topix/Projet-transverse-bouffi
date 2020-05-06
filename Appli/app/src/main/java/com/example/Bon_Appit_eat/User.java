package com.example.Bon_Appit_eat;

public class User {
    private String mail;
    private String firstName;
    private String lastName;

    public User(){

    }

    public User(String mail, String firstName, String lastName){
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
