package meuapp;

import meuapp.config.ConnectionFactory;
import meuapp.controller.SelectionSchemaGUI;
import meuapp.service.DataBaseService;

public class Main {
    public static void main(String[] args) {
        DataBaseService dataBaseService = new DataBaseService();
        SelectionSchemaGUI selectionSchemaGUI = new SelectionSchemaGUI(dataBaseService);

    }
}
