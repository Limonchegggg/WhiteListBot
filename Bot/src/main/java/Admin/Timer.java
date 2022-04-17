package Admin;

import java.util.Map.Entry;
import org.bukkit.scheduler.BukkitRunnable;

import Main.Main;

public class Timer {
	private Main main = Main.getPlugin(Main.class);
	public void createTimer() {
		new BukkitRunnable() {
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void run() {
				if(main.ban_list.isEmpty()) return;
				for(Entry<String, Integer> key : main.ban_list.entrySet()) {
					main.ban_list.replace(key.getKey(), main.ban_list.get(key)-1);
					if(main.ban_list.get(key) == 0) {
						main.ban_list.remove(key);
						main.jda.getJDA().getUserById(main.data.getIdFromName(key.getKey())).openPrivateChannel().queue(channel ->{
							channel.sendMessage("Поздравляю тебя разбанили! Теперь ты можешь играть на сервере!");
						});
					}
				}
			}
		}.runTaskTimer(main, 1000, 1000);
	}
}
