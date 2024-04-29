package meuapp;

import meuapp.config.PropertiesLoader;
import meuapp.controller.ChatGUI;
import meuapp.service.DataBaseService;

public class Main {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties();

        DataBaseService dataBaseService = new DataBaseService();
        ChatGUI chatGUI = new ChatGUI(dataBaseService);

    }
}
