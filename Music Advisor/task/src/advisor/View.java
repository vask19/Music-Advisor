package advisor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
public class View {


    public static void printText(String text){
        System.out.println(text);
    }


    public static void printAccessToken(String accessToken) {
        System.out.println("response:");

    }

    public static void printAuthView(){
        System.out.println();
        System.out.println("use this link to request the access code:");
        System.out.printf("%s/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                SpotifyData.AUTH_SERVER_PATH, SpotifyData.CLIENT_ID, SpotifyData.REDIRECT_URL);
        System.out.println();
        System.out.println("waiting for code...");
    }

    public static void printAccessTokenView() {
        System.out.println();

        System.out.println("making http request for access_token...");
        System.out.println("response:");
    }


    public static void printNewAlbums(JsonObject js){
        System.out.println();

        for (JsonElement item : js.getAsJsonArray("items")){
            List<String > artists = new ArrayList<>();
            String albumName = item.getAsJsonObject().get("name").getAsString();
            String  href = null;

            for (JsonElement artist :item.getAsJsonObject().getAsJsonArray("artists")){
                href = item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();
                artists.add(artist.getAsJsonObject().get("name").getAsString());
            }
            System.out.println(albumName + "\n" + artists + "\n" + href + "\n");
        }
    }

    public static void  printCategories(List<String > categories,int page,int shownOnAPage){
        System.out.println();

        int start = page * shownOnAPage-shownOnAPage;
        int finish = start + shownOnAPage;

        if (start >= categories.size())
            return;
        for (int i = start;i< finish;i++){
            if (categories.get(i) != null)
                System.out.println(categories.get(i));
        }

        System.out.printf("---PAGE %d OF %d---",page,categories.size()/shownOnAPage);

    }











    public static void printFeatured(JsonObject js){
        System.out.println();
        for (JsonElement item :js.getAsJsonArray("items")){
            String playlistName = item.getAsJsonObject().get("name").getAsString();
            String href = item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString();

            if (href != null && playlistName != null);
                System.out.println(playlistName + "\n" + href + "\n");


        }
    }
    public static void printPlaylists(JsonObject playlists){
        System.out.println();

        for (JsonElement item :playlists.getAsJsonArray("items")){
            System.out.println(item.getAsJsonObject().get("name").getAsString() +
                    "\n" + item.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString() + "\n");
        }

    }

}
