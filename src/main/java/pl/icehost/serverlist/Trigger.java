package pl.icehost.serverlist;

import org.bukkit.entity.Player;

public interface Trigger {

    void trigger(Integer interval, Player player);

}
