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


/** Осталось:

 1) Парсинг всех ссылок и разделить обработку между потоками (очередь страниц на обработку)
 2) Добавить глубину
 3) Добавить лимит времени
 4) Добавить всех спарсенных страниц
 5) Проверить весь функционал, отрефакторить явные дикости в коде

 */

public class WebCrawler extends JFrame {

    public static DefaultTableModel model = new DefaultTableModel();
    private Map<String, String> linksMap = new HashMap<>();

    private JLabel startUrl;
    private JTextField urlTextField;
    private JToggleButton runButton;

    private JLabel workers;
    private JTextField workersTextField;

    private JLabel maxDepth;
    private JTextField depthTextField;
    private JCheckBox depthCheckbox;

    private JLabel timeLimit;
    private JTextField timeLimitTextField;
    private JCheckBox timeLimitCheckbox;

    private JLabel elapsedTime;
    public static JLabel elapsedTimeCounter;

    private JLabel parsedPages;
    public static JLabel parsedPagesCounter;

    private JLabel parsingState;
    public static JLabel parsingStateText;

    private JTable table;
    private JScrollPane scrollTable;

    private JTextField exportText;
    private JButton exportButton;

    private static Timer timer;

    public WebCrawler() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setResizable(false);
        setTitle("Web Crawler");
        setLocationRelativeTo(null);
        setLayout(null);

        startUrl = new JLabel();
        startUrl.setBounds(20, 20, 130, 20);
        startUrl.setName("StartUrl");
        startUrl.setText("Start URL:");
        add(startUrl);

        urlTextField = new JTextField();
        urlTextField.setBounds(160, 20, 500, 20);
        urlTextField.setName("UrlTextField");
        add(urlTextField);

        runButton = new JToggleButton();
        runButton.setBounds(680, 20, 90, 20);
        runButton.setText("Run!");
        runButton.setName("RunButton");
        add(runButton);
        runButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Workers worker = new Workers();
                int count = 1;

                WebCrawler.parsedPagesCounter.setText("0");

                if (!workersTextField.getText().equals("")) {
                    count = Integer.parseInt(workersTextField.getText());
                }

                worker.execute(urlTextField.getText(), count);

                runButton.setSelected(false);

                model.setRowCount(0);

//                String sourceUrl = urlTextField.getText();


//
//                String sourceUrl = urlTextField.getText();
//                String sourceCode = HttpRequest.getSourceCode(sourceUrl);
//                String sourceTitle = Parser.getTitle(sourceCode);
//
//                linksMap = Parser.getHrefsAndTitles(sourceCode);
//
//                linksMap.put(sourceUrl, sourceTitle);
//
//                for (Map.Entry entry : linksMap.entrySet()) {
//                    String val = (String) entry.getKey();
//                    if (!val.contains("unavailablePage")) {
//                        model.addRow(new String[]{(String) entry.getKey(), (String) entry.getValue()});
//                    }
//                }

                //table.setEnabled(true);

            }
        });

        workers = new JLabel();
        workers.setBounds(20, 50, 150, 20);
        workers.setName("Workers");
        workers.setText("Workers:");
        add(workers);

        workersTextField = new JTextField();
        workersTextField.setBounds(160, 50, 610, 20);
        workersTextField.setName("WorkersTextField");
        add(workersTextField);


        maxDepth = new JLabel();
        maxDepth.setBounds(20, 80, 130, 20);
        maxDepth.setName("MaxDepthLabel");
        maxDepth.setText("Maximum depth:");
        add(maxDepth);

        depthTextField = new JTextField();
        depthTextField.setBounds(160, 80, 500, 20);
        depthTextField.setName("DepthTextField");
        depthTextField.setEnabled(true);
        add(depthTextField);

        depthCheckbox = new JCheckBox();
        depthCheckbox.setBounds(690, 80, 150, 20);
        depthCheckbox.setName("DepthCheckBox");
        depthCheckbox.setSelected(true);
        depthCheckbox.setText("Enabled");
        add(depthCheckbox);
        depthCheckbox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (depthTextField.isEnabled()) {
                    depthTextField.setEnabled(false);
                } else {
                    depthTextField.setEnabled(true);
                }
            }
        });

        timeLimit = new JLabel();
        timeLimit.setBounds(20, 110, 130, 20);
        timeLimit.setName("TimeLimit");
        timeLimit.setText("Time limit (sec):");
        add(timeLimit);

        timeLimitTextField = new JTextField();
        timeLimitTextField.setBounds(160, 110, 500, 20);
        timeLimitTextField.setName("timelimit");
        timeLimitTextField.setEnabled(true);
        add(timeLimitTextField);

        timeLimitCheckbox = new JCheckBox();
        timeLimitCheckbox.setBounds(690, 110, 150, 20);
        timeLimitCheckbox.setName("timeLimitCheckbox");
        timeLimitCheckbox.setSelected(true);
        timeLimitCheckbox.setText("Enabled");
        add(timeLimitCheckbox);
        timeLimitCheckbox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (timeLimitTextField.isEnabled()) {
                    timeLimitTextField.setEnabled(false);
                } else {
                    timeLimitTextField.setEnabled(true);
                }
            }
        });

        elapsedTime = new JLabel();
        elapsedTime.setBounds(20, 140, 130, 20);
        elapsedTime.setName("ElapsedTime");
        elapsedTime.setText("Elapsed time (sec) :");
        add(elapsedTime);

        elapsedTimeCounter = new JLabel();
        elapsedTimeCounter.setBounds(160, 140, 150, 20);
        elapsedTimeCounter.setName("ElapsedTimeCounter");
        elapsedTimeCounter.setText("0");
        add(elapsedTimeCounter);


        parsedPages = new JLabel();
        parsedPages.setBounds(20, 170, 130, 20);
        parsedPages.setName("ParsedLabel");
        parsedPages.setText("Parsed pages:");
        add(parsedPages);

        parsedPagesCounter = new JLabel();
        parsedPagesCounter.setBounds(160, 170, 150, 20);
        parsedPagesCounter.setName("ParsedPagesCounter");
        parsedPagesCounter.setText("0");
        add(parsedPagesCounter);


        parsingState = new JLabel();
        parsingState.setBounds(20, 200, 130, 20);
        parsingState.setName("ParsingState");
        parsingState.setText("Parsing state:");
        add(parsingState);

        parsingStateText = new JLabel();
        parsingStateText.setBounds(160, 200, 150, 20);
        parsingStateText.setName("ParsingStateText");
        parsingStateText.setText("Waiting to run");
        add(parsingStateText);


        table = new JTable();
        table.setBounds(20, 230, 740, 350);
        table.setName("TitlesTable");
        table.setEnabled(false);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFont(new Font("Verdana", Font.PLAIN, 12));
        add(table);

        scrollTable = new JScrollPane(table);
        scrollTable.setBounds(20, 230, 740, 350);
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