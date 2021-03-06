package com.github.dominikundtilo.calculator.gui;

import com.github.dominikundtilo.calculator.lib.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by Dominik on 28.05.2017.
 */

public class WorkGUI extends GUI{

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel productionPanel;
    private JPanel productionPanelCenter;
    private JPanel configPanel;
    private JPanel configPanelLeft;
    private JPanel configPanelRight;
    private JScrollPane resultPanel;
    private JComboBox<String> products;
    private JComboBox<String> machines;
    private JComboBox<String> furnaces;
    private JLabel speedbonus;
    private JLabel productivitybonus;
    private JLabel amount;
    private JTextField speedField;
    private JTextField productivityField;
    private JTextField amountField;
    //private JButton confirmButton;
    //private JButton calculateButton;
    private JTree rootTree;
    private JTextPane input;

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

        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        input = new JTextPane();
        input.setBorder(BorderFactory.createBevelBorder(1));
        input.setPreferredSize(new Dimension(250,150));
        rightPanel.add(new JLabel("Input:"), BorderLayout.PAGE_START);
        rightPanel.add(input, BorderLayout.CENTER);

        productionPanel = new JPanel();
        productionPanel.setLayout(new BorderLayout());

        configPanel = new JPanel();
        configPanel.setLayout(new BorderLayout());

        resultPanel = new JScrollPane();
        resultPanel.setPreferredSize(new Dimension(50,300));

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
        amountField.setText("1");


        products = new JComboBox<>();


        machines = new JComboBox<>();
        machines.addItem("Assembling-Machine 1");
        machines.addItem("Assembling-Machine 2");
        machines.addItem("Assembling-Machine 3");

        furnaces = new JComboBox<>();
        furnaces.addItem("Stone Furnace");
        furnaces.addItem("Steel Furnace");
        furnaces.addItem("Electronic Furnace");

        //confirmButton = new JButton();
        //confirmButton.setText("Confirm changes");

        //calculateButton = new JButton();
        //calculateButton.setText("Calculate");




        leftPanel.add(productionPanel, BorderLayout.LINE_START);
        //panel.add(new SpaceHolder(25, 0), BorderLayout.CENTER);
        leftPanel.add(configPanel, BorderLayout.LINE_END);
        leftPanel.add(resultPanel, BorderLayout.PAGE_END);

        panel.add(leftPanel, BorderLayout.LINE_START);
        panel.add(new JSeparator(JSeparator.VERTICAL),  BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.LINE_END);

        productionPanel.add(products, BorderLayout.PAGE_START);
        productionPanel.add(productionPanelCenter, BorderLayout.CENTER);
        //productionPanel.add(calculateButton, BorderLayout.PAGE_END);

        productionPanelCenter.add(amount, BorderLayout.PAGE_START);
        productionPanelCenter.add(amountField, BorderLayout.PAGE_END);

        configPanel.add(machines, BorderLayout.PAGE_START);
        configPanel.add(furnaces, BorderLayout.CENTER);
        configPanel.add(new SpaceHolder(0, 20), BorderLayout.PAGE_END);


        /*
        configPanel.add(configPanelLeft, BorderLayout.LINE_START);
        configPanel.add(new SpaceHolder(25, 0), BorderLayout.CENTER);
        configPanel.add(configPanelRight, BorderLayout.LINE_END);
        configPanel.add(confirmButton, BorderLayout.PAGE_END);

        configPanelLeft.add(speedbonus, BorderLayout.PAGE_START);
        configPanelLeft.add(speedField, BorderLayout.PAGE_END);

        configPanelRight.add(productivitybonus, BorderLayout.PAGE_START);
        configPanelRight.add(productivityField, BorderLayout.PAGE_END);
        */


    }


    @Override
    void initListeners() {

        amountField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculate();
                updateColor();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculate();
                updateColor();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculate();
                updateColor();
            }

            private void updateColor() {
                double amount;
                try {
                    amount = Double.parseDouble(amountField.getText().replace(',', '.'));
                } catch (NumberFormatException e) {
                    amountField.setForeground(Color.RED);
                    return;
                }
                if (amount <= 0) {
                    amountField.setForeground(Color.ORANGE);
                    return;
                }
                amountField.setForeground(Color.BLACK);
            }
        });

        products.addItemListener(e -> calculate());
        machines.addItemListener(e -> calculate());
        furnaces.addItemListener(e -> calculate());

    }

    private void calculate() {
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().replace(',', '.'));
        } catch (NumberFormatException e) {
            return;
        }
        if (amount <= 0) return;
        Recipe recipe = inputRecipe(data.craftableItems.get(products.getSelectedIndex()));
        CustomTreeNode rootNode = new CustomTreeNode(recipe, recipe.computeAmount(amount, machines.getSelectedIndex() + 1, furnaces.getSelectedIndex() + 1));
        for (Ingredient ingredient : recipe.getIngredients())
            rootNode.add(new CustomTreeNode(ingredient, recipe.computeAmount(amount, machines.getSelectedIndex() + 1, furnaces.getSelectedIndex() + 1) * ingredient.getAmount() / recipe.getEnergy() * recipe.getCraftingSpeed(machines.getSelectedIndex() + 1, furnaces.getSelectedIndex() + 1)));
        resultPanel.setViewportView(rootTree = new JTree(rootNode));
        updateInput();
        rootTree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CustomTreeNode node = (CustomTreeNode) rootTree.getSelectionPath().getLastPathComponent();
                if (node == null) return;
                if (node.getUserObject() instanceof Ingredient) {
                    Ingredient ingredient = (Ingredient) node.getUserObject();
                    Item item = data.getItem(ingredient);
                    if (!data.recipesForItem.containsKey(item)) return;
                    Recipe recipe = inputRecipe(item);
                    node.setUserObject(recipe);
                    node.setAmount(recipe.computeAmount(node.getAmount(), machines.getSelectedIndex() + 1, furnaces.getSelectedIndex() + 1));
                    for (Ingredient i : recipe.getIngredients())
                        node.add(new CustomTreeNode(i, node.getAmount() * i.getAmount()  / recipe.getEnergy() * recipe.getCraftingSpeed(machines.getSelectedIndex() + 1, furnaces.getSelectedIndex() + 1)));
                }
                updateInput();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("sadads");
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


    }

    private void updateInput() {
        StringBuilder builder = new StringBuilder();
        getInput().forEach(new BiConsumer<Item, Double>() {
            @Override
            public void accept(Item item, Double amount) {
                builder.append(Math.round(amount * 100) / 100D).append("x ").append(item.getName()).append("/sec\n");
            }
        });
        input.setText(builder.toString());
    }

    private HashMap<Item, Double> getInput() {
        HashMap<Item, Double> ret = new HashMap<>();
        ArrayList<CustomTreeNode> list = getSubNodes((CustomTreeNode) rootTree.getModel().getRoot());
        for (CustomTreeNode node : list) {
            if (node.getUserObject() instanceof Ingredient) {
                Item item = data.getItem((Ingredient) node.getUserObject());
                if (!ret.containsKey(item))
                    ret.put(item, 0D);
                ret.replace(item, ret.get(item) + node.amount);
            }
        }
        return ret;
    }

    private ArrayList<CustomTreeNode> getSubNodes(CustomTreeNode customTreeNode) {
        ArrayList<CustomTreeNode> list = new ArrayList<>();
        for (int i = 0; i < customTreeNode.getChildCount(); i++) {
            CustomTreeNode node = (CustomTreeNode) customTreeNode.getChildAt(i);
            list.add(node);
            list.addAll(getSubNodes(node));
        }
        return list;
    }

    private Recipe inputRecipe(Item item) {
        ArrayList<Recipe> recipes = data.recipesForItem.get(item);
        if (recipes.size() == 1)
            return recipes.get(0);
        JComboBox<String> comboBox = new JComboBox<>();
        for (Recipe recipe : recipes)
            comboBox.addItem(recipe.getName());
        JOptionPane.showMessageDialog( null, comboBox, "select recipe", JOptionPane.QUESTION_MESSAGE);
        return recipes.get(comboBox.getSelectedIndex());
    }

}
