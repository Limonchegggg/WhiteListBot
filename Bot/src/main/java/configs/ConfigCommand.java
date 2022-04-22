package configs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.yaml.snakeyaml.error.YAMLException;

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
		DiscordData.save();
		DiscordData.reload();
		Players.save();
		Players.reload();
		sender.sendMessage(ChatColor.GRAY + "Конфиги перезагружены");
		}catch(YAMLException e) {
			sender.sendMessage(ChatColor.RED + "Ошибка перезагрузки конфигов");
			new Logging().Log(e.getLocalizedMessage());
		}
		return false;
	}

}
