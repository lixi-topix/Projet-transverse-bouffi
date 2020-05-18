package com.example.Bon_Appit_eat;

import java.util.List;

public class Recipe {

    private String Name;
    private String Description;
    private String Url;
    private List<String> Ingredient;
    private List<String> IngredientQuantity;


    public Recipe() {

    }

    public Recipe(String name, String description, String url, List<String> ingredient, List<String> ingredientQuantity) {
        Name = name;
        Description = description;
        Url = url;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
