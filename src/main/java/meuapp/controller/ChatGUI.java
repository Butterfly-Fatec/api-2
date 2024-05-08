package meuapp.controller;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import meuapp.config.OutputStyles;
import meuapp.service.DataBaseService;
import meuapp.service.LMStudioService;

public class ChatGUI {
    private JFrame jFrame;
    private final DataBaseService dataBaseService;
    private final String selectionSchemaGUI;
    private JTextField input;
    private JEditorPane output;
    private String conversationHistory = "";

    public ChatGUI(DataBaseService dataBaseService, String selectionSchemaGUI) {
        this.selectionSchemaGUI = selectionSchemaGUI;
        this.dataBaseService = dataBaseService;
        SwingUtilities.invokeLater(this::configureGUI);
    }

    public void configureGUI() {
        jFrame = new JFrame();
        jFrame.setSize(600, 500);
        jFrame.setTitle("ChatBot");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);

        Container contentPane = jFrame.getContentPane();
        contentPane.setBackground(new Color(47, 126, 182));
        contentPane.setLayout(null);

        conversationHistory += "<b>Bot:</b> Olá, eu sou o SQL bot, faça uma pergunta:<br><br>";

        input = new JTextField();
        input.setText("Faça uma pergunta...");
        input.setBounds(10, 400, 470, 40);
        input.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (input.getText().equals("Faça uma pergunta...")) {
                    input.setText("");
                }
            }
        });

        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        output = new JEditorPane();
        HTMLEditorKit editor = new HTMLEditorKit();
        new OutputStyles().ApplyStyles(editor);
        output.setContentType("text/html");
        output.setEditable(false);
        output.setEditorKit(editor);
        output.setText("<html>" + conversationHistory + "</html>");

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(10, 40, 580, 330);

        JButton buttonSent = new JButton();
        buttonSent.setText("ENVIAR");
        buttonSent.setBounds(490, 400, 100, 40);
        buttonSent.setFont(new Font("Arial", Font.BOLD, 12));
        buttonSent.addActionListener(e -> sendMessage());

        JButton buttonReturn = new JButton();
        buttonReturn.setText("Voltar");
        buttonReturn.setBounds(5,5, 100,30);
        buttonReturn.setFont(new Font("Arial", Font.PLAIN, 12));
        buttonReturn.addActionListener(e -> returnGUI());

        jFrame.add(input);
        jFrame.add(scrollPane);
        jFrame.add(buttonSent);
        jFrame.add(buttonReturn);
        dataBaseService.DataSchema(selectionSchemaGUI);
        jFrame.setVisible(true);
    }

    private void sendMessage() {
        String userQuestion = input.getText();
        try {
            this.conversationHistory += "<b>You:</b> " + userQuestion + "<br><br>";

            LMStudioService lmStudioService = new LMStudioService(input, dataBaseService);
            lmStudioService.connectionLMStudio();
            String response = lmStudioService.resultSQL(selectionSchemaGUI);

            this.conversationHistory += "<b>Bot:</b> " + response + "<br><br>";

            this.output.setText("<html>" + conversationHistory + "</html>");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void returnGUI(){
        jFrame.setVisible(false);
        new MainGUI(dataBaseService);
    }
}

