package Main;


import org.bukkit.plugin.java.JavaPlugin;

import Bot.ShybkaBot;
import BukkitEvents.ActivityUpdateEvent;
import Commands.CommandLoader;
import Config.MainConfig;
import Config.MinecraftConfig;
import TextForce.PlaceHolders;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		MainConfig.loadConfig();
		MinecraftConfig.loadConfig();
		
		new CommandLoader(this);
		
		if(MainConfig.get().getBoolean("enableBot") == true) {
			ShybkaBot.Start(MainConfig.get().getString("BotKey"));
			getServer().getPluginManager().registerEvents(new ActivityUpdateEvent(), this);
		}
		
		if(MainConfig.get().getConfigurationSection("activity").getBoolean("enable") == true) {
			ActivityType type = ActivityType.fromKey(MainConfig.get().getConfigurationSection("activity").getInt("activityType"));
			String activityString = MainConfig.get().getConfigurationSection("activity").getString("activity");
			activityString = new PlaceHolders().onlinePlayers(activityString);
			activityString = new PlaceHolders().maxOnlinePlayers(activityString);
			ShybkaBot.getJDA().getPresence().setActivity(Activity.of(type, activityString));
		}
		

	}
	@Override
	public void onDisable() {
		ShybkaBot.Stop();
	}
	@Override
	public void reloadConfig() {
			ShybkaBot.Stop();
	}
	
}
