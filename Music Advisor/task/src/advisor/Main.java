package advisor;

import java.io.IOException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;


public class Main {
    private static boolean ifAuth = false;
    public static void main(String[] args) throws IOException, InterruptedException {
        int sw  = 2;
        int page;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-access")) {
                SpotifyData.AUTH_SERVER_PATH = args[i + 1];

            }
            if (args[i].equals("-resource")) {
                SpotifyData.API_PATH = args[i + 1];

            }
            if (args[i].equals("-page")) {
                sw = Integer.parseInt(args[i + 1]);

            }
        }
        Scanner scanner = new Scanner(System.in);
        Stack<String > answers = new Stack<>();
        SortedMap<String , Integer> map= new TreeMap<>();


        String playlistName = "";

        while (true) {
            String answer = scanner.nextLine();
            answers.add(answer);
            if (answer.equals("next")){
                answers.pop();
                answer = answers.peek();
                map.put(answer,map.get(answer) == null ? 2 : map.get(answer) +1);
                if (map.get(answer) > View.getMaxPage()){
                    System.out.println("No more pages.");
                    map.put(answer,View.getMaxPage());}

            }
            if (answer.equals("prev")){

                answers.pop();
                answer = answers.peek();
                if (map.get(answer) == null || map.get(answer) == 1){
                    System.out.println("No more pages.");
                    map.putIfAbsent(answer, 1);
                    continue;
                }
                else  map.put(answer,map.get(answer) > 1 ? map.get(answer) -1 : 1 );

            }

            if (map.get(answer) != null){
                page = map.get(answer);
            }
            else page = 1;


            if (answer.contains("playlists")) {
                playlistName = answer.substring(10);
                answer = "playlists";
            }



            switch (answer) {
                case "exit":
                    View.printExit();
                    return;
                case "new":
                    if (ifAuth)
                        View.printPages(Controller.newAlbums(),page,sw);
                    else View.printMessageIfNotAuth();
                    break;
                case "auth":
                    Controller.getAuthCode();
                    Controller.getAccessToken();
                    ifAuth = true;
                    break;
                case "featured":
                    if (ifAuth)
                        View.printPages(Controller.featured(),page,sw);
                    else View.printMessageIfNotAuth();
                    break;
                case "categories":
                    if (ifAuth)
                        View.printPages(Controller.categories(),page,sw);
                    else View.printMessageIfNotAuth();
                    break;
                case "playlists":
                    if (ifAuth)
                        View.printPages(Controller.getPlaylistInPage(playlistName),page,sw);
                    else View.printMessageIfNotAuth();

            }
        }














    }
}

