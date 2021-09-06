package advisor.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    public static void main(String[] args) {
        start();

    }
    
    public static void start(){
        HttpClient httpClient = HttpClient.newHttpClient();
        URI fakePostService = URI.create("https://jsonplaceholder.typicode.com/posts");
        String  bookDate = "{\"title\":\"The Invisible Man\", \"author\":\"H. G. Wells\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(fakePostService)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bookDate))
                .build();

        try {
            var response = httpClient
                    .send(request,HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode()); // 201 if everything is OK
            System.out.println(response.body());       // a JSON response with ID

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
