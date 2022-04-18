package Admin;

import java.util.Map.Entry;
import org.bukkit.scheduler.BukkitRunnable;

import Main.Main;

public class Timer {
	private Main main = Main.getPlugin(Main.class);
	private int i = 0;
	private BanData bd = new BanData();
	public void createTimer() {
		new BukkitRunnable() {
			@Override
			public void run() {
				i++;
					for(Entry<String, Integer> key : main.ban_list.entrySet()) {
						main.ban_list.replace(key.getKey(), key.getValue()-1);
						if(key.getValue() == 0) {
							main.ban_list.remove(key.getKey());
							main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(main.getName())).queue((channel) ->{
								channel.sendMessage("Поздравляю тебя разбанили! Теперь ты можешь играть на сервере!");
							});
						}
					}
				if(i==60) {
					bd.saveBans();
					i=0;
				}
			}
		}.runTaskTimer(main, 20L, 20L);
	}
}
