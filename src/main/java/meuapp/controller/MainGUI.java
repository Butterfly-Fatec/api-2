package meuapp.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import meuapp.factory.ConnectionFactory;
import meuapp.service.ChooseLLMService;
import meuapp.service.DataBaseService;

public class MainGUI {
    private String selectedSchema;
    private String selectedLLM;

    public MainGUI(DataBaseService dataBaseService, ChooseLLMService chooseLLMService,
            ConnectionFactory connectionFactory) {
        SwingUtilities.invokeLater(() -> {
            try {
                chooseLLMService.unloadLMStudioModel();
                configureGUI(dataBaseService, chooseLLMService, connectionFactory);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void configureGUI(DataBaseService dataBaseService, ChooseLLMService chooseLLMService,
            ConnectionFactory connectionFactory) throws IOException, InterruptedException {
        JFrame frame = new JFrame("ChatBot");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(new Color(255, 255, 255, 204));
        contentPane.setLayout(null);

        JLabel logotipo = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/main/resources/static/img/logo.jpg");
        Image image = imageIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        logotipo.setIcon(imageIcon);
        logotipo.setBounds(252, 30, 90, 90);
        frame.add(logotipo);

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

        ArrayList<String> listSchemas = DataBaseService.filterSchemas(connectionFactory);
        JComboBox<String> schemaOptions = new JComboBox<>(listSchemas.toArray(new String[0]));
        schemaOptions.setBounds(186, 230, 222, 25);
        schemaOptions.setOpaque(true);
        schemaOptions.setBackground(Color.white);
        schemaOptions.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(schemaOptions);
        schemaOptions.addActionListener(e -> {
            @SuppressWarnings("unchecked")
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedSchema = (String) comboBox.getSelectedItem();
            dataBaseService.dataSchema(selectedSchema, new ConnectionFactory());
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
            @SuppressWarnings("unchecked")
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
                chooseLLMService.loadLMStudioModel(selectedLLM);
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }

            frame.dispose();
            new ChatGUI(dataBaseService, selectedSchema, chooseLLMService, connectionFactory);
        });

        selectedSchema = listSchemas.isEmpty() ? "" : listSchemas.get(0);

        if (!selectedSchema.isEmpty()) {
            dataBaseService.dataSchema(selectedSchema, new ConnectionFactory());
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
                    chooseLLMService.unloadLMStudioModel();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            }
        });
    }

}
