package WhiteListCommands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Main.Main;
import bot.DiscordData;
import configs.Players;

public class connectBot implements CommandExecutor{
	Main main = Main.getPlugin(Main.class);
	@Override
	public boolean onCommand(CommandSender sender, Command com, String str, String[] args) {
		if(!sender.hasPermission("bwt.bot")) { 
			sender.sendMessage(ChatColor.RED + "У вас нет прав для этого!");
			return false;
		}
		if(args.length ==0) {
			sender.sendMessage(ChatColor.GRAY + "/bot [stop/start/restart/reloadData/reloadDiscordData/wrongWord]");
			return false;
		}
		switch(args[0]) {
		case "stop":
			main.jda.stopBot();
			sender.sendMessage(ChatColor.GRAY + "Остановка бота");
			return false;
		case "start":
			main.jda.startbot();
			sender.sendMessage(ChatColor.GRAY + "Запуск бота");
			return false;
		case "restart":
			main.jda.stopBot();
			sender.sendMessage(ChatColor.GRAY + "Рестарт бота бота");
			main.jda.startbot();
			sender.sendMessage(ChatColor.GRAY + "Бот перезапущен");
			return false;
		case "reloadData":
			sender.sendMessage(ChatColor.GRAY + "Основной конфиг перезагружен");
			Players.save();
			Players.reload();
			return false;
		case "reloadDiscordData":
			sender.sendMessage(ChatColor.GRAY + "Дискорд конфиг перезагружен");
			DiscordData.save();
			DiscordData.reload();
			return false;
		default:
			sender.sendMessage(ChatColor.GRAY + "/bot [stop/start/restart/reloadData/reloadDiscordData/wrongWord]");
			return false;
		}
	}

}
