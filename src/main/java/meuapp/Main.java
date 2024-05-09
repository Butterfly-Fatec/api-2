package meuapp;

import meuapp.controller.MainGUI;
import meuapp.service.ChooseLLMService;
import meuapp.service.DataBaseService;
import meuapp.service.LMStudioService;


public class Main {
    public static void main(String[] args) {
        DataBaseService dataBaseService = new DataBaseService();
        new MainGUI(dataBaseService, new ChooseLLMService());

    }
}
