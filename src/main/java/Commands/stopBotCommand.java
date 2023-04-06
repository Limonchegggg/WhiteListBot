package Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Bot.ShybkaBot;
import Config.MinecraftConfig;
import TextForce.Text;

public class stopBotCommand implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("ShybkaBot.stop")) {
			sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("noPermissonMessage")));
			return false;
		}
		ShybkaBot.Stop();
		sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("botStoppedMessage")));
		return false;
	}
}
