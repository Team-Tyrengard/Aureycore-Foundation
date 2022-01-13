package com.tyrengard.aureycore.foundation.common.anvilevents;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilUseEvent extends Event implements Cancellable {
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

    // region Cancellable overrides
    private boolean cancelled = false;
    @Override
    public boolean isCancelled() { return cancelled; }
    @Override
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
    // endregion

    private final Player player;
    private final Block anvilBlock;
    private final AnvilInventory anvilInventory;
    private ItemStack result;

    public AnvilUseEvent(Player player, Block anvilBlock, AnvilInventory anvilInventory, ItemStack result) {
        this.player = player;
        this.anvilBlock = anvilBlock;
        this.anvilInventory = anvilInventory;
        this.result = result;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getAnvilBlock() {
        return anvilBlock;
    }

    public AnvilInventory getAnvilInventory() {
        return anvilInventory;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }
}
