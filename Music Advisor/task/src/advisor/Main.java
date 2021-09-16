package advisor;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-access")) {
                SpotifyData.AUTH_SERVER_PATH = args[i + 1];

            }
            if (args[i].equals("-resource")) {
                SpotifyData.API_PATH = args[i + 1];

            }
        }
        Advisor advisor = new MusicAdvisor();
        AuthMusicAdvisorDecorator advisorDecorator = new AuthMusicAdvisorDecorator(advisor);
        Scanner scanner = new Scanner(System.in);
        String result = "";

       /* advisorDecorator.newAlbums();
        advisorDecorator.auth();
        advisorDecorator.newAlbums();
        advisorDecorator.featured();
        advisorDecorator.categories();
        advisorDecorator.playlists("Sleep");
        advisor.exit();*/


        while (true) {
            String answer = scanner.nextLine();
            String playlistName = "";
            if (answer.contains("playlists")){
                playlistName = answer.substring(10);

                answer = "playlists";

            }

            switch (answer) {
                case "exit":
                    result = advisorDecorator.exit();
                    return;
                case "new":
                    advisorDecorator.newAlbums();
                    break;
                case "auth":
                    advisorDecorator.auth();
                    break;
                case "featured":
                    result = advisorDecorator.featured();
                    break;
                case "categories":
                    advisorDecorator.categories();
                    break;
                case "playlists":
                    advisorDecorator.playlists(playlistName);
            }


        }


    }
}

