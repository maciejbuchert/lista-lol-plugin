package pl.icehost.serverlist;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Nagroda {

    private final ArrayList<ItemStack> itemStacks = new ArrayList<>();

    public ArrayList<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public void addItemStack(ItemStack itemStack){
        itemStacks.add(itemStack);
    }

    public boolean removeItemStack(ItemStack itemStack){
        return itemStacks.remove(itemStack);
    }

    public boolean give(Player player){
        if (player.isOnline()){
            for (ItemStack itemStack: itemStacks){
                if (player.getInventory().firstEmpty()!=-1){
                    player.getInventory().addItem(itemStack);
                }else{
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                }
            }
            return true;
        }
        return false;
    }
}
