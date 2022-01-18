package com.tyrengard.aureycore.foundation;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("rawtypes")
public abstract class AManagedPlugin extends JavaPlugin {
	private boolean debugLogging = true;
	private ADatabaseManager databaseManager;
	private final ArrayList<AManager> managers = new ArrayList<>();
	
	@Override
    public void onEnable() {
		log(Level.INFO, "The plugin \"" + this.getName() + "\" is managed by AureyCore!");
		saveDefaultConfig();

		try {
			onPluginEnable();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(this);
		}

		if (databaseManager != null) {
			log(Level.INFO, "Setting up database manager for " + this.getName() + "...");
			databaseManager.startup();
		} else log(Level.INFO, "No database manager set for " + this.getName() + ".");

		log(Level.INFO, "Starting up managers for " + this.getName() + "...");
		for (AManager manager : managers)
			manager.startup();

		onPostEnable();
    }
   
	@Override
    public void onDisable() {
    	onPluginDisable();

		log(Level.INFO, "Cleaning up managers for " + this.getName() + "...");
		boolean configHasChanges = false;
    	for(AManager manager : managers) {
			manager.cleanup();
			if (manager instanceof ConfigurationProvider configurationProvider) {
				configurationProvider.saveSettingsToConfig(getConfig());
				configHasChanges = true;
			}
			if (manager instanceof Listener listener)
				HandlerList.unregisterAll(listener);
		}
    	managers.clear();

		if (databaseManager != null) {
			log(Level.INFO, "Cleaning up database manager for " + this.getName() + "...");
			databaseManager.cleanup();
			if (databaseManager instanceof ConfigurationProvider configurationProvider) {
				configurationProvider.saveSettingsToConfig(getConfig());
				configHasChanges = true;
			}
		}
		databaseManager = null;

		if (configHasChanges)
    		saveConfig();
    }

    protected final void reload() {
		log(Level.INFO, "Reloading " + this.getName() + "...");
		setEnabled(false);
		setEnabled(true);
	}
	
	protected final void addManager(AManager manager) throws InvalidConfigurationException {
		managers.add(manager);
		if (manager instanceof ConfigurationProvider configurationProvider) {
			configurationProvider.setConfigDefaults(getConfig());
			saveConfig();
		}
		if (manager instanceof Configured configuredManager)
			configuredManager.loadSettingsFromConfig(getConfig());
		if (manager instanceof Listener listener)
			registerListener(listener);
	}
	
	protected final void registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}
	
	protected final void addCommandExecutor(String command, CommandExecutor executor) {
		PluginCommand c = getCommand(command);
		if (c == null)
			logWarning("Tried adding executor for unsupported command /" + command + ". " +
					"Please check this plugin's plugin.yml file.");
		else
			c.setExecutor(executor);
	}
	
	protected final void addACommandExecutor(ACommandExecutor ace, String... commands) {
		for (String command : commands) {
			PluginCommand c = getCommand(command);
			if (c == null)
				logWarning("Tried adding ACommandExecutor for unsupported command /" + command + ". " +
						"Please check this plugin's plugin.yml file.");
			else {
				c.setExecutor(ace);
				c.setTabCompleter(ace);
			}
		}
	}

	public ADatabaseManager getDatabaseManager() {
		return this.databaseManager;
	}
	protected final void setDatabaseManager(ADatabaseManager databaseManager) throws InvalidConfigurationException {
		this.databaseManager = databaseManager;
		if (databaseManager instanceof ConfigurationProvider configurationProvider) {
			configurationProvider.setConfigDefaults(getConfig());
			saveConfig();
		}
		if (databaseManager instanceof Configured configuredManager)
			configuredManager.loadSettingsFromConfig(getConfig());
		if (databaseManager instanceof Listener listener) registerListener(listener);
	}

	protected void logError(String message) {
		log(Level.SEVERE, message);
	}

	protected void logWarning(String message) {
		log(Level.WARNING, message);
	}

	protected void logInfo(String message) {
		log(Level.INFO, message);
	}

	protected void logDebug(String message) {
		if (debugLogging)
			log(Level.INFO, message);
	}

	protected void log(Level level, String message) {
		getLogger().log(level, message);
	}

	protected void setDebugLogging(boolean value) {
		debugLogging = value;
	}

	protected abstract void onPluginEnable() throws InvalidConfigurationException;
	protected abstract void onPluginDisable();

    protected void onPostEnable() {}
}
