package com.tyrengard.aureycore.foundation.common.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtils {

	@FunctionalInterface
	public interface Procedure {
		void invoke();
	}
	
	@FunctionalInterface
	public interface TimedProcedure {
		void invoke(long timeRemaining);
	}
	
	public static int runTask(Plugin plugin, Procedure p) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				p.invoke();
			}
		}.runTask(plugin).getTaskId();
	}
	
	public static int runTaskLater(Plugin plugin, long delay, Procedure p) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				p.invoke();
			}
		}.runTaskLater(plugin, delay).getTaskId();
	}
	
	public static int runTaskPeriodically(Plugin plugin, long delay, long period, Procedure p) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				p.invoke();
			}
		}.runTaskTimer(plugin, delay, period).getTaskId();
	}
	
	public static int runTaskTimer(Plugin plugin, long delay, long periodTicks, int ticks, TimedProcedure p) {
		return runTaskTimer(plugin, delay, periodTicks, ticks, p, null);
	}
	
	public static int runTaskTimer(Plugin plugin, long delayTicks, long periodTicks, long ticks, 
			TimedProcedure p, Procedure onFinish) {
		return new BukkitRunnable() {
			long timeRemaining = ticks / periodTicks;
			@Override
			public void run() {
				if (p != null)
					p.invoke(timeRemaining);
				if (timeRemaining > 0) {
					timeRemaining--;
					if (timeRemaining == 0) {
						cancel();
						if (onFinish != null) onFinish.invoke();
//							new BukkitRunnable() {
//								@Override
//								public void run() { onFinish.invoke(); }
//							}.runTaskLater(plugin, periodTicks);
					}
				}
			}
		}.runTaskTimer(plugin, delayTicks, periodTicks).getTaskId();
	}
	
	public static int runTaskAsynchronously(Plugin plugin, Procedure p) {
		return new BukkitRunnable() {
			@Override
			public void run() {
				p.invoke();
			}
		}.runTaskAsynchronously(plugin).getTaskId();
	}
	
	public static void cancelTask(int taskId) {
		Bukkit.getScheduler().cancelTask(taskId);
	}
}
