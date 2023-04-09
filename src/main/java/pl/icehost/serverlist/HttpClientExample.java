package pl.icehost.serverlist;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClientExample {
    public static void main(String[] args) throws Exception {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(25050), 0);
            server.createContext("/example", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String request = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("Received request: " + request);
                    String response = "This is the response message";
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            });
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port "+server.getAddress().getPort());
        } catch (IOException ignored) {
        }

/*
        URL url = new URL("http://localhost:25050/example");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response body: " + response.toString());

 */
    }
}