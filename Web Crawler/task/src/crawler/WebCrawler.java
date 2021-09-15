package crawler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler extends JFrame {

    private JPanel panel;
    private JTextField urlTextField;
    private JButton getText;
    private JTable table;
    private JScrollPane scrollTable;
    private JLabel title;
    private JLabel titleInfo;

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

                String sourceUrl = urlTextField.getText();
                String sourceCode = HttpRequest.get(sourceUrl);
                //table.setText(sourceCode);

                Pattern pattern = Pattern.compile("<title>[\\S ]+</title>");
                Matcher matcher = pattern.matcher(sourceCode);
                String title = "";
                if (matcher.find()) {
                    title = matcher.group();
                    title = title.replaceAll("<[/]?title>", "");
                    titleInfo.setText(title);
                }

            }
        });

        title = new JLabel();
        title.setBounds(20, 50, 30, 10);
        title.setName("Title");
        title.setText("Title:");
        panel.add(title);

        titleInfo = new JLabel();
        titleInfo.setBounds(60, 50, 200, 10);
        titleInfo.setName("TitleLabel");
        panel.add(titleInfo);

        table = new JTable();
        table.setBounds(20, 70, 740, 580);
        table.setName("TitlesTable");
        table.setEnabled(false);
        //textArea.setLineWrap(true);
        table.setFont(new Font("Verdana", Font.PLAIN, 12));
        panel.add(table);

        scrollTable = new JScrollPane(table);
        scrollTable.setBounds(20, 70, 740, 580);
        scrollTable.setVisible(true);
        scrollTable.setPreferredSize(new Dimension());
        add(scrollTable);
        panel.add(scrollTable);

        setVisible(true);
    }
}