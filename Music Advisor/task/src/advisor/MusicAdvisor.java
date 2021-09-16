package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MusicAdvisor implements Advisor{

    public static  String REST_PATH_ALL_CATEGORIES =  SpotifyData.API_PATH + "/v1/browse/categories";
    public static  String REST_PATH_PLAYLISTS = SpotifyData.API_PATH + "/v1/browse/categories/category_id/playlists";
    public static String REST_PATH_NEW_RELEASES = SpotifyData.API_PATH+ "/v1/browse/new-releases";
    public static  String REST_PATH_FEATURED_PLAYLISTS = SpotifyData.API_PATH + "/v1/browse/featured-playlists";

    @Override
    public String newAlbums() {
        JsonObject response = Controller.restRequestForSpotify(URI.create(REST_PATH_NEW_RELEASES));
        assert response != null;
        View.printNewAlbums(response.get("albums").getAsJsonObject());

        return null;
    }

    @Override
    public String featured() {
        JsonObject response = Controller.restRequestForSpotify(URI.create(REST_PATH_FEATURED_PLAYLISTS));
        assert response != null;
        View.printFeatured(response.get("playlists").getAsJsonObject());
        return null;
    }

    @Override
    public String categories() {
        System.out.println(REST_PATH_ALL_CATEGORIES);
        JsonObject response = Controller.restRequestForSpotify(URI.create(REST_PATH_ALL_CATEGORIES));
        assert response != null;
        View.printCategories(response.get("categories").getAsJsonObject());
        return " ";
    }

    @Override
    public String playlists(String nameOfCategory) {
        JsonObject allCategories = Controller.restRequestForSpotify(URI.create(REST_PATH_ALL_CATEGORIES));
        Map<String ,String > playlistsId = new HashMap<>();
        assert allCategories != null;
        for (JsonElement item : allCategories.get("categories").getAsJsonObject().getAsJsonArray("items")){
            playlistsId.put(item.getAsJsonObject().get("name").getAsString(),
                    item.getAsJsonObject().get("id").getAsString());
        }

        if (playlistsId.containsKey(nameOfCategory)){

            String uri = REST_PATH_PLAYLISTS.replaceFirst("category_id",playlistsId.get(nameOfCategory));
            JsonObject playlists = Controller.restRequestForSpotify(URI.create(uri));

            assert playlists != null;
            if (playlists.has("error")){
                System.out.println("Specified id doesn't exist");
                System.out.println(playlists);
                return null;
              //  System.out.println("{\"error\":{\"status\":404,\"message\":\"Specified id doesn't exist\"}}");
            }
            else View.printPlaylists(playlists.get("playlists").getAsJsonObject());
        }
        else System.out.println("Unknown category name.");



       // "Unknown category name."
        return null;
    }

    @Override
    public String exit() {
        System.out.println("---GOODBYE!---");
        return "---GOODBYE!---";
    }


    public String Rplaylists(String categoryName)  {
        String categoryID = getCategoryId(categoryName);
        if (categoryID == null) {
            View.printText("Unknown category name.");
        }
        else {
            String playlistsURL = SpotifyData.API_PATH + "v1/browse/categories/" + categoryID + "/playlists";
            JsonObject allCategories = Controller.restRequestForSpotify(URI.create(playlistsURL));
            View.printPlaylists(allCategories.get("playlists").getAsJsonObject());


        }
        return null;
    }

    private  String getCategoryId(String categoryName){
        JsonObject allCategories = Controller.restRequestForSpotify(URI.create(REST_PATH_ALL_CATEGORIES));
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
}
