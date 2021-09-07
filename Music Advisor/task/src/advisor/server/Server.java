package advisor.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

public class Server {

    public static void main(String[] args) {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void start() throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.createContext("/",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        String hello = "hello, world";
                        exchange.sendResponseHeaders(200, hello.length());
                        exchange.getResponseBody().write(hello.getBytes());
                        exchange.getResponseBody().close();
                        String query = exchange.getRequestURI().getQuery();
                        Pattern pattern = Pattern.compile("code=\\.*");

                        if (pattern.matcher(query).find())
                            System.out.println("true");
                        else System.out.println(false);
                        server.stop(1);




                    }
                }

        );
        server.start();

    }

}
