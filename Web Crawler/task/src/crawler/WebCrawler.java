package crawler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebCrawler extends JFrame {

    private DefaultTableModel model = new DefaultTableModel();
    private Map<String, String> linksMap = new HashMap<>();

    private JTable table;
    private JScrollPane scrollTable;
    private JTextField urlTextField;
    private JButton getText;
    private JLabel title;
    private JLabel titleInfo;
    private JTextField exportText;
    private JButton exportButton;

    public WebCrawler() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setResizable(false);
        setTitle("Web Crawler");
        setLocationRelativeTo(null);
        setLayout(null);

        urlTextField = new JTextField();
        urlTextField.setBounds(20, 20, 650, 20);
        urlTextField.setName("UrlTextField");
        add(urlTextField);

        getText = new JButton();
        getText.setBounds(680, 20, 90, 20);
        getText.setText("PARSE!");
        getText.setName("RunButton");
        add(getText);
        getText.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                model.setRowCount(0);

                String sourceUrl = urlTextField.getText();
                String sourceCode = HttpRequest.getSourceCode(sourceUrl);
                String sourceTitle = Parser.getTitle(sourceCode);

                titleInfo.setText(sourceTitle);
                linksMap = Parser.getHrefs(sourceCode);

                linksMap.put(sourceUrl, sourceTitle);

                for (Map.Entry entry : linksMap.entrySet()) {
                    String val = (String) entry.getKey();
                    if (!val.contains("unavailablePage")) {
                           model.addRow(new String[]{(String) entry.getKey(), (String) entry.getValue()});
                    }
                }

                table.setEnabled(true);
                //resultTable.setModel(model);

            }
        });

        title = new JLabel();
        title.setBounds(20, 50, 30, 10);
        title.setName("Title");
        title.setText("Title:");
        add(title);

        titleInfo = new JLabel();
        titleInfo.setBounds(60, 50, 700, 10);
        titleInfo.setName("TitleLabel");
        add(titleInfo);

        table = new JTable();
        table.setBounds(20, 70, 740, 540);
        table.setName("TitlesTable");
        table.setEnabled(false);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFont(new Font("Verdana", Font.PLAIN, 12));
        add(table);

        scrollTable = new JScrollPane(table);
        scrollTable.setBounds(20, 70, 740, 530);
        scrollTable.setVisible(true);
        scrollTable.setPreferredSize(new Dimension());
        add(scrollTable);

        String[] columnNames = {"URL", "Title"};
        model.setColumnIdentifiers(columnNames);
        table.setModel(model);

        exportText = new JTextField();
        exportText.setBounds(20, 610, 650, 20);
        exportText.setName("ExportUrlTextField");
        add(exportText);

        exportButton = new JButton();
        exportButton.setBounds(680, 610, 80, 20);
        exportButton.setText("Export");
        exportButton.setName("ExportButton");
        exportButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                File file = new File(exportText.getText());


                try (FileWriter fileWriter = new FileWriter(file)) {

                    for (Map.Entry entry : linksMap.entrySet()) {

                        String val = (String) entry.getKey();
                        if (!val.contains("unavailablePage")) {
                            fileWriter.write(entry.getKey() + "\n" + entry.getValue() + "\n");
                            fileWriter.flush();
                        }
                    }

                } catch (IOException e) {

                }

            }
        });

        add(exportButton);

        setVisible(true);
    }

}