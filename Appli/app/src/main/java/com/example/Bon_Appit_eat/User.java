package com.example.Bon_Appit_eat;

public class User {
    private String email;
    private String firstName;
    private String lastName;

    public User(){

    }

    public User(String email, String firstName, String lastName){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getMail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
