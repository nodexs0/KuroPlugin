package kp.node.commands;

import kp.node.kuroplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class WarpsCommand implements CommandExecutor, Listener {

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

        if (command.getName().equalsIgnoreCase("warp")) {
            if (args.length == 0) {
                openWarpGUI(player);
                return true;
            } else if (args.length == 1) {
                String warpName = args[0].toLowerCase();
                teleportToWarp(player, warpName);
                return true;
            } else {
                player.sendMessage("Usage: /warp or /warp <warpName>");
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("setwarp")) {
            if (args.length == 1) {
                String warpName = args[0].toLowerCase();
                setWarp(player, warpName);
                player.sendMessage("Warp " + warpName + " set!");
                return true;
            } else {
                player.sendMessage("Usage: /setwarp <warpName>");
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("delwarp")) {
            if (args.length == 1) {
                String warpName = args[0].toLowerCase();
                delWarp(player, warpName);
                player.sendMessage("Warp " + warpName + " deleted!");
                return true;
            } else {
                player.sendMessage("Usage: /delwarp <warpName>");
                return true;
            }
        }

        return false;
    }

    private void openWarpGUI(Player player) {
        FileConfiguration config = plugin.getConfig();
        Set<String> warps = config.getConfigurationSection("warps").getKeys(false);

        Inventory warpInventory = Bukkit.createInventory(null, 27, "Select a Warp");

        int i = 0;
        for (String warp : warps) {
            ItemStack item = new ItemStack(Material.ENDER_PEARL);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + warp);
            item.setItemMeta(meta);
            warpInventory.setItem(i, item);
            i++;
        }

        player.openInventory(warpInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Select a Warp")) return;
        event.setCancelled(true);

        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        Player player = (Player) event.getWhoClicked();
        String warpName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

        teleportToWarp(player, warpName);
        player.closeInventory();
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