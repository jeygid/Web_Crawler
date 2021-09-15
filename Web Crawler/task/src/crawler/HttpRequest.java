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

            response = client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response.body();

    }


}
