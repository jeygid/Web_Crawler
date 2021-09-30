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

    private JLabel elapsedTime;
    public static JLabel elapsedTimeCounter;

    private JLabel parsedPages;
    public static JLabel parsedPagesCounter;

    private JLabel parsingState;
    public static JLabel parsingStateText;

    public static JTable table;
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

                int count = 1;
                int depth = 1;

                if (!workersTextField.getText().matches("")) {
                    count = Integer.parseInt(workersTextField.getText());
                }

                if (!depthTextField.getText().matches("")) {
                    depth = Integer.parseInt(depthTextField.getText());
                }

                Workers worker = new Workers();
                worker.execute(urlTextField.getText(), count, depth);

                runButton.setSelected(false);
                model.setRowCount(0);

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
                    depthTextField.setText("");
                } else {
                    depthTextField.setEnabled(true);
                }
            }
        });


        elapsedTime = new JLabel();
        elapsedTime.setBounds(20, 110, 130, 20);
        elapsedTime.setName("ElapsedTime");
        elapsedTime.setText("Elapsed time (sec) :");
        add(elapsedTime);

        elapsedTimeCounter = new JLabel();
        elapsedTimeCounter.setBounds(160, 110, 150, 20);
        elapsedTimeCounter.setName("ElapsedTimeCounter");
        elapsedTimeCounter.setText("0");
        add(elapsedTimeCounter);


        parsedPages = new JLabel();
        parsedPages.setBounds(20, 140, 130, 20);
        parsedPages.setName("ParsedPagesLabel");
        parsedPages.setText("Parsed pages:");
        add(parsedPages);

        parsedPagesCounter = new JLabel();
        parsedPagesCounter.setBounds(160, 140, 150, 20);
        parsedPagesCounter.setName("ParsedLabel");
        parsedPagesCounter.setText("0");
        add(parsedPagesCounter);


        parsingState = new JLabel();
        parsingState.setBounds(20, 170, 130, 20);
        parsingState.setName("ParsingState");
        parsingState.setText("Parsing state:");
        add(parsingState);

        parsingStateText = new JLabel();
        parsingStateText.setBounds(160, 170, 150, 20);
        parsingStateText.setName("ParsingStateText");
        parsingStateText.setText("Waiting to run");
        add(parsingStateText);


        table = new JTable();
        table.setBounds(20, 200, 740, 390);
        table.setName("TitlesTable");
        table.setEnabled(false);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFont(new Font("Verdana", Font.PLAIN, 12));
        add(table);

        scrollTable = new JScrollPane(table);
        scrollTable.setBounds(20, 200, 740, 390);
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

                    for (Map.Entry entry : Workers.linksMap.entrySet()) {

                            fileWriter.write("Link: " + entry.getKey()
                                    + "\n" + "Title: " + entry.getValue() + "\n");

                    }

                    fileWriter.flush();

                } catch (IOException e) {

                }

            }
        });

        add(exportButton);

        setVisible(true);


    }


}