package Events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import Main.Main;
import configs.PlayersGetter;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class TeleMessageMinecraft implements Listener{
	private HashMap<String, String> message = new HashMap<String,String>();
	private HashMap<String, Integer> times = new HashMap<String, Integer>();
	private Main plugin = Main.getPlugin(Main.class);
	@EventHandler
		public void getChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		String messages = e.getMessage();
		if(messages.contains("@")) {
			messages = messages.replace('@', '\0');
		}
		//Проверка на спам
		if(message.get(player.getName()).contains(messages)) {
			int time = times.get(player.getName())+1;
			times.replace(player.getName(), time);
			if(time >= 2) {
				player.sendMessage(ChatColor.GRAY + "Отдохни");
				e.setCancelled(true);
				return;
			}
			e.setCancelled(true);
			return;
		}
		e.setCancelled(true);
		message.replace(player.getName(), messages);
		times.replace(player.getName(), 0);
		String[] message_split = messages.split(" ");
		char[] symbol = message_split[0].toCharArray();
		if(symbol[0] != '!') {
			new BukkitRunnable() {
				@Override
				public void run() {
					for(Entity entity : player.getNearbyEntities(200, 200, 200)) {
						if(entity instanceof Player) {
							entity.sendMessage(ChatColor.GREEN + "[Локал] " + ChatColor.WHITE + player.getName() + ": " + e.getMessage());
						}
					}
					player.sendMessage(ChatColor.GREEN + "[Локал] " + ChatColor.WHITE + player.getName() + ": " + e.getMessage());
					cancel();
				}
			}.runTask(plugin);
			return;
		}else
		messages = messages.replace('!', ' ');
		for(Player chat : Bukkit.getOnlinePlayers()) {
			chat.sendMessage(ChatColor.GOLD + "[Глобал] !" + ChatColor.WHITE + player.getName() + ":" + messages);
		}
		//Отправка сообщения в чат дискорд
		Main main = Main.getPlugin(Main.class);
		PlayersGetter pg = new PlayersGetter();
		MessageChannel msgch = main.jda.getJDA().getTextChannelById(pg.getTeleMessageChannelId());
		try {
		msgch.sendMessage("**[Minecraft]** " + player.getName() + ":" + messages).queue();
		}catch(ErrorResponseException e1) {
			System.out.println(e1);
		}
		return;
	}
	@EventHandler
	public void playerAdd(PlayerJoinEvent e) {
		message.put(e.getPlayer().getName(), " ");
		times.put(e.getPlayer().getName(), 0);
	}
	@EventHandler
	public void playerRemove(PlayerQuitEvent e) {
		message.remove(e.getPlayer().getName());
		times.remove(e.getPlayer().getName());
	}
}