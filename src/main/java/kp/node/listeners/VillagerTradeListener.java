package kp.node.listeners;

import kp.node.kuroplugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.inventory.MerchantRecipe;

public class VillagerTradeListener implements Listener {

    private final kuroplugin plugin;

    public VillagerTradeListener(kuroplugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVillagerReplenishTrade(VillagerReplenishTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        if (recipe != null) {
            if (plugin.isInfiniteVillagerTrades()) {
                recipe.setMaxUses(Integer.MAX_VALUE);
            } else {
                int defaultMaxUses = plugin.getConfig().getInt("defaultMaxUses", 12); // Load from config
                recipe.setMaxUses(defaultMaxUses);
            }
        }
    }

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        if (recipe != null) {
            if (plugin.isInfiniteVillagerTrades()) {
                recipe.setMaxUses(Integer.MAX_VALUE);
            } else {
                int defaultMaxUses = plugin.getConfig().getInt("defaultMaxUses", 12); // Load from config
                recipe.setMaxUses(defaultMaxUses);
            }
        }
    }
}
