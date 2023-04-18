package pl.icehost.serverlist.Api;

import pl.icehost.serverlist.Nagroda;
import pl.icehost.serverlist.ServerList;
import pl.icehost.serverlist.Interface.Trigger;

import java.util.ArrayList;

public class Api {

    private final ServerList plugin;

    public Api(ServerList plugin) {
        this.plugin = plugin;
    }

    public Nagroda getNagroda(){
        return plugin.nagroda;
    }

    private final ArrayList<Trigger> triggers = new ArrayList<>();

    public void addTrigger(Trigger trigger){
        this.triggers.add(trigger);
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }
}
