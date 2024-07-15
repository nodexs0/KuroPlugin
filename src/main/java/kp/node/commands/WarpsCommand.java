package kp.node.commands;

import kp.node.kuroplugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WarpsCommand implements CommandExecutor {

    private final kuroplugin plugin;

    public WarpsCommand(kuroplugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("Usage: /warp <set|del|teleport> <warpName>");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        if (subCommand.equals("set") && args.length == 2) {
            setWarp(player, args[1]);
            player.sendMessage("Warp " + args[1] + " set!");
            return true;
        } else if (subCommand.equals("del") && args.length == 2) {
            delWarp(player, args[1]);
            player.sendMessage("Warp " + args[1] + " deleted!");
            return true;
        } else if (subCommand.equals("teleport") && args.length == 2) {
            teleportToWarp(player, args[1]);
            return true;
        }

        player.sendMessage("Usage: /warp <set|del|teleport> <warpName>");
        return true;
    }

    private void setWarp(Player player, String warpName) {
        Location loc = player.getLocation();
        FileConfiguration config = plugin.getConfig();
        config.set("warps." + warpName + ".world", loc.getWorld().getName());
        config.set("warps." + warpName + ".x", loc.getX());
        config.set("warps." + warpName + ".y", loc.getY());
        config.set("warps." + warpName + ".z", loc.getZ());
        config.set("warps." + warpName + ".yaw", loc.getYaw());
        config.set("warps." + warpName + ".pitch", loc.getPitch());
        plugin.saveConfig();
    }

    private void teleportToWarp(Player player, String warpName) {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("warps." + warpName)) {
            String world = config.getString("warps." + warpName + ".world");
            double x = config.getDouble("warps." + warpName + ".x");
            double y = config.getDouble("warps." + warpName + ".y");
            double z = config.getDouble("warps." + warpName + ".z");
            float yaw = (float) config.getDouble("warps." + warpName + ".yaw");
            float pitch = (float) config.getDouble("warps." + warpName + ".pitch");
            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            player.teleport(loc);
            player.sendMessage("Teleported to warp " + warpName + "!");
        } else {
            player.sendMessage("Warp " + warpName + " does not exist!");
        }
    }

    private void delWarp(Player player, String warpName) {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("warps." + warpName)) {
            config.set("warps." + warpName, null);
            plugin.saveConfig();
        } else {
            player.sendMessage("Warp " + warpName + " does not exist!");
        }
    }
}
