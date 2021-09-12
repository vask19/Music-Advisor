package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Controller {
    private static String authCode = null;
    static String accessToken = "";

    public static void getAccessToken(){
        View.printAccessTokenView();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SpotifyData.AUTH_SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"
                        +   "&code=" + authCode
                        +   "&client_id=" + SpotifyData.CLIENT_ID
                        +   "&client_secret=" + SpotifyData.CLIENT_SECRET
                        +   "&redirect_uri=" + SpotifyData.REDIRECT_URL
                )).build();
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            assert response != null;
            System.out.println(response.body());
            System.out.println( "\n---SUCCESS---");
    }catch (InterruptedException | IOException e) { System.out.println("Error response"); }
    }


    public static void getAuthCode(){
        try {
            View.printAuthView();
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String request;
                        if (query != null && query.contains("code")) {
                            authCode = query.substring(5);
                            System.out.println("code received");
                            System.out.println(authCode);
                            request = "Got the code. Return back to your program.";
                        } else {
                            request = "Authorization code not found. Try again.";
                        }
                        exchange.sendResponseHeaders(200, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                    });


            while (authCode == null) {
                Thread.sleep(100);
            }
            server.stop(5);

        } catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }

     }




}
