package com.tyrengard.aureycore.foundation;

public abstract class AManager<T extends AManagedPlugin> {
	protected T plugin;
	protected AManager(T plugin) {
		this.plugin = plugin;
	}

	protected void logError(String message) {
		plugin.logError(message);
	}

	protected void logWarning(String message) {
		plugin.logWarning(message);
	}

	protected void logDebug(String message) {
		plugin.logDebug(message);
	}

	protected abstract void startup();
	protected abstract void cleanup();
}
