package pl.icehost.serverlist.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.icehost.serverlist.ServerList;

public class Reload implements CommandExecutor {

    private final ServerList plugin;

    public Reload(ServerList plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("server.list.*")) plugin.config.load();
        return false;
    }
}
