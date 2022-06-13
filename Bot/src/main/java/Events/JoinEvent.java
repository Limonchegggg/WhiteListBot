package Events;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import Api.ConfigCreator;
import Main.Main;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import configs.Players;
import configs.PlayersGetter;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;

public class JoinEvent implements Listener{
	private Main plugin = Main.getPlugin(Main.class);
	private PlayersGetter pg = new PlayersGetter();
	@EventHandler
	public void CheckList(PlayerJoinEvent e){
		Player player = e.getPlayer();
		//Проверка игрока в датабазе
			if(!plugin.data.existsPlayer(e.getPlayer().getName())) {
					String msg = Players.get().getString("Kick_Message");
					for(int a = 0; a < msg.length(); a++) {
						msg = ChatColor.translateAlternateColorCodes('&', msg);
					}
					e.setJoinMessage("");
					player.kickPlayer(msg);
					return;
			}
			if(plugin.ban_list.containsKey(e.getPlayer().getName())) {
				player.kickPlayer("Вы временно заблокированы");
				return;
			}
			plugin.jda.getJDA().getPresence().setActivity(Activity.watching(Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + " игроков"));
			MessageChannel msgchannel = plugin.jda.getJDA().getTextChannelById(pg.getTeleMessageChannelId());
			msgchannel.sendMessage("**" + player.getName() + " зашел на сервер**").queue();
			Location loc = player.getLocation();
			if(loc.add(0,1,0).getBlock().getRelative(BlockFace.SELF).getType() == Material.NETHER_PORTAL) {
				plugin.stack_players.put(plugin.data.getIdFromName(player.getName()), player);
				plugin.jda.getJDA().openPrivateChannelById(plugin.data.getIdFromName(player.getName())).queue((channel) -> 
				{
					channel.sendMessage("Похоже ты застрял, давай помогу. Введи [помоги] и я постараюсь тебя вытащить").queue();
				});
			}
		/*	String path = "players\\"+player.getName()+".yml";
		*	Lvl lvl = new Lvl();
		*	lvl.CreateProfile(path);
		*	lvl.addCategoria(Category.Digging.getTitle(), 50);
		*	lvl.LoadLvl(player.getName());
		*/	
			
		}
	@EventHandler
	public void QuitPlaeyr(PlayerQuitEvent e) {
			Player player = e.getPlayer();
			plugin.jda.getJDA().getPresence().setActivity(Activity.watching((Bukkit.getOnlinePlayers().size()-1) + "/" + Bukkit.getMaxPlayers() + " игроков"));
			MessageChannel msgchannel = plugin.jda.getJDA().getTextChannelById(pg.getTeleMessageChannelId());
			msgchannel.sendMessage("**" + player.getName() + " вышел с сервера**").queue();
	}
}