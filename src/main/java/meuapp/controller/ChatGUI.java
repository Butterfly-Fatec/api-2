package meuapp.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.html.HTMLEditorKit;

import meuapp.config.OutputStyles;
import meuapp.service.ChooseLLMService;
import meuapp.service.DataBaseService;
import meuapp.service.LMStudioService;

public class ChatGUI {
    private JFrame jFrame;
    private final DataBaseService dataBaseService;
    private final String selectionSchemaGUI;
    private JTextField input;
    private JEditorPane output;
    private String conversationHistory = "";
    private final ChooseLLMService chooseLLMService;

    public ChatGUI(DataBaseService dataBaseService, String selectionSchemaGUI, ChooseLLMService chooseLLMService) {
        this.selectionSchemaGUI = selectionSchemaGUI;
        this.dataBaseService = dataBaseService;
        this.chooseLLMService = chooseLLMService;
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
        contentPane.setBackground(Color.white);
        contentPane.setLayout(null);

        conversationHistory += "<b>Bot:</b> Olá, eu sou o SQL bot, faça uma pergunta:<br><br>";

        Border roundedBorder = createRoundedBorder(10);

        input = new JTextField();
        input.setText("Faça uma pergunta...");
        input.setBounds(40, 400, 410, 40);
        input.setBackground(new Color(255, 255, 255, 255));
        input.setForeground(new Color(0, 0, 0, 128));
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                roundedBorder));
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
        output.setForeground(new Color(0, 0, 0, 102));
        output.setText("<html>" + conversationHistory + "</html>");
        output.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                roundedBorder));

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(40, 55, 525, 330);
        scrollPane.setBorder(null);

        JButton buttonSent = new JButton();
        buttonSent.setText("ENVIAR");
        buttonSent.setBounds(470, 400, 90, 36);
        buttonSent.setFont(new Font("Arial", Font.BOLD, 12));
        buttonSent.addActionListener(e -> sendMessage());

        JButton buttonReturn = new JButton();
        buttonReturn.setText("Voltar");
        buttonReturn.setBounds(470, 15, 90, 36);
        buttonReturn.setFont(new Font("Arial", Font.BOLD, 14));
        buttonReturn.addActionListener(e -> {
            try {
                chooseLLMService.commandThree();
                returnGUI();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        JLabel nameDB = new JLabel();
        nameDB.setText("Banco de dados:      " + this.selectionSchemaGUI);
        nameDB.setBounds(44, 15, 500, 35);
        nameDB.setFont(new Font("Arial", Font.BOLD, 14));
        nameDB.setForeground(new Color(0, 0, 0, 179));
        jFrame.add(nameDB);

        JLabel imageOnlineDB = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/main/resources/static/img/online.png");

        Image image = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        imageOnlineDB.setIcon(imageIcon);
        imageOnlineDB.setBounds(160, 21, 25, 25);
        jFrame.add(imageOnlineDB);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    chooseLLMService.commandThree();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                jFrame.dispose();
            }
        });

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
            lmStudioService.resultSQL(selectionSchemaGUI);
            String response = lmStudioService.getOutput();

            this.conversationHistory += "<b>Bot:</b> " + response + "<br><br>";

            this.output.setText("<html>" + conversationHistory + "</html>");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void returnGUI() throws IOException, InterruptedException {
        jFrame.setVisible(false);
        ChooseLLMService chooseLLMService = new ChooseLLMService();
        new MainGUI(dataBaseService, chooseLLMService);
    }

    private Border createRoundedBorder(int radius) {
        return new CompoundBorder(new RoundedBorder(radius), BorderFactory.createEmptyBorder(0, 5, 0, 5));
    }

    class RoundedBorder implements Border {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
