package advisor;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Controller {
    private static String authCode = "";
    static String accessToken = "";
    public static void getAccessToken() throws IOException, InterruptedException {
        View.printAccessTokenView();
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
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> responseWithAccessToken = client.send(requestAccessToken, HttpResponse.BodyHandlers.ofString());
        accessToken = JsonParser.parseString(responseWithAccessToken.body()).getAsJsonObject().get("access_token").getAsString();
        System.out.println(responseWithAccessToken.body());
    }


    public static void getAuthCode() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.start();
        View.printAuthView();
        server.createContext("/",
                exchange -> {
                    String query = exchange.getRequestURI().getQuery();
                    String result;
                    if (query != null && query.contains("code")) {
                        authCode = query.substring(5);
                        result = "Got the code. Return back to your program.";
                    } else {
                        result = "Not found authorization code. Try again.";
                    }
                    exchange.sendResponseHeaders(200, result.length());
                    exchange.getResponseBody().write(result.getBytes());
                    exchange.getResponseBody().close();
                    View.printText(result);
                }
        );
        while (authCode.equals("")) {
            Thread.sleep(10);
        }
        server.stop(10);
    }



}
