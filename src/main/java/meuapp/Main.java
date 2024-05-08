package meuapp;

import meuapp.controller.MainGUI;
import meuapp.service.DataBaseService;

public class Main {
    public static void main(String[] args) {
        DataBaseService dataBaseService = new DataBaseService();
        new MainGUI(dataBaseService);
    }
}
