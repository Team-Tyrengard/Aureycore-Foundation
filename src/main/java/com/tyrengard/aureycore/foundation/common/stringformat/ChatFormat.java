package com.tyrengard.aureycore.foundation.common.stringformat;

import org.bukkit.ChatColor;

public class ChatFormat {
	public static String error(Object text) {
		return color(ChatColor.RED, text);
	}
	
	public static String color(ChatColor color, Object text) {
		return format(color, text, false, false, false, false, false);
	}
	
	public static String bold(ChatColor color, Object text) {
		return format(color, text, true, false, false, false, false);
	}
	
	public static String italic(ChatColor color, Object text) {
		return format(color, text, false, true, false, false, false);
	}
	
	public static String underline(ChatColor color, Object text) {
		return format(color, text, false, false, false, false, true);
	}
	
	public static String format(ChatColor color, Object text, boolean bold, boolean italic, boolean magic, boolean strike, 
			boolean underline) {
		return color + "" + 
			   (bold ? ChatColor.BOLD : "") + "" +
			   (italic ? ChatColor.ITALIC : "") + "" +
			   (magic ? ChatColor.MAGIC : "") + "" +
			   (strike ? ChatColor.STRIKETHROUGH : "") + "" +
			   (underline ? ChatColor.UNDERLINE : "") + "" +
			   text.toString() + ChatColor.RESET;
			   
	}
}
