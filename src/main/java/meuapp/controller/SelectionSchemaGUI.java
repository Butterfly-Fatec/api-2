package meuapp.controller;

import meuapp.service.DataBaseService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SelectionSchemaGUI {
    private String selectedSchema;

    public SelectionSchemaGUI(DataBaseService dataBaseService) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(600, 500);
        jFrame.setTitle("ChatBot");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        Container contentPane = jFrame.getContentPane();
        contentPane.setBackground(new Color(47,126,182));
        jFrame.setLayout(null);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/main/java/meuapp/controller/img/bot.png");
        imageLabel.setIcon(imageIcon);
        imageLabel.setBounds(260, 40, 100, 100);
        jFrame.add(imageLabel);

        JLabel jLabel1 = new JLabel();
        jLabel1.setText("Bem-vindo ao SQL Bot");
        jLabel1.setBounds(191, 150, 220, 50);
        jLabel1.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel1.setForeground(Color.white);
        jFrame.add(jLabel1);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("Selecionar Banco de Dados:");
        jLabel2.setBounds(191, 230, 220, 50);
        jLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
        jLabel2.setForeground(Color.white);
        jFrame.add(jLabel2);

        dataBaseService.FilterSchemas();
        ArrayList<String> listSchemas = dataBaseService.getSchemas();
        JComboBox<String> schemaOptions = new JComboBox<>(listSchemas.toArray(new String[0]));
        schemaOptions.setBounds(188, 270, 222, 20);
        jFrame.add(schemaOptions);
        schemaOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedSchema = (String) comboBox.getSelectedItem();
        });

        JButton jButton = new JButton();
        jButton.setText("INICIAR");
        jButton.setBounds(190, 360, 220, 40);
        jButton.setFont(new Font("Arial", Font.PLAIN, 16));
        jFrame.add(jButton);
        jFrame.setVisible(true);

        jButton.addActionListener(e -> {
            if (selectedSchema == null) {
                JOptionPane.showMessageDialog(null, "Por favor, selecione um banco de dados!");
                return;
            }
            jFrame.setVisible(false);
            ChatGUI chatGUI = new ChatGUI(dataBaseService, selectedSchema);

        });

    }


}
