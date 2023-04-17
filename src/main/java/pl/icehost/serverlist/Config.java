package pl.icehost.serverlist;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {

    private final ServerList plugin;

    private final File file;

    private final File players;

    private FileConfiguration playersConfig;

    public Config(ServerList plugin) {
        this.plugin = plugin;
        plugin.saveResource("config.yml", false);
        plugin.saveResource("players.yml", false);

        file=new File("plugins/ServerList/config.yml");
        players=new File("plugins/ServerList/players.yml");
        load();
    }

    private String token = null;

    public void load() {
        System.out.println("----------------");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        playersConfig = YamlConfiguration.loadConfiguration(players);
        plugin.addOfflinePlayer(playersConfig.getStringList("players"));
        ConfigurationSection section = null;
        ConfigurationSection items = null;
        if (config.isConfigurationSection("nagroda")) {
            section = config.getConfigurationSection("nagroda");
        } else {
            System.out.println("błąd");
        }
        if (config.isConfigurationSection("items")) {
            items = config.getConfigurationSection("items");
        } else {
            System.out.println("błąd");
        }
        if (items == null || section == null) {
            System.out.println("błąd");
            return;
        }
        HashMap<String, ArrayList<ItemStack>> itemsAdd = new HashMap<>();
        HashMap<String, ArrayList<String >> commandAdd = new HashMap<>();
        HashMap<String, ItemStack> itemMap = new HashMap<>();
        for (String var: items.getKeys(false)){
            ItemStack itemStack;
            if (items.isSet(var + ".material")) {
                Material m = Material.getMaterial(items.getString(var + ".material"));
                System.out.println(m);
                if (m == null) return;
                itemStack = new ItemStack(m);
            } else return;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (items.isSet(var + ".nazwa")) {
                System.out.println(items.getString(var + ".nazwa"));
                itemMeta.setDisplayName(chatfix(items.getString(var + ".nazwa")));
            }
            if (items.isSet(var + ".opis")) {
                System.out.println(items.getStringList(var + ".opis"));
                List<String> stringList = items.getStringList(var + ".opis");
                for (int i = 0; i < stringList.size(); i++) {
                    stringList.set(i, chatfix(stringList.get(i)));
                }
                itemMeta.setLore(stringList);
            }
            itemStack.setItemMeta(itemMeta);
            itemMap.put(var, itemStack);
        }
        for (String var : section.getKeys(false)) {
            if (var.equals("token")){
                token=section.getString(var);
                continue;
             }
            ArrayList<ItemStack> itemStacks = new ArrayList<>();
            ArrayList<String> commands = new ArrayList<>();
            for (String var2 : section.getStringList(var)){
                if (var2.startsWith("/")){
                    commands.add(var2.substring(1));
                    continue;
                }
                String[] s = var2.split(":");
                if (itemMap.containsKey(s[0])){
                    ItemStack is = itemMap.get(s[0]);
                    if (s.length!=1) {
                        try {
                            is.setAmount(Integer.parseInt(s[1]));
                        } catch (NumberFormatException e) {
                            System.out.println(s[1] + " to nie liczba");
                        }
                    }else{
                        is.setAmount(1);
                    }
                    itemStacks.add(is);
                }else{
                    System.out.println("Brak itemku o tym ID: "+s[0]);
                }
            }
            itemsAdd.put(var, itemStacks);
            commandAdd.put(var, commands);
        }
        plugin.nagroda.addCommand(commandAdd);
        plugin.nagroda.addItemStack(itemsAdd);
    }

    public String gettoken(){
        return token;
    }

    private String chatfix(String var){
        return ChatColor.translateAlternateColorCodes('&', var);
    }

    void savePlayers(){
        playersConfig.set("players", plugin.getOfflinePlayers());
    }
}
