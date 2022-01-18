package com.tyrengard.aureycore.foundation.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
	public static boolean hasEmptySlot(Inventory i) { return hasEmptySlots(i, 1); }
	public static boolean hasEmptySlots(Inventory i, int count) {
		int emptySlots = 0;
		for (ItemStack item : i.getContents()) if (item == null) emptySlots++;
		return emptySlots >= count;
	}
	
	public static void copyOverSlots(Inventory source, Inventory destination) {
		if (destination.getSize() < source.getSize()) return;
		
		for (int c = 0; c < destination.getSize(); c++)
			destination.setItem(c, source.getItem(c));
	}
	
	public static void fillInventory(Inventory inv, ItemStack item, int... exceptions) {
		List<Integer> exceptionsList = Arrays.stream(exceptions).boxed().collect(Collectors.toList());
		for (int c = 0; c < inv.getSize(); c++)
			if (!exceptionsList.contains(c))
				inv.setItem(c, item.clone());
	}
	
	public static List<ItemStack> getPlayerHotbar(Player p) {
		ItemStack[] contents = p.getInventory().getContents();
		List<ItemStack> output = new ArrayList<>(9);
		for (int c = 0; c < 9; c++)
			output.add(contents[c]);
		return output;
	}

	public static List<ItemStack> getContentsOfPotionSlots(BrewerInventory bi) {
		ArrayList<ItemStack> output = new ArrayList<>();
		for (int i = 0; i <= 2; i++)
			output.add(bi.getItem(i));
		return output;
	}
}
