package com.github.dominikundtilo.calculator;

import com.github.dominikundtilo.calculator.gui.WorkGUI;
import com.github.dominikundtilo.calculator.lib.GameData;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        GameData data;
        try {
            data = new GameData();
        } catch (IOException e) {
            return;
        }
        new WorkGUI(data).setVisible(true);





    }



}
