package com.github.dominikundtilo.calculator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dominik on 23.05.2017.
 */

public class OldGUI extends JFrame {
    private JPanel productionMainPanel;
    private JPanel configPanel;
    private JButton applyChangesButton;
    private JPanel panel1;
    private JComboBox assemblingComboBox;
    private JTextField productivityTextField;
    private JTextField speedTextField;
    private JLabel Speed;
    private JLabel Productivity;
    private JButton calculateButton;
    private JPanel productionInnerPanel;
    private JComboBox<String> productComboBox;
    private JLabel errorLabel;


    //final String[] miner = {"Burner Mining Drill", "Electric Minig Drill"};
    final String[] furnaces = {"Stonefurnace", "Steelfurnace", "Electronicfurnace"};
    final String[] assemblers = {"Assembling Maschien 1", "Assembling Maschien 2", "Assembling Maschien 3"};



    public OldGUI() {
        super("Example name");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();

        updateProductComboBox();
        updateAssemblingComboBox();

        speedTextField.setText("0");
        productivityTextField.setText("0");


        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });

        applyChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyCanges();
            }
        });
    }


    private void createUIComponents() {
        productComboBox = new JComboBox<>();
        errorLabel = new JLabel();


    }

    private void updateProductComboBox(){
        productComboBox.addItem("test 0");
        productComboBox.addItem("test 1");
        productComboBox.addItem("test 2");
    }


    private void updateAssemblingComboBox(){
        assemblingComboBox.addItem("test0");
        assemblingComboBox.addItem("test1");
        assemblingComboBox.addItem("test2");
    }

    private void calculate(){


    }

    private void applyCanges(){


        try {
            int newspeed = Integer.parseInt(speedTextField.getText());
            int newproductivity = Integer.parseInt(productivityTextField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Prodoctivity and Speed have to be givven as integers (0 = no bonus)");
        }

    }


}
