package crawler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static crawler.WebCrawler.parsingStateText;
import static crawler.Workers.URL;

public class Workers {

    static List<SwingWorker> workersList = new ArrayList<>();

    static Map<String, String> linksMap = new HashMap<>();
    static String URL;
    private static int workersCount;
    private static boolean timerActive = true;

    public void execute(String startURL, int count) {

        URL = startURL;
        workersCount = count;

        SwingWorker<Void, Void> timer = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {

                int i = 0;
                timerActive = true;
                parsingStateText.setText("Running");

                while (timerActive) {

                    WebCrawler.elapsedTimeCounter.setText(String.valueOf(i));
                    i++;

                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                return null;

            }
        };

        timer.execute();

        for (int i = 1; i <= count; i++) {
           workersList.add(new Bot(i));
        }

        for (SwingWorker thisBot : workersList) {
            thisBot.execute();
        }

        SwingWorker<Void, Void> checkThreads = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {


                while (true) {

                    int i = 0;

                    for (SwingWorker bot: workersList) {
                        if (bot.getState().toString().equals("DONE")) i++;
                        System.out.println(i);
                    }

                    if (i == workersList.size()) {
                        timerActive = false;
                        workersList.clear();
                        parsingStateText.setText("Done");
                        break;
                    }

                }

                return null;

            }
        };

        checkThreads.execute();






    }

}

class Bot extends SwingWorker<Void, Void> {

    int id;

    public Bot(int id) {
        this.id = id;
    }

    @Override
    public Void doInBackground() {

        String sourceCode = HttpRequest.getSourceCode(URL);
        String sourceTitle = Parser.getTitle(sourceCode);

        Workers.linksMap = Parser.getHrefs(sourceCode);

        Workers.linksMap.put(URL, sourceTitle);

        for (Map.Entry entry : Workers.linksMap.entrySet()) {
            String val = (String) entry.getKey();
            if (!val.contains("unavailablePage")) {
                WebCrawler.model.addRow(new String[]{(String) entry.getKey(), (String) entry.getValue()});
            }
        }

        return null;

    }


}


