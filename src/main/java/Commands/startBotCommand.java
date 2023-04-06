package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Bot.ShybkaBot;
import Config.MainConfig;
import Config.MinecraftConfig;
import TextForce.Text;

public class startBotCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("ShybkaBot.start")) {
			sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("noPermissonMessage")));
			return false;
		}
		ShybkaBot.Start(MainConfig.get().getString("BotKey"));
		sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("botStartedMessage")));
		return false;
	}

}
