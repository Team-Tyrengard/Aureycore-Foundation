package com.tyrengard.aureycore.foundation.common.anvilevents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Zelnehlun
 * @since Apr 22, 2013
 *
 * @author hayashikin
 * @since Aug 2, 2021
 */
public class AnvilListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent e){
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem != null && clickedItem.getType() != Material.AIR &&
                e.getInventory() instanceof AnvilInventory anvilInventory && e.getSlotType() == InventoryType.SlotType.RESULT) {
            Player p = (Player) e.getWhoClicked();
            Block anvilBlock = anvilInventory.getHolder() instanceof Block b ? b : null;

            AnvilUseEvent useEvent = new AnvilUseEvent(p, anvilBlock, anvilInventory, clickedItem);
            Bukkit.getPluginManager().callEvent(useEvent);
            if (useEvent.isCancelled()) {
                e.setCancelled(true);
                return;
            }

            String newName = anvilInventory.getRenameText();
            String oldName;
            if (newName != null) {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta != null) {
                    if (meta.hasDisplayName()) oldName = meta.getDisplayName();
                    else oldName = meta.getLocalizedName();
                } else oldName = "";

                if (!newName.equals(oldName)) {
                    AnvilRenameItemEvent renameItemEvent = new AnvilRenameItemEvent(p, anvilBlock, anvilInventory, clickedItem, oldName, newName);
                    Bukkit.getPluginManager().callEvent(renameItemEvent);
                    e.setCancelled(renameItemEvent.isCancelled());
                }
            }
        }
    }
}
