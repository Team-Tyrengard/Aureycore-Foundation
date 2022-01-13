package com.tyrengard.aureycore.foundation.common.anvilevents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilRenameItemEvent extends AnvilUseEvent {
    // region Base event components
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    // endregion

    private final String oldName;
    private String newName;

    public AnvilRenameItemEvent(Player player, Block anvilBlock, AnvilInventory anvilInventory, ItemStack result,
                                String oldName, String newName) {
        super(player, anvilBlock, anvilInventory, result);
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
