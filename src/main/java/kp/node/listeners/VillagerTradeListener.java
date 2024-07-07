package kp.node.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.inventory.MerchantRecipe;

public class VillagerTradeListener implements Listener {

    @EventHandler
    public void onVillagerReplenishTrade(VillagerReplenishTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        recipe.setMaxUses(Integer.MAX_VALUE);
    }

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        recipe.setMaxUses(Integer.MAX_VALUE);
    }
}
