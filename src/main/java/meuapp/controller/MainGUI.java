package meuapp.controller;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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
        contentPane.setBackground(new Color(255, 255, 255, 204));
        contentPane.setLayout(null);

        JLabel imageLOGO = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/main/resources/static/img/logo.jpg");

        Image image = imageIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        imageLOGO.setIcon(imageIcon);
        imageLOGO.setBounds(252, 30, 90, 90);
        frame.add(imageLOGO);



        JLabel welcomeLabel = new JLabel("Bem-vindo ao SQL Bot");
        welcomeLabel.setBounds(191, 130, 250, 50);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0, 0, 0, 179));
        contentPane.add(welcomeLabel);

        JLabel schemaLabel = new JLabel("Selecionar Banco de Dados:");
        schemaLabel.setBounds(191, 200, 220, 30);
        schemaLabel.setForeground(new Color(0, 0, 0, 153));
        schemaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(schemaLabel);

        if (dataBaseService.getSchemas().isEmpty()) {
            dataBaseService.FilterSchemas();
        }
        ArrayList<String> listSchemas = dataBaseService.getSchemas();
        JComboBox<String> schemaOptions = new JComboBox<>(listSchemas.toArray(new String[0]));
        schemaOptions.setBounds(186, 230, 222, 25);
        schemaOptions.setOpaque(true);
        schemaOptions.setBackground(Color.white);
        schemaOptions.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(schemaOptions);
        schemaOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedSchema = (String) comboBox.getSelectedItem();
            dataBaseService.DataSchema(selectedSchema);
        });

        JLabel languageLabel = new JLabel("Selecionar LLM:");
        languageLabel.setBounds(191, 290, 220, 30);
        languageLabel.setForeground(new Color(0, 0, 0, 153));
        languageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(languageLabel);

        ArrayList<String> listLLM = chooseLLMService.getListModels();
        JComboBox<String> llmOptions = new JComboBox<>(listLLM.toArray(new String[0]));
        llmOptions.setBounds(186, 320, 222, 25);
        llmOptions.setOpaque(true);
        llmOptions.setBackground(Color.white);
        contentPane.add(llmOptions);
        llmOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedLLM = (String) comboBox.getSelectedItem();
        });

        JButton startButton = new JButton("INICIAR");
        startButton.setBounds(190, 390, 220, 40);
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.setForeground(new Color(0, 0, 0, 153));
        startButton.setOpaque(true);
        startButton.setBackground(Color.white);
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

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    chooseLLMService.commandThree();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });
    }

}
