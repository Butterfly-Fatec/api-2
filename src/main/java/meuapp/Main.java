package meuapp;

import meuapp.config.PropertiesLoader;
import meuapp.controller.ChatGUI;
import meuapp.service.DataBaseService;

public class Main {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties();

        DataBaseService dataBaseService = new DataBaseService();

        // só trocar o selectionSchemaGUI: "seu_banco_dados";
        // ChatGUI chatGUI = new ChatGUI(dataBaseService, "teste-api-2");
        new ChatGUI(dataBaseService, "teste-api-2");
    }
}
