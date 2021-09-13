package advisor;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        for(int i =0;i<args.length;i++){
            if(args[i].equals("-access")){
                SpotifyData.AUTH_SERVER_PATH = args[i + 1];
                break;
            }
        }
        Advisor advisor = new MusicAdvisor();
        AuthMusicAdvisorDecorator advisorDecorator = new AuthMusicAdvisorDecorator(advisor);
        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (true){
            String answer = scanner.nextLine();
            switch (answer){
                case "exit":
                    result = advisor.exit();
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
                    result = advisorDecorator.categories();
                    break;
                case "playlists":
                    break;
            }
            System.out.println(result);



        }


    }

}
