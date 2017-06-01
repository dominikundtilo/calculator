package com.github.dominikundtilo.calculator.lib;

public class Recipe {

    private String name;
    private String category;
    private Ingredient[] ingredients;
    private Product[] products;
    private double energy;

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

    public double getEnergy() {
        return energy;
    }

    public double computeAmount(double amount) {
        return amount * energy / products[0].getAmount() * products[0].getProbability();
    }

    @Override
    public String toString() {
        return GameData.GSON.toJson(this);
    }
}
