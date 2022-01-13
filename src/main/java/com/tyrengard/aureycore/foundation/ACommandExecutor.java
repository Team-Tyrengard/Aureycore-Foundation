package com.tyrengard.aureycore.foundation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.tyrengard.aureycore.foundation.internal.Trie;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public abstract class ACommandExecutor implements TabExecutor {
	private final CommandDeclaration<CommandSender> baseCommand;
	private final HashMap<String, Trie<CommandDeclaration<CommandSender>>> regularCommands = new HashMap<>();
	private final HashMap<String, Trie<CommandDeclaration<Player>>> playerCommands = new HashMap<>();
	
	protected ACommandExecutor() {
		this(null);
	}
	protected ACommandExecutor(CommandDeclaration<CommandSender> baseCommand) {
		this.baseCommand = baseCommand;
	}
	
	protected final void addRegularCommand(CommandDeclaration<CommandSender> cd) {
		this.regularCommands.putIfAbsent(cd.getMainCommand(), new Trie<>());
		this.regularCommands.get(cd.getMainCommand()).add(cd.getSubCommand(), cd);
	}
	protected final void addPlayerCommand(CommandDeclaration<Player> cd) {
		this.playerCommands.putIfAbsent(cd.getMainCommand(), new Trie<>());
		this.playerCommands.get(cd.getMainCommand()).add(cd.getSubCommand(), cd);
	}
	protected final void createHelpCommands(String header, ChatColor baseColor, ChatColor accentColor) {
		for (Entry<String, Trie<CommandDeclaration<Player>>> entry : playerCommands.entrySet()) {
			String mainCommand = entry.getKey();
			Trie<CommandDeclaration<Player>> cdTrie = entry.getValue();
			
			ArrayList<String> mainHelpOutput = new ArrayList<>();
			if (header != null) 
				mainHelpOutput.add(header);
			if (baseCommand != null) 
				mainHelpOutput.add("" + accentColor + "\u2022" + mainCommand + ChatColor.WHITE + " - " 
					+ baseCommand.getShortDescription());
			mainHelpOutput.add("" + accentColor + "\u2022" + " /" + mainCommand + " help, ?" + ChatColor.GRAY + 
				" - Shows this help menu");
			mainHelpOutput.add("" + accentColor + "\u2022" + " /" + mainCommand + " [subcommand] help, ?" + ChatColor.GRAY + 
					" - Shows command info");
			
			for (CommandDeclaration<Player> cd : cdTrie.getValues()) {
				if (cd.getShortDescription() != null) {
					mainHelpOutput.add("" + accentColor + "\u2022" + " /" +  cd.getFullCommand() + ChatColor.GRAY + " - " + 
						cd.getShortDescription());
					
					if (cd.getFullDescription() != null) {
						ArrayList<String> specificHelpOutput = new ArrayList<>();
						specificHelpOutput.addAll(Arrays.asList(
							baseColor + "Command: " + accentColor + " /" + cd.getFullCommand(),
							baseColor + "Description: " + accentColor + cd.getShortDescription()
						));
						specificHelpOutput.addAll(Arrays.asList(cd.getFullDescription()));
						CommandDeclaration<Player> specificHelpCD = new CommandDeclaration<Player>((p, args) -> {
							p.sendMessage(specificHelpOutput.toArray(new String[specificHelpOutput.size()]));
							return true;
						});
						cdTrie.add(cd.getSubCommand() + " help", specificHelpCD);
						cdTrie.add(cd.getSubCommand() + " ?", specificHelpCD);
					}
				}
			}
			
			CommandDeclaration<Player> generalHelpCD = new CommandDeclaration<>((p, args) -> {
				p.sendMessage(mainHelpOutput.toArray(new String[mainHelpOutput.size()]));
				return true;
			});
			cdTrie.add("help", generalHelpCD);
			cdTrie.add("?", generalHelpCD);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandDeclaration.CommandExecutable<CommandSender> executable = null;
		CommandDeclaration.CommandExecutable<Player> playerExecutable = null;
		
		if (args.length == 0) {
			if (baseCommand != null) executable = baseCommand.getExecutable();
		} else if (sender instanceof Player) {
			Trie<CommandDeclaration<Player>> pcTrie = playerCommands.get(cmd.getName());
			if (pcTrie != null) {
				CommandDeclaration<Player> cd = pcTrie.get(String.join(" ", args));
				if (cd != null)
					playerExecutable = cd.getExecutable();
			}
		}
		
		if (executable == null) {
			Trie<CommandDeclaration<CommandSender>> rcTrie = regularCommands.get(cmd.getName());
			if (rcTrie != null) {
				CommandDeclaration<CommandSender> cd = rcTrie.get(String.join(" ", args));
				if (cd != null)
					executable = cd.getExecutable();
			}
		}
		
		if (playerExecutable != null) return playerExecutable.execute((Player) sender, args);
		else if (executable != null) return executable.execute(sender, args);
		else return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		String fullArgs = String.join(" ", args);
		int lastIndexOfSpace = fullArgs.lastIndexOf(' ');
		String formedArgs = lastIndexOfSpace == -1 ? "" : fullArgs.substring(0, lastIndexOfSpace + 1);
		
		List<String> output = regularCommands.get(cmd.getName()) == null ? null 
				: regularCommands.get(cmd.getName()).getKeys(args.length == 0 ? null : fullArgs);
		
		if (sender instanceof Player) {
			List<String> playerCommandStrings = playerCommands.get(cmd.getName()) == null ? null
				: playerCommands.get(cmd.getName()).getKeys(args.length == 0 ? null : fullArgs);
			
			if (output != null) {
				if (playerCommandStrings != null) output.addAll(playerCommandStrings);
			} else output = playerCommandStrings;
		}
		
		if (output != null)
			return output.stream().map(k -> k.replaceFirst(formedArgs, "")).filter(k -> !k.contains(" ")).sorted()
					.collect(Collectors.toList());
		else 
			return null;
	}
}
