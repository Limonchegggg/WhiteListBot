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
import bot.DiscordData;
import configs.PlayersGetter;
import console.Logging;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

public class TeleMessageMinecraft implements Listener{
	private HashMap<String, String> message = new HashMap<String,String>();
	private HashMap<String, Integer> times = new HashMap<String, Integer>();
	private String messages;
	private Main plugin = Main.getPlugin(Main.class);
	private Logging log = new Logging();
	@EventHandler
		public void getChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		String messages = e.getMessage();
		if(messages.contains("@")) {
			messages = messages.replace('@', '\0');
		}
		//Мут
		if(plugin.mute_list.containsKey(player.getName())) {
			player.sendMessage(ChatColor.GRAY + "У вас мут, вы не можете писать!");
			e.setCancelled(true);
			return;
		}
		
		String[] args = messages.split(" ");
		for(int i=0; i<args.length;i++) {
			if(DiscordData.get().getStringList("BlackWords").contains(args[i])) {
				player.sendMessage(ChatColor.GRAY + "В сообщении есть запрещенное слово!");
				e.setCancelled(true);
				return;
			}
		}
		
		//Проверка на спам
		if(message.get(player.getName()).contains(messages)) {
			int time = times.get(player.getName())+1;
			times.replace(player.getName(), time);
			player.sendMessage(ChatColor.GRAY + "Отдохни");
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
					log.Log("[Локал] " + player.getName() + ": " + e.getMessage());
					cancel();
				}
			}.runTask(plugin);
			this.messages = messages;
			return;
		}else
		symbol[0] = '\0';
		messages = symbol.toString();
		this.messages = messages;
		for(Player chat : Bukkit.getOnlinePlayers()) {
			chat.sendMessage(ChatColor.GOLD + "[Глобал] " + ChatColor.WHITE + player.getName() + ":" + messages);
		}
		log.Log("[Глобал] " + player.getName() + ":" + messages);
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
	
	public String getMessage() {
		return messages;
	}
}