package crawler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static String getTitle(String sourceCode) {

        Pattern titlePattern = Pattern.compile("<title>[\\S ]+</title>");
        Matcher titleMatcher = titlePattern.matcher(sourceCode);

        String title = "";

        if (titleMatcher.find()) {
            title = titleMatcher.group();
            title = title.replaceAll("<[/]?title>", "");
        }

        return title;

    }

    public static Map<String, String> getHrefs(String sourceCode) {

        Map<String, String> linksMap = new HashMap<>();
        Pattern hrefPattern = Pattern.compile("href=[\"']http[s]?[\\S]+[\"']");
        Matcher hrefMatcher = hrefPattern.matcher(sourceCode);
        String href = "";

        while (hrefMatcher.find()) {

            href = hrefMatcher.group();
            System.out.println(href);
            href = href.replaceAll("(href=[\"']|[\"']|)", "");
            String hrefSourceCode = HttpRequest.getSourceCode(href);
            String title = getTitle(hrefSourceCode);
            linksMap.put(href, title);
            WebCrawler.parsedPagesCounter.setText(String.valueOf(Integer.parseInt(WebCrawler.parsedPagesCounter.getText()) + 1));
        }

        return linksMap;

    }

}
