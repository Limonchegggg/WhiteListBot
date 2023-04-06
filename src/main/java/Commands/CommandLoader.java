package Commands;

import Main.Main;

public class CommandLoader {
	Main plugin;
	
	public CommandLoader(Main plugin) {
		this.plugin = plugin;
		
		plugin.getServer().getPluginCommand("botReload").setExecutor(new reloadCommand());
		plugin.getServer().getPluginCommand("botReload").setTabCompleter(new reloadCommand());
		plugin.getServer().getPluginCommand("botStart").setExecutor(new startBotCommand());
		plugin.getServer().getPluginCommand("botStop").setExecutor(new stopBotCommand());
	}
}
