package Admin;

import java.util.Map.Entry;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import Database.DataBaseType;
import Database.mongoDB.MongoDbTables.Collection;
import Main.Main;
import configs.Players;
import net.md_5.bungee.api.ChatColor;

public class Timer {
	private Main main = Main.getPlugin(Main.class);
	private int i = 0;
	private BanData bd = new BanData();
	public Timer() {
		new BukkitRunnable() {
			@Override
			public void run() {
				i++;
					for(Entry<String, Integer> key : main.ban_list.entrySet()) {
						main.ban_list.replace(key.getKey(), key.getValue()-1);
						if(key.getValue() <= 0) {
							
							switch(DataBaseType.getByName(Players.get().getString("DatabaseType"))) {
							case All:
								main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(key.getKey())).queue((channel) ->{
									channel.sendMessage("Поздравляю тебя разбанили! Теперь ты можешь играть на сервере!").queue();
								});
								break;
							case MongoDB:
								Document doc = main.mongoTables.getDocument("NickName", key.getKey(), Collection.WhiteList);
								main.jda.getJDA().openPrivateChannelById(doc.getString("_id")).queue((channel) ->{
									channel.sendMessage("Поздравляю тебя разбанили! Теперь ты можешь играть на сервере!").queue();
								});
								break;
							case MySQL:
								main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(key.getKey())).queue((channel) ->{
									channel.sendMessage("Поздравляю тебя разбанили! Теперь ты можешь играть на сервере!").queue();
								});
								break;
							case None:
								break;
							default:
								break;
							
							}
							main.ban_list.remove(key.getKey());
						}
					}
					for(Entry<String, Integer> key : main.mute_list.entrySet()) {
						main.mute_list.replace(key.getKey(), key.getValue()-1);
						if(key.getValue() <= 0) {
							if(Bukkit.getPlayer(key.getKey()) != null) {
								Bukkit.getPlayer(key.getKey()).sendMessage(ChatColor.GREEN + "Вы были размучены");
							}
							main.mute_list.remove(key.getKey());
						}
					}
				if(i==30) {
					bd.saveBans();
					bd.saveMute();
					i=0;
				}
			}
		}.runTaskTimer(main, 20L, 20L);
	}
}
