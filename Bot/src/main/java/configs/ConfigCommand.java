package configs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.yaml.snakeyaml.error.YAMLException;

import Admin.BanData;
import bot.DiscordData;
import console.Logging;
import net.md_5.bungee.api.ChatColor;

public class ConfigCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
		if(!sender.hasPermission("bwt.configs")) {
			return false;
		}
		try {
			switch(args[0]) {
			case "DiscrodData":
				DiscordData.save();
				sender.sendMessage(ChatColor.GRAY + "Конфиг перезагружен");
				return false;
			case "BanData":
				BanData.save();
				sender.sendMessage(ChatColor.GRAY + "Конфиг перезагружен");
				return false;
			case "BotConfig":
				Players.save();
				sender.sendMessage(ChatColor.GRAY + "Конфиг перезагружен");
				return false;
			default:
				sender.sendMessage(ChatColor.GRAY + "[DiscrodData/BanData/BotConfig]");
				return false;
			}
		}catch(YAMLException e) {
			sender.sendMessage(ChatColor.RED + "Ошибка перезагрузки конфигов");
			new Logging().Log(e.getLocalizedMessage());
		}
		return false;
	}
}
