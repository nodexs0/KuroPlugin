package kp.node;

import kp.node.commands.InfiniteVillagerTrades;
import kp.node.commands.InfiniteVillagerTradesOff;
import kp.node.listeners.VillagerTradeListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class kuroplugin extends JavaPlugin {

    private boolean infiniteVillagerTrades = false;

    private void loadConfig() {
        saveDefaultConfig();
        infiniteVillagerTrades = getConfig().getBoolean("infiniteTradesEnabled", false);
    }

    @Override
    public void saveConfig() {
        FileConfiguration config = getConfig();
        config.set("infiniteTradesEnabled", infiniteVillagerTrades);
        super.saveConfig();
    }

    @Override
    public void onEnable() {
        loadConfig();
        registerEvents();
        registerCommands();
        Bukkit.getConsoleSender().sendMessage(infiniteVillagerTrades ? "Infinite Villager Trades enabled" : "Infinite Villager Trades disabled");
        Bukkit.getConsoleSender().sendMessage("KuroPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getConsoleSender().sendMessage("KuroPlugin has been disabled!");
    }

    private void registerCommands() {
        getCommand("InfiniteVillagerTrades").setExecutor(new InfiniteVillagerTrades(this));
        getCommand("InfiniteVillagerTradesOff").setExecutor(new InfiniteVillagerTradesOff(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new VillagerTradeListener(this), this);
    }

    public boolean isInfiniteVillagerTrades() {
        return infiniteVillagerTrades;
    }

    public void setInfiniteVillagerTrades(boolean infiniteVillagerTrades) {
        this.infiniteVillagerTrades = infiniteVillagerTrades;
    }
}
