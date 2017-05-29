package com.github.dominikundtilo.calculator.lib;

public class Ingredient implements IItem {

    private String type;
    private String name;
    private int amount = 1;
    private int minimum_temperature = -1;
    private int maximum_temperature = -1;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getMinimumTemperature() {
        return minimum_temperature;
    }

    public int getMaximumTemperature() {
        return maximum_temperature;
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
