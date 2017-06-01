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

    public double computeAmount(double amount, int assemblingTier, int smeltingTier) {
        return amount * energy / products[0].getAmount() * products[0].getProbability() / getCraftingSpeed(assemblingTier, smeltingTier);
    }

    private double getCraftingSpeed(int assemblingTier, int smeltingTier) {
        if (category.equals("chemistry"))
            return 1.25;
        else if (category.equals("centrifuging"))
            return 0.75;
        else if (category.equals("crafting") || category.equals("advanced-crafting") || category.equals("crafting-with-fluid"))
            switch (assemblingTier) {
                case 1: return 0.5;
                case 2: return 0.75;
                case 3: return 1.25;
            }
        else if (category.equals("smelting"))
            switch (smeltingTier) {
                case 1: return 1;
                case 2: case 3: return 2;
            }
        return 0;
    }

    @Override
    public String toString() {
        return GameData.GSON.toJson(this);
    }
}
