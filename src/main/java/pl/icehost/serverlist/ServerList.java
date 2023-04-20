package pl.icehost.serverlist;

import com.squareup.okhttp.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.icehost.serverlist.Api.Api;
import pl.icehost.serverlist.Command.Reload;
import pl.icehost.serverlist.Config.Config;
import pl.icehost.serverlist.Interface.Trigger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class ServerList extends JavaPlugin {

    public final Nagroda nagroda = new Nagroda();

    private final Reload reload = new Reload(this);

    public static Api api;

    public static Api getApi(){
        return api;
    }

    public Api get(){
        return api;
    }

    private final ArrayList<String> offlinePlayers = new ArrayList<>();

    public void addOfflinePlayer(List<String> players){
        offlinePlayers.addAll(players);
    }

    public final Config config = new Config(this);

    private PrintWriter out;

    private BufferedReader in;

    public ArrayList<String> getOfflinePlayers() {
        return offlinePlayers;
    }

    @Override
    public void onEnable() {
        api=new Api(this);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://codebit.pl/api/v1/game_servers/minecraft/get/prizes")
                .addHeader("Authorization", "Bearer "+config.gettoken())
                .build();
        api.addTrigger(new Trigger() {
            @Override
            public void trigger(Integer interval, Player player) {
                nagroda.give(interval, player);
            }
        });
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.code()==401)return;
                    JSONArray obj = (JSONArray) new JSONParser().parse(response.body().string());
                    for (Object var2 : obj) {
                        JSONObject jsonObject = (JSONObject) var2;
                        Player player = Bukkit.getPlayer(jsonObject.get("player_nickname").toString());
                        if (player != null) {
                            if (player.isOnline()) {
                                for (Trigger var : api.getTriggers()) {
                                    var.trigger(Integer.parseInt(jsonObject.get("days").toString()), player);
                                }
                                send(jsonObject.get("transaction_hash").toString());
                            }
                        }
                    }
                } catch (IOException | ParseException ioException) {
                    ioException.printStackTrace();
                }
            }
        }.runTaskTimer(this, 20, 200);
        getCommand("reload").setExecutor(reload);
    }

    @Override
    public void onDisable() {
    }

    private void send(String key){
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            System.out.println(key);
            RequestBody body = RequestBody.create(mediaType, "{\n    \"transaction_hash\": \""+key+"\"\n}");
            Request request = new Request.Builder()
                    .url("https://codebit.pl/api/v1/game_servers/minecraft/claim/prize")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+config.gettoken())
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code()==404){
                System.out.println("Błąd nie znaleziono transaction_hash: "+key);
            }
            // 404 not found
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
