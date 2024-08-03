package kp.node;

import kp.node.commands.InfiniteVillagerTrades;
import kp.node.commands.InfiniteVillagerTradesOff;
import kp.node.commands.WarpsCommand;
import kp.node.items.CustomItems;
import kp.node.listeners.VillagerTradeListener;
import kp.node.listeners.SmithingListener;
import kp.node.listeners.ToolListener;
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
        registerCustomItems();

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
        WarpsCommand warpsCommand = new WarpsCommand(this);
        getCommand("warp").setExecutor(warpsCommand);
        getCommand("setwarp").setExecutor(warpsCommand);
        getCommand("delwarp").setExecutor(warpsCommand);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new VillagerTradeListener(this), this);
        getServer().getPluginManager().registerEvents(new WarpsCommand(this), this);
        getServer().getPluginManager().registerEvents(new SmithingListener(), this);
        getServer().getPluginManager().registerEvents(new ToolListener(this), this);
    }

    private void registerCustomItems() {
        CustomItems customItems = new CustomItems(this);
        customItems.registerRecipe();
    }

    public boolean isInfiniteVillagerTrades() {
        return infiniteVillagerTrades;
    }

    public void setInfiniteVillagerTrades(boolean infiniteVillagerTrades) {
        this.infiniteVillagerTrades = infiniteVillagerTrades;
    }
}
