package advisor;

public class View {


    public static void printText(String text){
        System.out.println(text);
    }

    public static void printAccessToken() {

        System.out.println("making http request for access_token...");
    }
    public static void printAccessToken(String accessToken) {
        System.out.println("response:");
        System.out.println(accessToken);
    }

    public static void printAuthView(){
        System.out.println("use this link to request the access code:");
        System.out.printf("%s/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                SpotifyData.AUTH_SERVER_PATH, SpotifyData.CLIENT_ID, SpotifyData.REDIRECT_URL);
        System.out.println();
        System.out.println("waiting for code...");
    }

    public static void printAccessTokenView() {
        System.out.println("making http request for access_token...");
    }

}
