package pl.icehost.serverlist;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.icehost.serverlist.Dane.Reward;

public class Nagroda {

    private final ConsoleCommandSender sender = Bukkit.getConsoleSender();

    public void give(Integer interval, Player player){
        if (player!=null) {
            if (player.isOnline()) {
                for (Reward i : Reward.get()) {
                    if (i.interval(interval)) {
                        System.out.println("Min: "+i.getMin());
                        System.out.println("max: "+i.getMax());
                        for (ItemStack itemStack : i.getItems()) {
                            if (player.getInventory().firstEmpty() != -1) {
                                player.getInventory().addItem(itemStack);
                            } else {
                                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                            }
                        }
                        for (String k : i.getCommands()) {
                            Bukkit.dispatchCommand(sender, k.replaceAll("%player%", player.getName()));
                        }
                        for (String m : i.getMessage()) {
                            player.sendMessage(m);
                        }
                    }
                }
            }
        }
    }
}
