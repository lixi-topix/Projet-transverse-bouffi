package com.example.Bon_Appit_eat;

public class Listcourse_Element {

    String Ingredient_name;
    String Qty_needed;
    int Ingredient_img;
    int CartQuantity;

    public Listcourse_Element(String Ingredient_name, String Qty_needed, int Ingredient_img, int CartQantity) {
        this.Ingredient_name = Ingredient_name;
        this.Qty_needed = Qty_needed;
        this.Ingredient_img = Ingredient_img;
        this.CartQuantity = CartQantity;
    }

    public void setCartQuantity(int cartQuantity) {
        CartQuantity = cartQuantity;
    }
}
