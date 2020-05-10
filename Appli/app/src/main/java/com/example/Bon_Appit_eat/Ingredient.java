package com.example.Bon_Appit_eat;

import androidx.annotation.NonNull;

public class Ingredient {

    private String Name;
    private String Type;

    Ingredient(){

    }
    public Ingredient(String name, String type) {
        this.Name = name;
        this.Type = type;
    }

    public String getName() { return Name; }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return Name + " " + Type;
    }
}
