package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class HttpRequest {

    public static String get(String source) {

        HttpResponse<String> response = null;

        try {

            HttpClient client = HttpClient.newHttpClient();

            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(URI.create(source))
                    .GET()
                    .build();

            response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response.body();

    }

    public static String getURLSource(String url) {

        try {
            URL urlObject = new URL(url);
            URLConnection urlConnection;

            urlConnection = urlObject.openConnection();

            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            return toString(urlConnection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String toString(InputStream inputStream) throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {

            String inputLine;

            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }


}
