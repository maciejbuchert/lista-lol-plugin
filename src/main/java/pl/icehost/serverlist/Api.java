package pl.icehost.serverlist;

public class Api {

    private final ServerList plugin;

    public Api(ServerList plugin) {
        this.plugin = plugin;
    }

    public Nagroda getNagroda(){
        return plugin.nagroda;
    }

    private Trigger trigger = null;

    public void setTrigger(Trigger trigger){
        this.trigger=trigger;
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
