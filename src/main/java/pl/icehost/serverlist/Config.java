package pl.icehost.serverlist;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class Config {

    private final ServerList plugin;

    private final File file;

    public Config(ServerList plugin) {
        this.plugin = plugin;
        plugin.saveResource("config.yml", false);
        file=new File("plugins/ServerList/config.yml");
        load();
    }

    public void load() {
        System.out.println("----------------");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection configurationSection = null;
        if (config.isConfigurationSection("nagroda")) {
            configurationSection = config.getConfigurationSection("nagroda");
        }else{
            System.out.println("błąd");
        }
        if (configurationSection==null){
            System.out.println("błąd");
            return;
        }
        for (String var: configurationSection.getKeys(false)){
            if (configurationSection.isSet(var+".wlaczone")){
                if (!configurationSection.getBoolean(var+".wlaczone"))continue;
            }
            ItemStack itemStack;
            if (configurationSection.isSet(var+".material")){
                Material m = Material.getMaterial(configurationSection.getString(var+".material"));
                System.out.println(m);
                if (m==null)return;
                itemStack = new ItemStack(m);
            }else return;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (configurationSection.isSet(var+".nazwa")){
                System.out.println(configurationSection.getString(var+".nazwa"));
                itemMeta.setDisplayName(chatfix(configurationSection.getString(var+".nazwa")));
            }
            if (configurationSection.isSet(var+".opis")){
                System.out.println(configurationSection.getStringList(var+".opis"));
                List<String> stringList = configurationSection.getStringList(var + ".opis");
                for (int i = 0; i < stringList.size(); i++) {
                    stringList.set(i, chatfix(stringList.get(i)));
                }
                itemMeta.setLore(stringList);
            }
            plugin.nagroda.addItemStack(itemStack);
        }
    }

    private String chatfix(String var){
        return ChatColor.translateAlternateColorCodes('&', var);
    }
}
