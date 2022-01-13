package com.tyrengard.aureycore.foundation;

import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigurationProvider {
    void setConfigDefaults(FileConfiguration config);
    void saveSettingsToConfig(FileConfiguration config);
}
