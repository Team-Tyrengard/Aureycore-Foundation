package com.tyrengard.aureycore.foundation.common.utils;

import java.util.HashMap;

import org.bukkit.enchantments.Enchantment;

public class EnchantUtils {
	public static HashMap<Enchantment, Integer> getMapForEnchantment(Enchantment e) {
		HashMap<Enchantment, Integer> output = new HashMap<>();
		output.put(e, 1);
		return output;
	}
	
	public static String getVanillaEnchantmentAsString(Enchantment e, int level) {
		String output = "";
		
		if (e.equals(Enchantment.ARROW_DAMAGE)) output += "Power";
		else if (e.equals(Enchantment.ARROW_FIRE)) output += "Flame";
		else if (e.equals(Enchantment.ARROW_INFINITE)) output += "Infinity";
		else if (e.equals(Enchantment.ARROW_KNOCKBACK)) output += "Punch";
		else if (e.equals(Enchantment.BINDING_CURSE)) output += "Curse of Binding";
		else if (e.equals(Enchantment.CHANNELING)) output += "Channeling";
		else if (e.equals(Enchantment.DAMAGE_ALL)) output += "Sharpness";
		else if (e.equals(Enchantment.DAMAGE_ARTHROPODS)) output += "Bane of Arthropods";
		else if (e.equals(Enchantment.DAMAGE_UNDEAD)) output += "Smite";
		else if (e.equals(Enchantment.DEPTH_STRIDER)) output += "Depth Strider";
		else if (e.equals(Enchantment.DIG_SPEED)) output += "Efficiency";
		else if (e.equals(Enchantment.DURABILITY)) output += "Unbreaking";
		else if (e.equals(Enchantment.FIRE_ASPECT)) output += "Fire Aspect";
		else if (e.equals(Enchantment.FROST_WALKER)) output += "Frost Walker";
		else if (e.equals(Enchantment.IMPALING)) output += "Impaling";
		else if (e.equals(Enchantment.KNOCKBACK)) output += "Knockback";
		else if (e.equals(Enchantment.LOOT_BONUS_BLOCKS)) output += "Fortune";
		else if (e.equals(Enchantment.LOOT_BONUS_MOBS)) output += "Looting";
		else if (e.equals(Enchantment.LOYALTY)) output += "Loyalty";
		else if (e.equals(Enchantment.LUCK)) output += "Luck of the Sea";
		else if (e.equals(Enchantment.MENDING)) output += "Mending";
		else if (e.equals(Enchantment.MULTISHOT)) output += "Multishot";
		else if (e.equals(Enchantment.OXYGEN)) output += "Respiration";
		else if (e.equals(Enchantment.PIERCING)) output += "Piercing";
		else if (e.equals(Enchantment.PROTECTION_ENVIRONMENTAL)) output += "Protection";
		else if (e.equals(Enchantment.PROTECTION_EXPLOSIONS)) output += "Blast Protection";
		else if (e.equals(Enchantment.PROTECTION_FALL)) output += "Feather Falling";
		else if (e.equals(Enchantment.PROTECTION_FIRE)) output += "Fire Protection";
		else if (e.equals(Enchantment.PROTECTION_PROJECTILE)) output += "Projectile Protection";
		else if (e.equals(Enchantment.QUICK_CHARGE)) output += "Quick Charge";
		else if (e.equals(Enchantment.RIPTIDE)) output += "Riptide";
		else if (e.equals(Enchantment.SILK_TOUCH)) output += "Silk Touch";
		else if (e.equals(Enchantment.SWEEPING_EDGE)) output += "Sweeping Edge";
		else if (e.equals(Enchantment.THORNS)) output += "Thorns";
		else if (e.equals(Enchantment.VANISHING_CURSE)) output += "Curse of Vanishing";
		else if (e.equals(Enchantment.WATER_WORKER)) output += "Aqua Affinity";
		else return null;
		
		output += " " + StringUtils.convertIntToRomanNumerals(level);
		return output;
	}
}
