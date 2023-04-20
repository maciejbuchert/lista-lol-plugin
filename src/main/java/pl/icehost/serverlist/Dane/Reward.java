package pl.icehost.serverlist.Dane;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class Reward {

    private final Integer min;

    private final Integer max;

    private final ArrayList<ItemStack> items = new ArrayList<>();

    private final ArrayList<String> commands = new ArrayList<>();

    private final ArrayList<String> message = new ArrayList<>();

    private static final ArrayList<Reward> list = new ArrayList<>();

    public static ArrayList<Reward> get(){
        return list;
    }

    public Reward(Integer min, Integer max) {
        this.min = min;
        this.max = max;
        list.add(this);
    }

    public void addItem(ItemStack itemStack){
        items.add(itemStack);
    }

    public void addCommand(String var){
        commands.add(var);
    }

    public void addMessage(String var){
        message.add(var);
    }


    public boolean removeItem(ItemStack itemStack){
        return items.remove(itemStack);
    }

    public void setItems(Collection<ItemStack> var){
        items.clear();
        items.addAll(var);
    }

    public void setCommands(Collection<String> var){
        commands.clear();
        commands.addAll(var);
    }

    public void setMessages(Collection<String> var){
        message.clear();
        message.addAll(var);
    }

    public boolean interval(int var){
        if (max==null) {
            return min == var;
        }else if (max==-1){
            return min <= var;
        }else return min <= var && var <= max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public ArrayList<String> getMessage() {
        return message;
    }
}
