package pl.icehost.serverlist;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nagroda {

    private final ConsoleCommandSender sender = Bukkit.getConsoleSender();

    private final HashMap<String, ArrayList<ItemStack>> itemStacks = new HashMap<>();

    private final HashMap<String, ArrayList<String>> commands = new HashMap<>();

    public HashMap<String, ArrayList<ItemStack>> getItemStacks() {
        return itemStacks;
    }

    public HashMap<String, ArrayList<String>> getCommands() {
        return commands;
    }


    public void addItemStack(Integer min, Integer max,ItemStack itemStack){
        ArrayList<ItemStack> orDefault = itemStacks.getOrDefault(min + "-" + max, new ArrayList<>());
        orDefault.add(itemStack);
        itemStacks.put(min + "-" + max, orDefault);
    }

    public void addCommand(Integer min, Integer max,String command){
        ArrayList<String> orDefault = commands.getOrDefault(min + "-" + max, new ArrayList<>());
        orDefault.add(command);
        commands.put(min + "-" + max, orDefault);
    }

    public void addCommand(HashMap<String, ArrayList<String>> command){
        commands.putAll(command);
    }
    public void addItemStack(HashMap<String, ArrayList<ItemStack>> itemStack){
        itemStacks.putAll(itemStack);
    }

    public void give(Integer interval, Player player){
        if (player!=null) {
            if (player.isOnline()) {
                for (Map.Entry<String, ArrayList<String>> i : commands.entrySet()){
                    String[] var = i.getKey().split("-");
                    if (interval >= Integer.parseInt(var[0]) && interval <= Integer.parseInt(var[1])) {
                        for (String i2 : i.getValue()) {
                            Bukkit.dispatchCommand(sender, i2.replaceAll("%player%", player.getName()));
                        }
                    }
                }
                for (Map.Entry<String, ArrayList<ItemStack>> i : itemStacks.entrySet()){
                    String[] var = i.getKey().split("-");
                    if (interval >= Integer.parseInt(var[0]) && interval <= Integer.parseInt(var[1])) {
                        for (ItemStack i2 : i.getValue()) {
                            if (player.getInventory().firstEmpty() != -1) {
                                player.getInventory().addItem(i2);
                            } else {
                                player.getWorld().dropItemNaturally(player.getLocation(), i2);
                            }
                        }
                    }
                }
            }
        }
    }
}
