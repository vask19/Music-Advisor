package advisor;
import java.util.Scanner;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class test {



        private static final Scanner input = new Scanner(System.in);
        private static String authorizationServerUrl = "https://accounts.spotify.com";
        private static boolean isAuthorized = false;

        public static void main(String[] args) {

            if (args.length > 1 && args[0].equals("-access")) {
                authorizationServerUrl = args[1];
            }

            while (true) {
                String userInput = input.nextLine();

                if (userInput.equals("exit" )) {
                    System.out.println("---GOODBYE!---" );
                    return;
                } else if (userInput.equals("auth" ) || isAuthorized) {
                    menuOptions(userInput);
                } else {
                    System.out.println("Please, provide access for application." );
                }
            }
        }

        private static void menuOptions(String userInput) {
            switch (userInput) {
                case "new":
                    getNewReleases();
                    break;
                case "featured":
                    getFeatures();
                    break;
                case "auth":
                    getAuthorized();
                    break;
                case "categories":
                    getCategories();
                    break;
                case "playlists Mood":
                    getPlaylists();
                    break;
                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        private static void getAuthorized() {
            Authorization auth = new Authorization();
            auth.authorize(authorizationServerUrl);
            auth.getAccessToken(authorizationServerUrl);
            isAuthorized = true;
        }

        private static void getNewReleases() {
            System.out.println("---NEW RELEASES---");
            System.out.println("Mountains [Sia, Diplo, Labrinth]");
            System.out.println("Runaway [Lil Peep]");
            System.out.println("The Greatest Show [Panic! At The Disco]");
            System.out.println("All Out Life [Slipknot]");
        }

        private static void getFeatures() {
            System.out.println("---FEATURED---");
            System.out.println("Mellow Morning");
            System.out.println("Wake Up and Smell the Coffee");
            System.out.println("Monday Motivation");
            System.out.println("Songs to Sing in the Shower");
        }

        private static void getCategories() {
            System.out.println("---CATEGORIES---");
            System.out.println("Top Lists");
            System.out.println("Pop");
            System.out.println("Mood");
            System.out.println("Latin");
        }

        private static void getPlaylists() {
            System.out.println("---MOOD PLAYLISTS---");
            System.out.println("Walk Like A Badass");
            System.out.println("Rage Beats");
            System.out.println("Arab Mood Booster");
            System.out.println("Sunday Stroll");
        }
    }



 class Authorization {
    private static final String CLIENT_ID = "409ad95019b34181ad22ed97e9eddf47";
    private static final String CLIENT_SECRET = "b65db44a485d49159a86247095e31ee4";
    private static final String REDIRECT_URI = "http://localhost:8080";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE = "code";
    private static String authorizationCode;

    protected void authorize(String authorizationServerUrl) {
        System.out.println("use this link to request the access code:");
        System.out.println(authorizationServerUrl
                + "/authorize"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE);
        getRequest();
    }

    private void getRequest() {
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String request;
                        if (query != null && query.contains("code")) {
                            authorizationCode = query.substring(5);
                            System.out.println("code received");
                            System.out.println(authorizationCode);
                            request = "Got the code. Return back to your program.";
                        } else {
                            request = "Authorization code not found. Try again.";
                        }
                        exchange.sendResponseHeaders(200, request.length());
                        exchange.getResponseBody().write(request.getBytes());
                        exchange.getResponseBody().close();
                    });

            System.out.println("waiting for code...");
            while (authorizationCode == null) {
                Thread.sleep(100);
            }
            server.stop(5);

        } catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }
    }

    protected void getAccessToken(String accessPoint) {
        System.out.println("making http request for access_token...");
        System.out.println("response:");
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accessPoint + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=" + GRANT_TYPE
                                + "&code=" + authorizationCode
                                + "&client_id=" + CLIENT_ID
                                + "&client_secret=" + CLIENT_SECRET
                                + "&redirect_uri=" + REDIRECT_URI))
                .build();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assert response != null;
            System.out.println(response.body());
            System.out.println( "\n---SUCCESS---");

        } catch (InterruptedException | IOException e) { System.out.println("Error response"); }
    }
}
