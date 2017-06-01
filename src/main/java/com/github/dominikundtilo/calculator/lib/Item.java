package com.github.dominikundtilo.calculator.lib;

public class Item implements IItem {

    private final String type;
    private final String name;

    public Item(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Item(IItem item) {
        this(item.getType(), item.getName());
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IItem) {
            return type.equals(((IItem) obj).getType()) && name.equals(((IItem) obj).getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return GameData.GSON.toJson(this);
    }

    @Override
    public int hashCode() {
        return (type + name).hashCode();
    }

}
