package advisor;

import java.util.List;
public class View {
    private static int max_page = 0;
    private static final String MESSAGE_IF_NOT_AUTH = "You not auth in application";


    public static void printMessageIfNotAuth(){
        System.out.println(MESSAGE_IF_NOT_AUTH);
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
    public static void printPages(List<String > categories, int page, int shownOnAPage){
        System.out.println();

        int start = page * shownOnAPage-shownOnAPage;
        int finish = start + shownOnAPage;

        if (start >= categories.size()){

            return;}
        for (int i = start;i< finish;i++){
            if (categories.get(i) != null)
                System.out.println(categories.get(i));
        }
        max_page = categories.size()/shownOnAPage;

        System.out.printf("---PAGE %d OF %d---",page,categories.size()/shownOnAPage);

    }

    public static int getMaxPage(){
        return max_page;
    }

    public static void  printExit() {
        System.out.println("---GOODBYE!---");

    }

}
