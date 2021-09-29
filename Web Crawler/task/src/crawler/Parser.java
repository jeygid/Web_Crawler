package crawler;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static String getTitle(String link) {

        String sourceCode = HttpRequest.getSourceCode(link);

        Pattern titlePattern = Pattern.compile("<title>[\\S ]+</title>");
        Matcher titleMatcher = titlePattern.matcher(sourceCode);

        String title = "";

        if (titleMatcher.find()) {
            title = titleMatcher.group();
            title = title.replaceAll("<[/]?title>", "");
        }

        return title;

    }

    public static ArrayList<String> getHrefs(String basicTitle) {

        String sourceCode = HttpRequest.getSourceCode(basicTitle);
        ArrayList<String> links = new ArrayList<>();
        Pattern hrefPattern = Pattern.compile("href=[\"']http[s]?[\\S]+[\"']");
        Matcher hrefMatcher = hrefPattern.matcher(sourceCode);
        String href = "";

        while (hrefMatcher.find()) {

            href = hrefMatcher.group();
            System.out.println(href);
            href = href.replaceAll("(href=[\"']|[\"']|)", "");
            String hrefSourceCode = HttpRequest.getSourceCode(href);
            links.add(href);
            WebCrawler.parsedPagesCounter.setText(String.valueOf(Integer.parseInt(WebCrawler.parsedPagesCounter.getText()) + 1));

        }

        return links;

    }

    public static Map<String, String> getDeepHrefsAndTitles(String basicTitle, int depth) {

        if (depth < 2) return null;

        ArrayList<String> result = new ArrayList<>();

        ArrayList<String> links = getHrefs(basicTitle);

        for (int i = 1; i < depth; i++) {



        }

        return null;
    }

}
