package pl.icehost.serverlist;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public final class ServerList extends JavaPlugin {

    public final Nagroda nagroda = new Nagroda();

    public static Api api;

    public static Api getApi(){
        return api;
    }

    public final Config config = new Config(this);

    @Override
    public void onEnable() {
        api=new Api(this);
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(25050), 0);
            server.createContext("/example", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String response = "This is the response message";
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    if (api.getTrigger()!=null) api.getTrigger().trigger("rex89m");
                }
            });
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port "+server.getAddress().getPort());
        } catch (IOException ignored) {
        }
    }

    @Override
    public void onDisable() {
    }
}
