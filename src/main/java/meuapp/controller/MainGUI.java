package meuapp.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import meuapp.service.ChooseLLMService;
import meuapp.service.DataBaseService;

public class MainGUI {
    private String selectedSchema;
    private String selectedLLM;

    public MainGUI(DataBaseService dataBaseService, ChooseLLMService chooseLLMService) {
        SwingUtilities.invokeLater(() -> {
            try {
                configureGUI(dataBaseService, chooseLLMService);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void configureGUI(DataBaseService dataBaseService, ChooseLLMService chooseLLMService)
            throws IOException, InterruptedException {
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

        ArrayList<String> listLLM = chooseLLMService.getListModels();
        JComboBox<String> llmOptions = new JComboBox<>(listLLM.toArray(new String[0]));
        llmOptions.setBounds(186, 290, 222, 50);
        contentPane.add(llmOptions);
        llmOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedLLM = (String) comboBox.getSelectedItem();
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

            if (selectedLLM == null) {
                JOptionPane.showMessageDialog(null, "Por favor, selecione um modelo de línguagem!");
                return;
            }
            try {
                chooseLLMService.commandTwo(selectedLLM);
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }

            frame.dispose();
            new ChatGUI(dataBaseService, selectedSchema, chooseLLMService);
        });

        selectedSchema = listSchemas.isEmpty() ? "" : listSchemas.get(0);

        if (!selectedSchema.isEmpty()) {
            dataBaseService.DataSchema(selectedSchema);
        }

        if (!listLLM.isEmpty()) {
            selectedLLM = listLLM.get(0);
            llmOptions.setSelectedItem(selectedLLM);
        }
        frame.setVisible(true);
    }

}
