package advisor;

import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
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
        Scanner scanner = new Scanner(System.in);
        Controller.getAuthCode();
        Controller.getAccessToken();
        Stack<String > answers = new Stack<>();
        SortedMap<String , Integer> map= new TreeMap<>();

        int sw  = 5;
        String playlistName = "";
        int page;
        while (true) {

            String answer = scanner.nextLine();
            answers.add(answer);

            if (answer.equals("next")){

                answers.pop();
                answer = answers.peek();
                map.put(answer,map.get(answer) == null ? 2 : map.get(answer) +1);


            }
            if (map.get(answer) != null){
                page = map.get(answer);
            }
            else page = 1;


            if (answer.contains("playlists")) {
                playlistName = answer.substring(10);
                answer = "playlists";
            }
            System.out.println(map);
            switch (answer) {
                case "next":

                    break;
                case "exit":

                    return;
                case "new":

                    break;
                case "auth":

                    break;
                case "featured":

                    break;
                case "categories":
                    View.printCategories(Controller.categories(),page,sw);

                    break;
                case "playlists":
                    View.printCategories(Controller.getPlaylistInPage(playlistName),page,sw);

            }
        }














    }
}

