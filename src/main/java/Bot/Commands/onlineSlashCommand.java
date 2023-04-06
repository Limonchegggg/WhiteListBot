package Bot.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import Config.MainConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onlineSlashCommand extends ListenerAdapter{
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if(!(event.getCommandId().equalsIgnoreCase(MainConfig.get().getConfigurationSection("onlineCommand").getString("slashCommandID")))) return;
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
