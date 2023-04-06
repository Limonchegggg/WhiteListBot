package Bot.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import Config.MainConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onlineCommand extends ListenerAdapter{
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(!(event.getMessage().getContentRaw().toLowerCase().equalsIgnoreCase(MainConfig.get().getConfigurationSection("onlineCommand").getString("command").toLowerCase()))) return;
		if(MainConfig.get().getConfigurationSection("onlineCommand").getBoolean("enable") == false) return;
 		if(!(event.getChannel().getId().equalsIgnoreCase(MainConfig.get().getConfigurationSection("onlineCommand").getString("channelId")))) return;
 		EmbedBuilder eb = new EmbedBuilder();
 		eb.setAuthor(MainConfig.get().getConfigurationSection("onlineCommand").getConfigurationSection("embed").getString("author"));
 		eb.setTitle(MainConfig.get().getConfigurationSection("onlineCommand").getConfigurationSection("embed").getString("title"));
 		eb.setFooter(MainConfig.get().getConfigurationSection("onlineCommand").getConfigurationSection("embed").getString("footer"));
 		if(Bukkit.getOnlinePlayers().size() <= 0) {
 			eb.appendDescription(MainConfig.get().getConfigurationSection("onlineCommand").getConfigurationSection("embed").getString("nullOnline"));
 		}else {
 			for(Player name : Bukkit.getOnlinePlayers()) {
 	 			eb.appendDescription(MainConfig.get().getConfigurationSection("onlineCommand").getConfigurationSection("embed").getString("description").replace("%player%", name.getName()));
 	 		}
 		}
 		
 		event.getChannel().sendMessageEmbeds(eb.build()).queue();
 		
 	
	
 }
	

}