package advisor;

public class SpotifyData {
    public static String AUTH_SERVER_PATH = "https://accounts.spotify.com";
    public static final String GET_ACCESS_TOKEN_FROM_URL = "https://accounts.spotify.com/api/token";
    public static  String REDIRECT_URL = "http://localhost:8080";
    public static final String CLIENT_SECRET = "b1a2411bbb91493383810b4c7edeaa11";
    public static String API_PATH = "https://api.spotify.com/";
    public static final String CLIENT_ID = "55baa53664b8400995154bb8f972fa92";
    public static final String REDIRECT_URL_IN_QUERY_STRING = "http%3A%2F%2Flocalhost%3A8080";

    public static final String REST_PATH_ALL_CATEGORIES =  "https://api.spotify.com/v1/browse/categories";
    public static final String REST_PATH_PLAYLISTS = "https://api.spotify.com/v1/browse/categories/{category_id}/playlists";
    public static final String REST_PATH_NEW_RELEASES = "https://api.spotify.com/v1/browse/new-releases";
    public static final String REST_PATH_FEATURED_PLAYLISTS = "https://api.spotify.com/v1/browse/featured-playlists";


   /* Чтобы получить все категории, используйте https://api.spotify.com/v1/browse/categories
    Для получения списка воспроизведения, используйте https://api.spotify.com/v1/browse/categories/{category_id}/playlists
    Для получения новых выпусков, используйте https://api.spotify.com/v1/browse/new-releases
    Для получения избранных списков воспроизведения, используйте https://api.spotify.com/v1/browse/featured-playlists
*/
}

//b1a2411bbb91493383810b4c7edeaa11
//https://accounts.spotify.com/authorize?client_id=55baa53664b8400995154bb8f972fa92&redirect_uri=http://localhost:8080&response_type=code