package meuapp.controller;

import meuapp.service.DataBaseService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI {
    private String selectedSchema;

    public MainGUI(DataBaseService dataBaseService) {
        SwingUtilities.invokeLater(() -> configureGUI(dataBaseService));
    }

    private void configureGUI(DataBaseService dataBaseService) {
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
        schemaLabel.setBounds(191, 230, 220, 50);
        schemaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        schemaLabel.setForeground(Color.WHITE);
        contentPane.add(schemaLabel);

        if (dataBaseService.getSchemas().isEmpty()) {
            dataBaseService.FilterSchemas();
        }
        ArrayList<String> listSchemas = dataBaseService.getSchemas();
        JComboBox<String> schemaOptions = new JComboBox<>(listSchemas.toArray(new String[0]));
        schemaOptions.setBounds(188, 270, 222, 20);
        contentPane.add(schemaOptions);
        schemaOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedSchema = (String) comboBox.getSelectedItem();
            dataBaseService.DataSchema(selectedSchema);
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
