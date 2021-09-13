package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;

public class MusicAdvisor implements Advisor{

    @Override
    public String newAlbums() {
        JsonObject js = Controller.getNewAlbums();
        View.printNewAlbums(js);
        return null;
    }

    @Override
    public String featured() {
        return "---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower";
    }

    @Override
    public String categories() {
        return "---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin";
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
