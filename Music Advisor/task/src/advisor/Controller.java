package advisor;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


public class Controller {
    private static String authCode = null;
    static String accessToken = "";
    public static  String REST_PATH_ALL_CATEGORIES =  SpotifyData.API_PATH + "/v1/browse/categories";
    public static  String REST_PATH_PLAYLISTS = SpotifyData.API_PATH + "/v1/browse/categories/category_id/playlists";
    public static String REST_PATH_NEW_RELEASES = SpotifyData.API_PATH+ "/v1/browse/new-releases";
    public static  String REST_PATH_FEATURED_PLAYLISTS = SpotifyData.API_PATH + "/v1/browse/featured-playlists";


    public static List<String> newAlbums() {
        JsonObject response =restRequestForSpotify(URI.create(REST_PATH_NEW_RELEASES));
        List<String> pages = new ArrayList<>();
        assert response != null;
        for (JsonElement item : response.get("albums").getAsJsonObject().getAsJsonArray("items")) {
            List<String> artists = new ArrayList<>();
            String albumName = item.getAsJsonObject().get("name").getAsString();
            String href = null;

            for (JsonElement artist : item.getAsJsonObject().getAsJsonArray("artists")) {
                href = item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
                artists.add(artist.getAsJsonObject().get("name").getAsString());
            }
            pages.add(albumName + "\n" + artists + "\n" + href + "\n");
        }

        return pages;
    }


    public static List<String > featured() {
        JsonObject response = restRequestForSpotify(URI.create(REST_PATH_FEATURED_PLAYLISTS));
        List<String > pages = new ArrayList<>();
        assert response != null;
        for (JsonElement item :response.get("playlists").getAsJsonObject().getAsJsonArray("items")) {
            String playlistName = item.getAsJsonObject().get("name").getAsString();
            String href = item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();

            if (href != null && playlistName != null);
                pages.add(playlistName + "\n" + href + "\n");
        }

        return pages;
    }
    public static List<String > categories() {
        List<String > categories = new ArrayList<>();
        JsonObject response = restRequestForSpotify(URI.create(REST_PATH_ALL_CATEGORIES));
        assert response != null;
        for (JsonElement item : response.get("categories").getAsJsonObject().getAsJsonArray("items")) {
            categories.add(item.getAsJsonObject().get("name").getAsString());
        }
        return categories;
    }
    private static JsonObject  playlists(String nameOfCategory) {

        JsonObject allCategories = restRequestForSpotify(URI.create(REST_PATH_ALL_CATEGORIES));
        Map<String, String> playlistsId = new HashMap<>();
        assert allCategories != null;
        for (JsonElement item : allCategories.get("categories").getAsJsonObject().getAsJsonArray("items")) {
            playlistsId.put(item.getAsJsonObject().get("name").getAsString(),
                    item.getAsJsonObject().get("id").getAsString());
        }

        if (playlistsId.containsKey(nameOfCategory)) {

            String uri = REST_PATH_PLAYLISTS.replaceFirst("category_id", playlistsId.get(nameOfCategory));
            JsonObject playlists = Controller.restRequestForSpotify(URI.create(uri));

            assert playlists != null;
            if (playlists.has("error")) {
                System.out.println("Specified id doesn't exist");
                System.out.println(playlists);
                return null;
            } else {
                return playlists.get("playlists").getAsJsonObject();

            }
        }

        else System.out.println("Unknown category name.");

            return null;
        }


    public static List<String>  getPlaylistInPage(String nameOfCategory){
        JsonObject playlists = playlists(nameOfCategory);
        List<String > pages = new ArrayList<>();
        for (JsonElement item :playlists.getAsJsonArray("items")){
            String page = item.getAsJsonObject().get("name").getAsString() +
                    "\n" + item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString() + "\n";
            pages.add(page);
        }
        return pages;
    }

    private  String getCategoryId(String categoryName){
        JsonObject allCategories = restRequestForSpotify(URI.create(REST_PATH_ALL_CATEGORIES));
        Map<String ,String > playlistsId = new HashMap<>();
        assert allCategories != null;
        for (JsonElement item : allCategories.get("categories").getAsJsonObject().getAsJsonArray("items")){
            playlistsId.put(item.getAsJsonObject().get("name").getAsString(),
                    item.getAsJsonObject().get("id").getAsString());
        }
        if (playlistsId.equals(categoryName)) {
            return playlistsId.get(categoryName);
        }
        return null;
    }
    public static JsonObject restRequestForSpotify(URI uri){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization","Bearer " + accessToken)
                .uri(uri)
                .GET()
                .build();
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(httpRequest,HttpResponse.BodyHandlers.ofString());

            return  JsonParser.parseString(response.body()).getAsJsonObject();


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
       return null;

    }

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

            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            accessToken = jo.get("access_token").getAsString();
            assert response != null;

            System.out.print( "\n---SUCCESS---");
    }catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }

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
