package com.github.dominikundtilo.calculator.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dominik on 28.05.2017.
 */

public class WorkGUI extends GUI{
    private JPanel productionPanel;
    private JPanel configPanel;
    private JPanel resultPanel;

    @Override
    void initGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.cyan);


        productionPanel = new JPanel();
        productionPanel.setLayout(new BorderLayout());
        productionPanel.setBackground(Color.black);
        productionPanel.setPreferredSize(new Dimension(50,50));


        configPanel = new JPanel();
        configPanel.setLayout(new BorderLayout());
        configPanel.setBackground(Color.blue);
        configPanel.setPreferredSize(new Dimension(50,50));


        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBackground(Color.DARK_GRAY);
        resultPanel.setPreferredSize(new Dimension(50,50));


        panel.add(productionPanel, BorderLayout.LINE_START);
        panel.add(configPanel, BorderLayout.LINE_END);
        panel.add(resultPanel, BorderLayout.PAGE_END);



    }


    @Override
    void initListeners() {



    }

}
