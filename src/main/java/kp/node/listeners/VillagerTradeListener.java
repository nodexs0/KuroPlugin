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
        if (plugin.isInfiniteVillagerTrades()) {
            MerchantRecipe recipe = event.getRecipe();
            recipe.setMaxUses(Integer.MAX_VALUE);
        }
    }

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        if (plugin.isInfiniteVillagerTrades()) {
            MerchantRecipe recipe = event.getRecipe();
            recipe.setMaxUses(Integer.MAX_VALUE);
        }
    }
}
