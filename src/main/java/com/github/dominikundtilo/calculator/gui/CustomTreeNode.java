package com.github.dominikundtilo.calculator.gui;

import com.github.dominikundtilo.calculator.lib.Ingredient;
import com.github.dominikundtilo.calculator.lib.Recipe;

import javax.swing.tree.DefaultMutableTreeNode;

public class CustomTreeNode extends DefaultMutableTreeNode {

    public void setAmount(double amount) {
        this.amount = amount;
    }

    double amount;

    CustomTreeNode(Object userObject, double amount) {
        super(userObject);
        this.amount = amount;
    }

    @Override
    public String toString() {
        if (userObject instanceof Recipe)
            return (Math.round(amount * 100)/ 100) + " Maschinen " + ((Recipe) userObject).getName();
        if (userObject instanceof Ingredient)
            return (Math.round(amount*100) / 100 )+ "x " + ((Ingredient) userObject).getName() + "/sec";
        return super.toString();
    }

    public double getAmount() {
        return amount;
    }
}
