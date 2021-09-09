package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Controller {
    private static String authCode = "";
    private static String  accessToken = "";

    public static void main(String[] args) throws IOException, InterruptedException {
        gAuthCode();
        Scanner sc = new Scanner(System.in);
        authCode = sc.nextLine();
        getAccessToken();
        System.out.println(accessToken);
    }



    public static void gAuthCode() throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.createContext("/",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        String hello = "hello, world";
                        exchange.sendResponseHeaders(200, hello.length());
                        exchange.getResponseBody().write(hello.getBytes());
                        exchange.getResponseBody().close();
                        String query = exchange.getRequestURI().getQuery();
                        Pattern pattern = Pattern.compile("code=\\.*");

                        if (pattern.matcher(query).find()){

                            System.out.println("true");
                            System.out.println(query);
                        }
                        else System.out.println(false);
                        server.stop(1);
                    }
                }
        );
        server.start();

    }



    public static void getAccessToken() throws IOException, InterruptedException {
        View.printAccessToken();
        HttpRequest requestAccessToken = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(
                        "client_id=" + SpotifyData.CLIENT_ID
                                + "&client_secret=" + SpotifyData.CLIENT_SECRET
                                + "&grant_type=" + "authorization_code"
                                + "&code=" + authCode
                                + "&redirect_uri=" + SpotifyData.REDIRECT_URL_IN_QUERY_STRING))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SpotifyData.GET_ACCESS_TOKEN_FROM_URL))
                .build();


        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpResponse<String> responseWithAccessToken = httpClient.send(requestAccessToken, HttpResponse.BodyHandlers.ofString());
        accessToken = responseWithAccessToken.body();
    }
}
