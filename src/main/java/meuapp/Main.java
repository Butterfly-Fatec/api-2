package meuapp;

import java.io.IOException;

import meuapp.controller.MainGUI;
import meuapp.factory.ConnectionFactory;
import meuapp.service.ChooseLLMService;
import meuapp.service.DataBaseService;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        new MainGUI(new DataBaseService(), new ChooseLLMService(), new ConnectionFactory());
    }
}
