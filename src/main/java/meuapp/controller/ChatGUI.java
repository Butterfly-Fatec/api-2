package meuapp.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.html.HTMLEditorKit;

import meuapp.config.OutputStyles;
import meuapp.service.DataBaseService;
import meuapp.service.LMStudioService;

public class ChatGUI implements ActionListener, KeyListener {
    private final DataBaseService dataBaseService;
    private final String selectionSchemaGUI;
    private final JTextField input;
    private final JEditorPane output;
    private String conversationHistory = "";

    public ChatGUI(DataBaseService dataBaseService, String selectionSchemaGUI) {
        this.selectionSchemaGUI = selectionSchemaGUI;
        this.dataBaseService = dataBaseService;

        JFrame jFrame = new JFrame();
        jFrame.setSize(600, 500);
        jFrame.setTitle("ChatBot");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        Container contentPane = jFrame.getContentPane();
        contentPane.setBackground(new Color(47, 126, 182));
        jFrame.setLayout(null);

        conversationHistory += "<b>Bot:</b> Olá, eu sou o SQL bot, faça uma pergunta:<br><br>";

        this.input = new JTextField("Faça uma pergunta...");
        input.setBounds(10, 400, 480, 40);
        input.setForeground(new Color(12, 12, 12, 153));
        input.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (input.getText().equals("Faça uma pergunta...")) {
                    input.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        jFrame.add(input);

        this.output = new JEditorPane();
        HTMLEditorKit editor = new HTMLEditorKit();
        new OutputStyles().ApplyStyles(editor);
        output.setContentType("text/html");
        output.setEditable(false);
        output.setEditorKit(editor);

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(10, 30, 580, 330);
        jFrame.add(scrollPane);

        JButton jButton = new JButton();
        jButton.setText("ENVIAR");
        jButton.setBounds(490, 400, 100, 40);
        jButton.setFont(new Font("Arial", Font.BOLD, 12));
        jButton.addActionListener(this);
        jFrame.add(jButton);
        jFrame.setVisible(true);

        output.setText("<html>" + conversationHistory + "</html>");

        input.addKeyListener(this); // Adicionando o KeyListener ao campo de entrada
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userQuestion = input.getText();

        try {

            conversationHistory += "<b>You:</b> " + userQuestion + "<br>";

            this.dataBaseService.DataSchema(this.selectionSchemaGUI);
            LMStudioService lmStudioService = new LMStudioService(this.input, this.dataBaseService);
            lmStudioService.connectionLMStudio();
            String response = lmStudioService.resultSQL(this.selectionSchemaGUI);

            conversationHistory += "<b>Bot:</b> " + response + "<br>";

            output.setText("<html>" + conversationHistory + "</html>");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não precisa ser implementado, mas requerido pela interface KeyListener
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Não precisa ser implementado, mas requerido pela interface KeyListener
    }
}
