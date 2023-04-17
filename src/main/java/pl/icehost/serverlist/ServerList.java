package pl.icehost.serverlist;

import com.squareup.okhttp.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.icehost.serverlist.Api.Api;
import pl.icehost.serverlist.Command.NagrodaCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ServerList extends JavaPlugin {

    public final Nagroda nagroda = new Nagroda();

    private final NagrodaCommand nagrodaCommand = new NagrodaCommand(this);

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
        Timer time = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray obj = (JSONArray) new JSONParser().parse(response.body().string());
                    for (Object var2 : obj) {
                        JSONObject jsonObject = (JSONObject) var2;
                        System.out.println(jsonObject.get("player_nickname").toString());
                        Player player = Bukkit.getPlayer(jsonObject.get("player_nickname").toString());
                        if (player != null) {
                            if (player.isOnline()) {
                                send(jsonObject.get("transaction_hash").toString());
                                for (Trigger var : api.getTriggers()) {
                                    var.trigger(Integer.parseInt(jsonObject.get("days").toString()), player);
                                }
                            }
                        }
                    }
                } catch (IOException | ParseException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        api.addTrigger(new Trigger() {
            @Override
            public void trigger(Integer interval, Player player) {
                System.out.println(interval+"  "+player.getName());
                nagroda.give(interval, player);
            }
        });
        getCommand("nagroda").setExecutor(nagrodaCommand);
    }

    @Override
    public void onDisable() {
        config.savePlayers();
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
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("test2")) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://codebit.pl/api/v1/game_servers/minecraft/get/prizes")
                        .addHeader("Authorization", "Bearer " + config.gettoken())
                        .build();
                Response response = client.newCall(request).execute();
                JSONArray obj = (JSONArray) new JSONParser().parse(response.body().string());
                System.out.println(obj.toString());
                for (Object var2 : obj) {
                    JSONObject jsonObject = (JSONObject) var2;
                    System.out.println(jsonObject.get("player_nickname").toString());
                    Player player = Bukkit.getPlayer(jsonObject.get("player_nickname").toString());
                    if (player != null) {
                        if (player.isOnline()) {
                            send(jsonObject.get("transaction_hash").toString());
                            for (Trigger var : api.getTriggers()) {
                                var.trigger(Integer.parseInt(jsonObject.get("days").toString()), player);
                            }
                        }
                    }
                }
            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
        }
        return super.onCommand(sender, command, label, args);
    }
}
