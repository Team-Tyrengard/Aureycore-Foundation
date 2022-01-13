package com.tyrengard.aureycore.foundation;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

public interface Configured {
    void loadSettingsFromConfig(FileConfiguration config) throws InvalidConfigurationException;
}
