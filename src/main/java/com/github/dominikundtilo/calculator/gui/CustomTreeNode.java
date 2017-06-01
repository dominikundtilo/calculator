package com.github.dominikundtilo.calculator.gui;

import com.github.dominikundtilo.calculator.lib.Ingredient;
import com.github.dominikundtilo.calculator.lib.Recipe;

import javax.swing.tree.DefaultMutableTreeNode;

public class CustomTreeNode extends DefaultMutableTreeNode {

    double amount;

    CustomTreeNode(Object userObject, double amount) {
        super(userObject);
        this.amount = amount;
    }

    @Override
    public String toString() {
        if (userObject instanceof Recipe)
            return amount + "x " + ((Recipe) userObject).getName();
        if (userObject instanceof Ingredient)
            return amount + "x " + ((Ingredient) userObject).getName();
        return super.toString();
    }

    public double getAmount() {
        return amount;
    }
}
