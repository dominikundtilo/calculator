package com.github.dominikundtilo.calculator.gui;

import javax.swing.*;

/**
 * Created by Dominik on 28.05.2017.
 */

abstract class GUI extends JFrame {

    final JPanel panel;

    GUI() {
        // create panel
        panel = new JPanel();

        // initialise GUI
        initGUI();

        // add panel
        setContentPane(panel);
        pack();

        // initialise Listeners
        initListeners();
    }

    abstract void initGUI();

    abstract void initListeners();
}