package com.tyrengard.aureycore.foundation.common.utils;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class BossBarUtils {
	private static final HashMap<NamespacedKey, Integer> bossBarTasks = new HashMap<>();
	
	public static void addBossBarTimerForPlayer(Plugin plugin, String key, String title, BarColor color, Player p, long durationTicks) {
		NamespacedKey nsk = new NamespacedKey(plugin, key + "-" +  p.getName());
		KeyedBossBar bossBar = plugin.getServer().getBossBar(nsk);
		final KeyedBossBar kbb = bossBar == null ? plugin.getServer().createBossBar(nsk, title, color, BarStyle.SOLID) : bossBar;
		kbb.setTitle(title);
		kbb.setColor(color);
		kbb.addPlayer(p);
		if (bossBarTasks.containsKey(nsk))
			TaskUtils.cancelTask(bossBarTasks.get(nsk));
		bossBarTasks.put(nsk, TaskUtils.runTaskTimer(plugin, 0, 1, durationTicks, timeRemaining -> {
			kbb.setProgress(Math.max(0, Math.min((double) timeRemaining / (double) durationTicks, 1.0)));
		}, () -> {
			kbb.removeAll();
			plugin.getServer().removeBossBar(nsk);
			bossBarTasks.remove(nsk);
		}));
	}
	
	public static void addBossBarForPlayer(Plugin plugin, String key, String title, BarColor color, double progress, Player p, 
			long durationTicks) {
		if (durationTicks > 0) {
			NamespacedKey nsk = new NamespacedKey(plugin, key + "-" +  p.getName());
			KeyedBossBar bossBar = plugin.getServer().getBossBar(nsk);
			final KeyedBossBar kbb = bossBar == null ? plugin.getServer().createBossBar(nsk, title, color, BarStyle.SOLID) : bossBar;
			kbb.setTitle(title);
			kbb.setColor(color);
			kbb.addPlayer(p);
			kbb.setProgress(progress);
			if (bossBarTasks.containsKey(nsk))
				TaskUtils.cancelTask(bossBarTasks.get(nsk));
			bossBarTasks.put(nsk, TaskUtils.runTaskLater(plugin, durationTicks, () -> {
				kbb.removeAll();
				plugin.getServer().removeBossBar(nsk);
				bossBarTasks.remove(nsk);
			}));
		}
	}
	
	public static void removeBossBarForPlayer(Plugin plugin, String key, Player p) {
		NamespacedKey nsk = new NamespacedKey(plugin, key + "-" +  p.getName());
		KeyedBossBar bossBar = plugin.getServer().getBossBar(nsk);
		if (bossBar != null) {
			bossBar.removeAll();
			plugin.getServer().removeBossBar(nsk);
		}
		if (bossBarTasks.containsKey(nsk))
			TaskUtils.cancelTask(bossBarTasks.get(nsk));
	}
}
