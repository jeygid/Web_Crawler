package crawler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class WebCrawler extends JFrame {

    private JPanel panel;
    private JTextField urlTextField;
    private JButton getText;
    private JTextArea textArea;
    private JScrollPane scrollTable;

    public WebCrawler() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setResizable(false);
        setTitle("Web Crawler");
        setLocationRelativeTo(null);
        setLayout(null);

        panel = new JPanel();
        panel.setBounds(0, 0, 800, 700);
        panel.setLayout(null);
        add(panel);

        urlTextField = new JTextField();
        urlTextField.setBounds(20, 20, 650, 20);
        urlTextField.setName("UrlTextField");
        panel.add(urlTextField);

        getText = new JButton();
        getText.setBounds(680, 20, 90, 20);
        getText.setText("Get text!");
        getText.setName("RunButton");
        panel.add(getText);
        getText.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String source = urlTextField.getText();
                textArea.setText(HttpRequest.get(source));



            }
        });

        textArea = new JTextArea();
        textArea.setBounds(20, 50, 740, 600);
        textArea.setName("HtmlTextArea");
        textArea.setText("HtmlTextArea");
        textArea.setEnabled(false);
        //textArea.setLineWrap(true);
        textArea.setFont(new Font("Verdana", Font.PLAIN, 12));
        panel.add(textArea);

        scrollTable = new JScrollPane(textArea);
        scrollTable.setBounds(20, 50, 740, 600);
        scrollTable.setVisible(true);
        scrollTable.setPreferredSize(new Dimension());
        add(scrollTable);
        panel.add(scrollTable);

        setVisible(true);
    }
}