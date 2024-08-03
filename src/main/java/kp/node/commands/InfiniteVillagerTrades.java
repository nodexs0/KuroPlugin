package kp.node.commands;

import kp.node.kuroplugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfiniteVillagerTrades implements CommandExecutor {

    private final kuroplugin plugin;

    public InfiniteVillagerTrades(kuroplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            plugin.setInfiniteVillagerTrades(true);
            plugin.saveConfig();
            sender.sendMessage("Intercambios infinitos habilitados");
            return true;
        }
        return false;
    }
}
