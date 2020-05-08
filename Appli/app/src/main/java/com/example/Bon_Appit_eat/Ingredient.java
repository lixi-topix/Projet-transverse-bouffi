package com.example.Bon_Appit_eat;

public class Ingredient {

    String Name;
    String Type;

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

}
