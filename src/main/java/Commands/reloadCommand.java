package Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import Bot.ShybkaBot;
import Config.MainConfig;
import Config.MinecraftConfig;
import TextForce.Text;

public class reloadCommand implements CommandExecutor, TabCompleter{
	
	private String[] configArgs = {"mainConfig", "minecraftConfig"};
	private String[] args = {"config", "bot"};

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("ShybkaBot.reload")) {
			sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("noPermissonMessage")));
			return false;
		}
		switch(args[0]) {
			case "config":
				switch(args[2]) {
					case "mainConfig":
						MainConfig.loadConfig();
						sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("configReloadMessage").replace("%config%", args[2])));
						return false;
					case "minecraftConfig":
						MinecraftConfig.loadConfig();
						sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("configReloadMessage").replace("%config%", args[2])));
						return false;
					default: 
						sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("invalidArguments").replace("%args%", ""+this.configArgs)));
						return false;
				}
			case "bot":
				ShybkaBot.Stop();
				ShybkaBot.Start(MainConfig.get().getString("BotKey"));
				sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("restartBotMessage")));
				return false;
			default:
				sender.sendMessage(new Text().ColorizeText(MinecraftConfig.get().getString("invalidArguments").replace("%args%", ""+this.args)));
				return false;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			List<String> ar = new ArrayList<String>();
			for(String s : this.args) {
				ar.add(s);
			}
			return ar;
		}
		if(args[1].equalsIgnoreCase("mainConfig")) {
			List<String> ar = new ArrayList<String>();
			for(String s : this.configArgs) {
				ar.add(s);
			}
			return ar;
		}
		return null;
	}

}
