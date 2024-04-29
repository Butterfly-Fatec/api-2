package meuapp.controller;

import meuapp.service.DataBaseService;
import meuapp.service.LMStudioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ChatGUI implements ActionListener {
    private String selectedSchema;
    private final DataBaseService dataBaseService;
    private final JTextField input;
    private final JEditorPane output;

    public ChatGUI(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
        JFrame jFrame = new JFrame();
        jFrame.setSize(600, 600);
        jFrame.setTitle("ChatBot");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setBackground(Color.blue);
        jFrame.setLayout(null);


        JLabel jLabel1 = new JLabel();
        jLabel1.setText("Escolher banco de dados: ");
        jLabel1.setBounds(100, 80, 300, 50);
        jLabel1.setFont(new Font("Arial", Font.PLAIN, 18));
//        jFrame.add(jLabel1);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText("Chat Bot");
        jLabel2.setBounds(250, 20, 200, 50);
        jLabel2.setForeground(new Color(78, 120, 148));
        jLabel2.setFont(new Font("Arial", Font.BOLD, 25));
        jFrame.add(jLabel2);

        JLabel jLabel3 = new JLabel();
        jLabel3.setText("Faça uma pergunta: ");
        jLabel3.setBounds(100, 140, 200, 50);
        jLabel3.setFont(new Font("Arial", Font.PLAIN, 18));
        jFrame.add(jLabel3);

        JLabel jLabel4 = new JLabel();
        jLabel4.setText("Resultado: ");
        jLabel4.setBounds(100, 290, 200, 50);
        jLabel4.setFont(new Font("Arial", Font.PLAIN, 18));
        jFrame.add(jLabel4);

        JButton jButton = new JButton();
        jButton.setText("ENVIAR");
        jButton.setBounds(100, 240, 200, 40);
        jButton.setFont(new Font("Arial", Font.BOLD, 16));
        jButton.setBackground(new Color(135, 188, 222));
        jButton.setBorderPainted(false);
        jButton.setOpaque(true);
        jFrame.add(jButton);
        jButton.addActionListener(this);

        this.input = new JTextField();
        input.setBounds(100, 180, 400, 40);
        jFrame.add(input);

        this.output = new JEditorPane();
        output.setContentType("text/html");
        output.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(100, 330, 400, 200);
        jFrame.add(scrollPane);

        dataBaseService.FilterSchemas();
        ArrayList<String> listSchemas = dataBaseService.getSchemas();
        JComboBox<String> schemaOptions = new JComboBox<>(listSchemas.toArray(new String[0]));
        schemaOptions.setBounds(350, 57, 150, 100);
//        jFrame.add(schemaOptions);
        schemaOptions.addActionListener(e -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            selectedSchema = (String) comboBox.getSelectedItem();
        });
        jFrame.setVisible(true);
    }


    public String getSelectedSchema() {
        return selectedSchema;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (selectedSchema == null) {
//            JOptionPane.showMessageDialog(null, "Por favor, selecione um banco de dados!");
//            return;
//        }
        this.dataBaseService.DataSchema("cinemark");
        LMStudioService lmStudioService = new LMStudioService(this.input, this.dataBaseService);
        try {
            lmStudioService.connectionLMStudio();
            this.output.setText(lmStudioService.resultSQL("cinemark"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
