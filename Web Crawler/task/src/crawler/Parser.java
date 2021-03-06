package crawler;

import java.util.ArrayList;
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
            href = href.replaceAll("(href=[\"']|[\"'])", "");
            links.add(href);

        }

        return links;

    }

    public static ArrayList<String> getDeepHrefsAndTitles(String basicTitle, int depth) {

        ArrayList<String> links = getHrefs(basicTitle);

        ArrayList<String> result = new ArrayList<>(links);

        ArrayList<String> intermediateLinks = new ArrayList<>();

        for (int i = 1; i <= depth; i++) {

            for (String link : links) {
                ArrayList<String> newLinks = getHrefs(link);
                intermediateLinks.addAll(newLinks);
            }

            result.addAll(intermediateLinks);
            links = new ArrayList<>(intermediateLinks);

        }

        return result;
    }

}
