package com.github.dominikundtilo.calculator.lib;

public class Recipe {

    private String name;
    private String category;
    private Ingredient[] ingredients;
    private Product[] products;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Product[] getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return GameData.GSON.toJson(this);
    }
}
