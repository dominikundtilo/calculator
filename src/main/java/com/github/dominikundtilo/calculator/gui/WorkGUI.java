package com.github.dominikundtilo.calculator.gui;

import com.github.dominikundtilo.calculator.lib.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Dominik on 28.05.2017.
 */

public class WorkGUI extends GUI{
    private JPanel productionPanel;
    private JPanel productionPanelCenter;
    private JPanel configPanel;
    private JPanel configPanelLeft;
    private JPanel configPanelRight;
    private JPanel resultPanel;
    private JComboBox<String> products;
    private JComboBox<String> machines;
    private JLabel speedbonus;
    private JLabel productivitybonus;
    private JLabel amount;
    private JTextField speedField;
    private JTextField productivityField;
    private JTextField amountField;
    private JButton confirmButton;
    private JButton calculateButton;
    private JScrollPane treePane;

    private GameData data;


    public WorkGUI(GameData data) {
        super();
        this.data = data;

        initData();
        pack();
    }

    private void initData() {
        for(Item item : data.craftableItems){
            products.addItem(item.getName());
        }
    }

    @Override
    void initGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        productionPanel = new JPanel();
        productionPanel.setLayout(new BorderLayout());

        configPanel = new JPanel();
        configPanel.setLayout(new BorderLayout());

        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBackground(Color.white);
        resultPanel.setPreferredSize(new Dimension(50,200));

        configPanelLeft = new JPanel();
        configPanelLeft.setLayout(new BorderLayout());

        configPanelRight = new JPanel();
        configPanelRight.setLayout(new BorderLayout());

        productionPanelCenter = new JPanel();
        productionPanelCenter.setLayout(new BorderLayout());

        speedbonus = new JLabel();
        speedbonus.setText("speedbonus");

        productivitybonus = new JLabel();
        productivitybonus.setText("productivitybonus");

        amount = new JLabel();
        amount.setText("Amount per second");

        speedField = new JTextField();
        speedField.setText("0");

        productivityField = new JTextField();
        productivityField.setText("0");

        amountField = new JTextField();
        amountField.setText("0");


        products = new JComboBox<>();


        machines = new JComboBox<>();
        machines.addItem("Machine 0");
        machines.addItem("Machine 1");
        machines.addItem("Machine 2");

        confirmButton = new JButton();
        confirmButton.setText("Confirm changes");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        calculateButton = new JButton();
        calculateButton.setText("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        treePane = new JScrollPane();





        panel.add(productionPanel, BorderLayout.LINE_START);
        panel.add(new SpaceHolder(25, 0), BorderLayout.CENTER);
        panel.add(configPanel, BorderLayout.LINE_END);
        panel.add(resultPanel, BorderLayout.PAGE_END);

        productionPanel.add(products, BorderLayout.PAGE_START);
        productionPanel.add(productionPanelCenter, BorderLayout.CENTER);
        productionPanel.add(calculateButton, BorderLayout.PAGE_END);

        productionPanelCenter.add(amount, BorderLayout.PAGE_START);
        productionPanelCenter.add(amountField, BorderLayout.PAGE_END);

        configPanel.add(machines, BorderLayout.PAGE_START);
        configPanel.add(configPanelLeft, BorderLayout.LINE_START);
        configPanel.add(new SpaceHolder(25, 0), BorderLayout.CENTER);
        configPanel.add(configPanelRight, BorderLayout.LINE_END);
        configPanel.add(confirmButton, BorderLayout.PAGE_END);

        configPanelLeft.add(speedbonus, BorderLayout.PAGE_START);
        configPanelLeft.add(speedField, BorderLayout.PAGE_END);

        configPanelRight.add(productivitybonus, BorderLayout.PAGE_START);
        configPanelRight.add(productivityField, BorderLayout.PAGE_END);

        resultPanel.add(treePane, BorderLayout.CENTER);


    }


    @Override
    void initListeners() {

        calculateButton.addActionListener(e -> {

            printTree(data.craftableItems.get(products.getSelectedIndex()), Integer.parseInt(amountField.getText()));
        });

        confirmButton.addActionListener(e -> {

        });

    }

    public Recipe inputRecipe(Item item) {
        ArrayList<Recipe> recipes = data.recipesForItem.get(item);
        if (recipes.size() == 1)
            return recipes.get(0);
        JComboBox<String> comboBox = new JComboBox<>();
        for (Recipe recipe : recipes)
            comboBox.addItem(recipe.getName());
        JOptionPane.showMessageDialog( null, comboBox, "select recipe", JOptionPane.QUESTION_MESSAGE);
        System.out.println(comboBox.getSelectedIndex());
        return recipes.get(comboBox.getSelectedIndex());
    }

    private TreeNode[] buildNode(Item item, double amount){
        if (!isCraftabel(item.getName())){
            DefaultMutableTreeNode[] treeNode = new DefaultMutableTreeNode[1];
            treeNode[0] = new DefaultMutableTreeNode(item.getName() + " " + amount + "/s");
            return treeNode;
        }
        else {

            Recipe recipe = new Recipe();
            recipe = inputRecipe(item);

            double ingredientAmount = 0;
            int productIndex = 0;

            Product[] products = recipe.getProducts();

            Ingredient[] ingredients = recipe.getIngredients();

            TreeNode[] treeNode = new DefaultMutableTreeNode[ingredients.length];

            for (int x = 0; x < ingredients.length; x++) {
                ingredientAmount = (amount * ingredients[x].getAmount() / products[productIndex].getAmount());

                treeNode[x] = new DefaultMutableTreeNode(ingredients[x].getName() + " " + amount + "/s");

                TreeNode[] nodeNode = buildNode(data.items.get(getIndexDataItems(ingredients[x].getName())), ingredientAmount);

                for (int y = 0; y < nodeNode.length; y++) treeNode[x].add(nodeNode[y]);
            }

            return treeNode;
        }
    }

    private TreeNode buildMainNode(Item item, int amount){
        TreeNode tree = new DefaultMutableTreeNode(item.getName() + " " + amount + "/s");

        TreeNode[] treeNodes = buildNode(item, amount);

        for (int x = 0; x > treeNodes.length; x++) tree.add(treeNodes[x]);

        return tree;

    }


    private void printTree(Item item,int amount){

        resultPanel.add(new JScrollPane(new JTree(buildMainNode(item, amount))), BorderLayout.CENTER);

        pack();

    }

    private boolean isCraftabel (String name){
        if (name.contains("barrel")) return false;
        for (int x = 0; x < data.craftableItems.size(); x++){
            if (name.equalsIgnoreCase(data.craftableItems.get(x).getName())) return true;
        }
        return false;
    }



    private int getIndexDataItems(String name){
        int index = -1;

        for (int x = 0; x < data.items.size(); x++){
            if (name.equalsIgnoreCase(data.items.get(x).getName())) index = x;
        }

        return index;
    }

    private Item getItem(String name){
        return data.items.get(getIndexDataItems(name));
    }




}
