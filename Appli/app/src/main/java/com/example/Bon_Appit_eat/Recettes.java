package com.example.Bon_Appit_eat;

import java.util.List;

public class Recettes {

    private String Name;
    private String Description;
    private List<String> Ingredient;
    private List<String> IngredientQuantity;


    public Recettes(){

    }

    public Recettes(String name, String description, List<String> ingredient, List<String> ingredientQuantity) {
        Name = name;
        Description = description;
        Ingredient = ingredient;
        IngredientQuantity = ingredientQuantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<String> getIngredient() {
        return Ingredient;
    }

    public void setIngredient(List<String> ingredient) {
        Ingredient = ingredient;
    }

    public List<String> getIngredientQuantity() {
        return IngredientQuantity;
    }

    public void setIngredientQuantity(List<String> ingredientQuantity) {
        IngredientQuantity = ingredientQuantity;
    }
}
