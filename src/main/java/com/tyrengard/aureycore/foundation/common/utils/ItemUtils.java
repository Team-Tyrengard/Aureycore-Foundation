package com.tyrengard.aureycore.foundation.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class ItemUtils {
	/**
	 * Returns the closest-colored wool material to the provided {@code ChatColor}.
	 * @param chatColor
	 * @return
	 */
	public static Material getWoolMaterialForColor(ChatColor chatColor) {
		return switch (chatColor) {
			case BLACK -> Material.BLACK_WOOL;
			case DARK_GRAY -> Material.GRAY_WOOL;
			case GRAY -> Material.LIGHT_GRAY_WOOL;
			case WHITE -> Material.WHITE_WOOL;
			case DARK_BLUE, BLUE -> Material.BLUE_WOOL;
			case DARK_AQUA -> Material.CYAN_WOOL;
			case AQUA -> Material.LIGHT_BLUE_WOOL;
			case DARK_GREEN -> Material.GREEN_WOOL;
			case GREEN -> Material.LIME_WOOL;
			case DARK_RED -> Material.RED_WOOL;
			case RED -> Material.PINK_WOOL;
			case DARK_PURPLE -> Material.PURPLE_WOOL;
			case LIGHT_PURPLE -> Material.MAGENTA_WOOL;
			case GOLD -> Material.ORANGE_WOOL;
			case YELLOW -> Material.YELLOW_WOOL;
			default -> null;
		};
	}

	public static ItemStack[] combine(ItemStack i1, ItemStack i2) {
		if (!i1.isSimilar(i2))
			return null;

		ItemStack oi1 = i1.clone(), oi2 = i2.clone();
		Inventory tempInventory = Bukkit.createInventory(null, 9);
		tempInventory.addItem(oi1, oi2);
		return new ItemStack[] { tempInventory.getItem(0), tempInventory.getItem(1) };
	}

	public static ItemStack newItem(Material mat, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchantments) {
		if (amount < 1) amount = 1;
		
		ItemStack item = new ItemStack(mat, amount);
		
		ItemMeta meta = item.getItemMeta();
		if (name != null)
			meta.setDisplayName(name);
		if (lore != null)
			meta.setLore(lore);
		item.setItemMeta(meta);
		
		if (enchantments != null)
			item.addUnsafeEnchantments(enchantments);
		
		return item;
	}
	
	public static String getName(ItemStack item) {
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasDisplayName()) return item.getItemMeta().getDisplayName();
			else if (meta instanceof PotionMeta) {
				PotionType type = ((PotionMeta) meta).getBasePotionData().getType();
				switch (type) {
				case INSTANT_HEAL: return "Potion of Healing";
				default: return "Potion of " + StringUtils.toTitleCase(type.toString().replace("_", " ").toLowerCase());
				}
			}
			else return getDefaultName(item);
		}
		else return getDefaultName(item);
	}
	
	public static String getDefaultName(ItemStack item) {
		return StringUtils.toTitleCase(item.getType().toString().replace("_", " ").toLowerCase());
	}
	
	public static ItemStack getEnchanted(ItemStack item) {
		ItemStack output = item.clone();
		output.addUnsafeEnchantment(Enchantment.LOYALTY, 1);
		return output;
	}
	
	public static ArrayList<ItemStack> flattenItemStacks(Collection<ItemStack> items) {
		return flattenItemStacks(items.toArray(new ItemStack[items.size()]));
	}
	
	public static ArrayList<ItemStack> flattenItemStacks(ItemStack... items) {
		ArrayList<ItemStack> output = new ArrayList<>();
		for (ItemStack item : items) {
			if (item == null || item.getType() == Material.AIR) continue;
			boolean added = false;
			for (ItemStack outputItem : output) {
				if (item.isSimilar(outputItem)) {
					if (item.getAmount() + outputItem.getAmount() <= outputItem.getMaxStackSize()) {
						outputItem.setAmount(item.getAmount() + outputItem.getAmount());
						added = true;
					} else {
						item.setAmount(item.getAmount() - (outputItem.getMaxStackSize() - outputItem.getAmount()));
						outputItem.setAmount(outputItem.getMaxStackSize());
					}
					break;
				}
			}
			if (!added) output.add(item);
		}
		return output;
	}
	
	public static boolean isABucketItem(ItemStack item) {
		switch(item.getType()) {
		case LAVA_BUCKET: case WATER_BUCKET: case MILK_BUCKET:
			return true;
		default: return false;
		
		}
	}
	
	public static boolean isABottleItem(ItemStack item) {
		switch(item.getType()) {
		case HONEY_BOTTLE: case POTION:
			return true;
		default: return false;
		}
	}
	
	public static void giveOrDropItemsToPlayer(Player p, ItemStack... items) {
		Inventory playerInv = p.getInventory();
		Location playerLoc = p.getLocation();
		for (ItemStack item : items) {
			if (item != null && item.getType() != Material.AIR)
				for (ItemStack excessItem : playerInv.addItem(item).values())
					if (item != null && item.getType() != Material.AIR)
						p.getWorld().dropItem(playerLoc, excessItem);
		}
	}
	
	public static int getIntTagOnItem(ItemStack item, NamespacedKey rootKey) {
		if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return 0;
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		Integer tag = pdc.get(rootKey, PersistentDataType.INTEGER);
		return tag == null ? 0 : tag;
	}
	
	public static void setIntTagOnItem(ItemStack item, NamespacedKey rootKey, int tag) {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(rootKey, PersistentDataType.INTEGER, tag);
		item.setItemMeta(meta);
	}
	
	public static int[] getIntArrayTagOnItem(ItemStack item, NamespacedKey rootKey) {
		if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return new int[0];
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		return pdc.get(rootKey, PersistentDataType.INTEGER_ARRAY);
	}
	
	public static void setIntArrayTagOnItem(ItemStack item, NamespacedKey rootKey, int[] tags) {
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(rootKey, PersistentDataType.INTEGER_ARRAY, tags);
		item.setItemMeta(meta);
	}
	
	public static ItemStack withSilkTouch(ItemStack item) {
		ItemStack output = item.clone();
		output.addEnchantment(Enchantment.SILK_TOUCH, 1);
		return output;
	}
	
	public static ItemStack newPotion(PotionType potionType, boolean extended, boolean upgraded) {
		ItemStack output = new ItemStack(Material.POTION);
		PotionMeta meta = (PotionMeta) output.getItemMeta();
		meta.setBasePotionData(new PotionData(potionType, extended, upgraded));
		output.setItemMeta(meta);
		return output;
	}

	public static ItemStack getPlayerHead(OfflinePlayer p, boolean named) {
		ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
		meta.setOwningPlayer(p);
		if (named) meta.setDisplayName(ChatColor.YELLOW + p.getName());
		
		playerHead.setItemMeta(meta);
		return playerHead;
	}
}
