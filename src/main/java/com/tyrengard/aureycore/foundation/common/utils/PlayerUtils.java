package com.tyrengard.aureycore.foundation.common.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerUtils {
    public static OfflinePlayer getOfflinePlayer(String name) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            String playerName = player.getName();
            if (name.equalsIgnoreCase(playerName))
                return player;
        }

        return null;
    }
}
