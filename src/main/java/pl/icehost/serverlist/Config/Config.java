package pl.icehost.serverlist.Config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.icehost.serverlist.Dane.Reward;
import pl.icehost.serverlist.ServerList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {

    private final ServerList plugin;

    private final File file;

    public Config(ServerList plugin) {
        this.plugin = plugin;
        plugin.saveResource("config.yml", false);
        file=new File("plugins/ServerList/config.yml");
        load();
    }

    private String token = null;

    public void load() {
        Reward.clear();
        System.out.println("----------------");
        FileConfiguration nagrodaconfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = null;
        ConfigurationSection items = null;
        if (nagrodaconfig.isConfigurationSection("nagroda")) {
            section = nagrodaconfig.getConfigurationSection("nagroda");
        } else {
            System.out.println("błąd");
        }
        if (nagrodaconfig.isConfigurationSection("items")) {
            items = nagrodaconfig.getConfigurationSection("items");
        } else {
            System.out.println("błąd");
        }
        if (nagrodaconfig.isConfigurationSection("config")) {
            token = nagrodaconfig.getConfigurationSection("config").getString("token");
        } else {
            System.out.println("błąd");
        }
        if (items == null || section == null || token == null) {
            System.out.println("błąd");
            return;
        }
        HashMap<String, ItemStack> itemMap = new HashMap<>();
        for (String var: items.getKeys(false)){
            ItemStack itemStack;
            if (items.isSet(var + ".material")) {
                Material m = Material.getMaterial(items.getString(var + ".material"));
                if (m == null) return;
                itemStack = new ItemStack(m);
            } else return;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (items.isSet(var + ".nazwa")) {
                itemMeta.setDisplayName(chatfix(items.getString(var + ".nazwa")));
            }
            if (items.isSet(var + ".opis")) {
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
            String[] internal = var.split("-");
            Reward reward;
            if (internal.length==1){
                if (internal[0].endsWith("+")){
                    reward = new Reward(Integer.parseInt(internal[0].replaceAll("\\+","")), -1);
                }else {
                    reward = new Reward(Integer.parseInt(internal[0]), null);
                }
            }else{
                reward = new Reward(Integer.parseInt(internal[0]), Integer.parseInt(internal[1]));
            }
            for (String var2 : section.getStringList(var)){
                if (var2.startsWith("/")){
                    reward.addCommand(var2.substring(1));
                    continue;
                }else if (var2.startsWith("message:")){
                    reward.addMessage(chatfix(var2.substring(8)));
                    continue;
                }
                String[] s = var2.split(":");
                if (itemMap.containsKey(s[0])){
                    ItemStack is = itemMap.get(s[0]).clone();
                    if (s.length!=1) {
                        try {
                            is.setAmount(Integer.parseInt(s[1]));
                        } catch (NumberFormatException e) {
                            System.out.println(s[1] + " to nie liczba");
                        }
                    }else{
                        is.setAmount(1);
                    }
                    reward.addItem(is);
                }else{
                    System.out.println("Brak itemku o tym ID: "+s[0]);
                }
            }
        }
    }

    public String gettoken(){
        return token;
    }

    private String chatfix(String var){
        return ChatColor.translateAlternateColorCodes('&', var);
    }
}
