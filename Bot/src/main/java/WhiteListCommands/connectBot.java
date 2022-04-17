package WhiteListCommands;

import java.util.List;

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
		if(!sender.hasPermission("bwt.bot")) return false;
		if(args.length < 1) return false;
		if(args.length == 0) {
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
		case "wrongWord":
			List<String> word = DiscordData.get().getStringList("BlackWords");
			switch(args[1]) {
			case "add":
				word.add(args[2]);
				DiscordData.get().set("BlackWords", word);
				DiscordData.save();
				DiscordData.reload();
				sender.sendMessage(ChatColor.GRAY + "Слово " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + " было добавлено в черный список");
				return false;
			case "remove":
				word.remove(args[2]);
				DiscordData.get().set("BlackWords", word);
				DiscordData.save();
				DiscordData.reload();
				sender.sendMessage(ChatColor.GRAY + "Слово " + ChatColor.YELLOW + args[2] + ChatColor.GRAY + " было убрано из черного списка");
				return false;
			default:
				sender.sendMessage(ChatColor.GRAY + "/bot wrongWord [add/remove]");
				return false;
			}
		default:
			sender.sendMessage(ChatColor.GRAY + "/bot [stop/start/restart/reloadData/reloadDiscordData/wrongWord]");
			return false;
		}
	}

}
