package com.tyrengard.aureycore.foundation.common.stringformat;

import org.bukkit.Material;

public enum Color {
	BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, 
	DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, ORANGE, MAGENTA, LIGHT_BLUE, LIME, PINK, PURPLE;
	
	public org.bukkit.ChatColor toChatColor() {
		switch(this) {
		case BLACK: return org.bukkit.ChatColor.BLACK;
		case DARK_GRAY: return org.bukkit.ChatColor.DARK_GRAY;
		case GRAY: return org.bukkit.ChatColor.GRAY;
		case WHITE: return org.bukkit.ChatColor.WHITE;
		case DARK_BLUE: return org.bukkit.ChatColor.DARK_BLUE;
		case BLUE: return org.bukkit.ChatColor.BLUE;
		case DARK_AQUA: return org.bukkit.ChatColor.DARK_AQUA;
		case AQUA: case LIGHT_BLUE: return org.bukkit.ChatColor.AQUA;
		case DARK_GREEN: return org.bukkit.ChatColor.DARK_GREEN;
		case GREEN: case LIME: return org.bukkit.ChatColor.GREEN;
		case DARK_RED: return org.bukkit.ChatColor.DARK_RED;
		case RED: case PINK: return org.bukkit.ChatColor.RED;
		case DARK_PURPLE: case PURPLE: return org.bukkit.ChatColor.DARK_PURPLE;
		case LIGHT_PURPLE: case MAGENTA: return org.bukkit.ChatColor.LIGHT_PURPLE;
		case GOLD: case ORANGE: return org.bukkit.ChatColor.GOLD;
		case YELLOW: return org.bukkit.ChatColor.YELLOW;
		default: return null;
		}
	}
	
	public Material toGlassPane() {
		switch(this) {
		case BLACK: return Material.BLACK_STAINED_GLASS_PANE;
		case DARK_GRAY: return Material.GRAY_STAINED_GLASS_PANE;
		case GRAY: return Material.LIGHT_GRAY_STAINED_GLASS_PANE;
		case WHITE: return Material.WHITE_STAINED_GLASS_PANE;
		case DARK_BLUE: case BLUE: return Material.BLUE_STAINED_GLASS_PANE;
		case DARK_AQUA: return Material.CYAN_STAINED_GLASS_PANE;
		case AQUA: case LIGHT_BLUE: return Material.LIGHT_BLUE_STAINED_GLASS_PANE;
		case DARK_GREEN: return Material.GREEN_STAINED_GLASS_PANE;
		case GREEN: case LIME: return Material.LIME_STAINED_GLASS_PANE;
		case DARK_RED: return Material.RED_STAINED_GLASS_PANE;
		case RED: return Material.PINK_STAINED_GLASS_PANE;
		case DARK_PURPLE: case PURPLE: return Material.PURPLE_STAINED_GLASS_PANE;
		case LIGHT_PURPLE: case MAGENTA: return Material.MAGENTA_STAINED_GLASS_PANE;
		case GOLD: case ORANGE: return Material.ORANGE_STAINED_GLASS_PANE;
		case YELLOW: return Material.YELLOW_STAINED_GLASS_PANE;
		default: return null;
		}
	}
	
	public Material toWool() {
		switch(this) {
		case BLACK: return Material.BLACK_WOOL;
		case DARK_GRAY: return Material.GRAY_WOOL;
		case GRAY: return Material.LIGHT_GRAY_WOOL;
		case WHITE: return Material.WHITE_WOOL;
		case DARK_BLUE: case BLUE: return Material.BLUE_WOOL;
		case DARK_AQUA: return Material.CYAN_WOOL;
		case AQUA: case LIGHT_BLUE: return Material.LIGHT_BLUE_WOOL;
		case DARK_GREEN: return Material.GREEN_WOOL;
		case GREEN: case LIME: return Material.LIME_WOOL;
		case DARK_RED: return Material.RED_WOOL;
		case RED: return Material.PINK_WOOL;
		case DARK_PURPLE: case PURPLE: return Material.PURPLE_WOOL;
		case LIGHT_PURPLE: case MAGENTA: return Material.MAGENTA_WOOL;
		case GOLD: case ORANGE: return Material.ORANGE_WOOL;
		case YELLOW: return Material.YELLOW_WOOL;
		default: return null;
		}
	}
}
