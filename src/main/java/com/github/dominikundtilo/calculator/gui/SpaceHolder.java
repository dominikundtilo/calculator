package com.github.dominikundtilo.calculator.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dominik on 28.05.2017.
 */
class SpaceHolder extends JPanel {

    SpaceHolder(int verticalSpace, int horitsontalSpace){

        if (verticalSpace <= 0) verticalSpace = -1;
        if (horitsontalSpace <= 0) horitsontalSpace = -1;

        setPreferredSize(new Dimension(verticalSpace, horitsontalSpace));
    }
}
