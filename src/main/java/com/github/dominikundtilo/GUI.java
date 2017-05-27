package com.github.dominikundtilo;

import javax.swing.*;

/**
 * Created by Dominik on 23.05.2017.
 */

public class GUI extends JFrame {
    private JPanel productionPanel;
    private JPanel configPanel;
    private JButton applyChangesButton;
    private JButton calculateButton;
    private JComboBox<String> furnaceComboBox;
    private JPanel panel1;
    private JComboBox assemblingComboBox;


    //final String[] miner = {"Burner Mining Drill", "Electric Minig Drill"};
    final String[] furnaces = {"Stonefurnace", "Steelfurnace", "Electronicfurnace"};
    final String[] assemblers = {"Assembling Maschien 1", "Assembling Maschien 2", "Assembling Maschien 3"};

    public GUI() {
        super("Example name");
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
    }
}
