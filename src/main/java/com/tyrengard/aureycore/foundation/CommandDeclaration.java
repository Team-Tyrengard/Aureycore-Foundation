package com.tyrengard.aureycore.foundation;

import org.bukkit.command.CommandSender;

public final class CommandDeclaration<T extends CommandSender> {
	final boolean isVariable;
	private final String mainCommand, subCommand, shortDescription;
	private final String[] fullDescription;
	private final CommandExecutable<T> executable;
	
	// for help commands
	CommandDeclaration(CommandExecutable<T> executable) {
		this(false, null, null, null, null, executable);
	}
	
	// for subcommand categories without descriptions
	public CommandDeclaration(String mainCommand, String subCommand) {
		this(false, mainCommand, subCommand, null, null, null);
	}
	
	// for subcommand categories with descriptions
	public CommandDeclaration(String mainCommand, String subCommand, String shortDescription, String[] fullDescription) {
		this(false, mainCommand, subCommand, shortDescription, fullDescription, null);
	}
	
	// for subcommand endpoints without descriptions
	public CommandDeclaration(boolean isVariable, String mainCommand, String subCommand, CommandExecutable<T> executable) {
		this(isVariable, mainCommand, subCommand, null, null, executable);
	}
	
	// for subcommand endpoints with descriptions
	public CommandDeclaration(boolean isVariable, String mainCommand, String subCommand, String shortDescription, String[] fullDescription,
			CommandExecutable<T> executable) {
		this.mainCommand = mainCommand;
		this.subCommand = subCommand;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		this.executable = executable;
		this.isVariable = isVariable;
	}
	
	String getFullCommand() { return this.mainCommand + " " + this.subCommand; }
	String getMainCommand() { return this.mainCommand; }
	String getSubCommand() { return this.subCommand; }
	String getShortDescription() { return this.shortDescription; }
	String[] getFullDescription() { return this.fullDescription; }
	
	CommandExecutable<T> getExecutable() { return this.executable; }
	
	@FunctionalInterface
	public interface CommandExecutable<T extends CommandSender> {
		boolean execute(T sender, String[] args);
	}
}
