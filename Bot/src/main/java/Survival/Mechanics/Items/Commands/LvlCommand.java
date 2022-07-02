package Survival.Mechanics.Items.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import Api.ConfigCreator;
import Survival.Mechanics.CommandLevel;
import net.md_5.bungee.api.ChatColor;

public class LvlCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("bwt.commands")) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав для этого!");
			return false;
		}
		if(args.length < 1) {
			sender.sendMessage(ChatColor.GRAY + "/lvlcommand add/remove <команда> <лвл>");
			return false;
		}
		CommandLevel cl = new CommandLevel();
		switch(args[0]) {
		case "add":
			cl.addComand(args[1], Integer.parseInt(args[2]));
			sender.sendMessage(ChatColor.GRAY + "Вы добавили команду");
			return false;
		case "remove":
			cl.removeCommand(label);
			sender.sendMessage(ChatColor.GRAY + "Вы убрали команду");
			return false;
		default :
			sender.sendMessage(ChatColor.GRAY + "/lvlcommand add/remove <команда> <лвл>");
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = Arrays.asList("add","remove");
		List<String> nums = Arrays.asList("1","2","3","4","5","6","7","8","9");
		
		
		switch(args.length) {
		case 1:
			return commands;
		case 2:
			if(args[0].equalsIgnoreCase("remove")) {
				List<String> coms = new ArrayList<String>();
				for(int i=0; i<ConfigCreator.getConfigList("command").size(); i++) {
					coms.add(ConfigCreator.getConfigList("command").get(i).replace(".yml", ""));
				}
				return coms;
			}
		case 3:
			return nums;
		}
		
		return null;
	}

}
