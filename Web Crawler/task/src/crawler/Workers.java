package crawler;

import java.util.HashMap;
import java.util.Map;

public class Workers {

    static Map<String, String> linksMap = new HashMap<>();
    static String URL;
    private static int count;

    public void execute(String startURL, int count) {

        WebCrawler.timerStart();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        URL = startURL;
//
//        Worker worker = new Worker();
//        Thread workerThread = new Thread(worker);
//
//        workerThread.start();

        WebCrawler.timerStop();

    }

}

class Worker implements Runnable {

    @Override
    public void run() {

            String sourceCode = HttpRequest.getSourceCode(Workers.URL);
            String sourceTitle = Parser.getTitle(sourceCode);

            Workers.linksMap = Parser.getHrefs(sourceCode);

            Workers.linksMap.put(Workers.URL, sourceTitle);

            for (Map.Entry entry : Workers.linksMap.entrySet()) {
                String val = (String) entry.getKey();
                if (!val.contains("unavailablePage")) {
                    WebCrawler.model.addRow(new String[]{(String) entry.getKey(), (String) entry.getValue()});

                    int pagesCount = Integer.parseInt(WebCrawler.parsedPagesCounter.getText());
                    System.out.println(pagesCount);

                    WebCrawler.parsedPagesCounter.setText(String.valueOf(++pagesCount));

                }
            }

    }
}
