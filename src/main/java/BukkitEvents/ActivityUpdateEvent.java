package BukkitEvents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import Bot.ShybkaBot;
import Config.MainConfig;
import TextForce.PlaceHolders;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;

public class ActivityUpdateEvent implements Listener{

	@EventHandler
	public void addPlayer(PlayerJoinEvent e) {
		String activity = MainConfig.get().getConfigurationSection("activity").getString("activity");
		activity = new PlaceHolders().onlinePlayers(activity);
		activity = new PlaceHolders().maxOnlinePlayers(activity);
		ActivityType type = ActivityType.fromKey(MainConfig.get().getConfigurationSection("activity").getInt("activityType"));
		ShybkaBot.getJDA().getPresence().setActivity(Activity.of(type, activity));
	}
	
	@EventHandler
	public void removePlayer(PlayerQuitEvent e) {
		String activity = MainConfig.get().getConfigurationSection("activity").getString("activity");
		activity = new PlaceHolders().onlinePlayers(activity);
		activity = new PlaceHolders().maxOnlinePlayers(activity);
		ActivityType type = ActivityType.fromKey(MainConfig.get().getConfigurationSection("activity").getInt("activityType"));
		ShybkaBot.getJDA().getPresence().setActivity(Activity.of(type, activity));
	}
	
}
