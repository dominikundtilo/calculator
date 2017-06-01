package com.github.dominikundtilo.calculator.lib;

public class Product implements IItem {

    private String type;
    private String name;
    private double amount = 1;
    private int temperature = -1;
    private int amount_min = 1;
    private int amount_max = 1;
    private double probability = 1;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }


    public double getAmount() {
        return amount * (amount_min + amount_max) / 2;
    }

    public int getTemperature() {
        return temperature;
    }

    public double getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return GameData.GSON.toJson(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return obj == this;
        } else if (obj instanceof IItem) {
            return type.equals(((IItem) obj).getType()) && name.equals(((IItem) obj).getName());
        }
        return false;
    }
}
