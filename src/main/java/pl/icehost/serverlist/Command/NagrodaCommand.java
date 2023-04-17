package pl.icehost.serverlist.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.icehost.serverlist.ServerList;
import pl.icehost.serverlist.Trigger;

public class NagrodaCommand implements CommandExecutor {

    private final ServerList plugin;

    public NagrodaCommand(ServerList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return false;
    }
}
