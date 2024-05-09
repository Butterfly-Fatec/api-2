package meuapp.controller;

import meuapp.service.ChooseLLMService;
import meuapp.service.DataBaseService;
import meuapp.service.LMStudioService;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI {
    private String selectedSchema;
    private String selectedLLM;
    private ChooseLLMService chooseLLMService;

    public MainGUI(DataBaseService dataBaseService, ChooseLLMService chooseLLMService) {
        SwingUtilities.invokeLater(() -> configureGUI(dataBaseService, chooseLLMService));
    }

    private void configureGUI(DataBaseService dataBaseService, ChooseLLMService chooseLLMService) {
        JFrame frame = new JFrame("ChatBot");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(new Color(47, 126, 182));
        contentPane.setLayout(null);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/main/resources/static/img/bot.png");
        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(260, 40, 100, 100);
        contentPane.add(imageLabel);

        JLabel welcomeLabel = new JLabel("Bem-vindo ao SQL Bot");
        welcomeLabel.setBounds(191, 150, 220, 50);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        contentPane.add(welcomeLabel);

        JLabel schemaLabel = new JLabel("Selecionar Banco de Dados:");
        schemaLabel.setBounds(191, 190, 220, 50);
        schemaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        schemaLabel.setForeground(Color.WHITE);
        contentPane.add(schemaLabel);


        if (dataBaseService.getSchemas().isEmpty()) {
            dataBaseService.FilterSchemas();
        }
        ArrayList<String> listSchemas = dataBaseService.getSchemas();
        JComboBox<String> schemaOptions = new JComboBox<>(listSchemas.toArray(new String[0]));
        schemaOptions.setBounds(186, 220, 222, 50);
        contentPane.add(schemaOptions);
        schemaOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedSchema = (String) comboBox.getSelectedItem();
            dataBaseService.DataSchema(selectedSchema);
        });

        JLabel languageLabel = new JLabel("Selecionar LLM:");
        languageLabel.setBounds(191, 260, 220, 50);
        languageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        languageLabel.setForeground(Color.WHITE);
        contentPane.add(languageLabel);

        if (chooseLLMService.getNameModel().isEmpty()) {
            chooseLLMService.lmList();
        }
        ArrayList<String> listLLM = chooseLLMService.getNameModel();
        JComboBox<String> llmOptions = new JComboBox<>(listLLM.toArray(new String[0]));
        llmOptions.setBounds(186, 290, 222, 50);
        contentPane.add(llmOptions);
        llmOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedLLM = (String) comboBox.getSelectedItem();
            dataBaseService.DataSchema(selectedLLM);
        });


        JButton startButton = new JButton("INICIAR");
        startButton.setBounds(190, 360, 220, 40);
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(startButton);
        startButton.addActionListener(e -> {
            if (selectedSchema == null) {
                JOptionPane.showMessageDialog(null, "Por favor, selecione um banco de dados!");
                return;
            }
            frame.dispose();
            new ChatGUI(dataBaseService, selectedSchema);
        });
        selectedSchema = listSchemas.isEmpty() ? "" : listSchemas.get(0);
        if (!selectedSchema.isEmpty()) {
            dataBaseService.DataSchema(selectedSchema);
        }

        frame.setVisible(true);
    }
}
