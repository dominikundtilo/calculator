package com.github.dominikundtilo;

import java.util.ArrayList;

public class Main {

    private static ArrayList<Machine> machines = new ArrayList<>();

    static {
        machines.add(new Machine("test", 1));

    }

    public static void main(String[] args) {
        System.out.println("It works");

        new GUI().setVisible(true);


    }

    public static String getMachineName(int number){
        return machines.get(number).getName();
    }


}
