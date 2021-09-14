package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;

public class MusicAdvisor implements Advisor{

    @Override
    public String newAlbums() {
        JsonObject response = Controller.restRequestForSpotify(URI.create(SpotifyData.REST_PATH_NEW_RELEASES));
        assert response != null;
        View.printNewAlbums(response.get("albums").getAsJsonObject());

        return null;
    }

    @Override
    public String featured() {
        JsonObject response = Controller.restRequestForSpotify(URI.create(SpotifyData.REST_PATH_FEATURED_PLAYLISTS));
        assert response != null;
        View.printFeatured(response.get("playlists").getAsJsonObject());
        return null;
    }

    @Override
    public String categories() {
        JsonObject response = Controller.restRequestForSpotify(URI.create(SpotifyData.REST_PATH_ALL_CATEGORIES));
        assert response != null;
        View.printCategories(response.get("categories").getAsJsonObject());
        return null;
    }

    @Override
    public String playlists(String nameOfCategory) {
        return "---MOOD PLAYLISTS---\n" +
                "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll";
    }

    @Override
    public String exit() {
        return "---GOODBYE!---";
    }
}
