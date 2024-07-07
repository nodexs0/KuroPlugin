package kp.node;

import kp.node.listeners.VillagerTradeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class kuroplugin extends JavaPlugin {

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("KuroPlugin has been enabled!");
        registerEvents();
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("KuroPlugin has been disabled!");
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new VillagerTradeListener(), this);
    }
}
