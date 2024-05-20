package meuapp;

import meuapp.config.ConnectionFactory;
import meuapp.controller.ChatGUI;
import meuapp.controller.MainGUI;
import meuapp.service.ChooseLLMService;
import meuapp.service.DataBaseService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        new MainGUI(new DataBaseService(), new ChooseLLMService(), new ConnectionFactory());
    }
}
