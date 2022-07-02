package Survival.Mechanics.Items.Commands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import Survival.Mechanics.CommandLevel;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import net.md_5.bungee.api.ChatColor;

public class EventCommand implements Listener{

	
	@EventHandler
	public void CommandExecute(PlayerCommandPreprocessEvent e) {
		Lvl lvl = new Lvl();
		CommandLevel cl = new CommandLevel();
		if(!cl.ExistCommand(e.getMessage())) return;
		Player player = (Player) e.getPlayer();
		if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < cl.getLvl(e.getMessage())) {
			e.setCancelled(true);
			player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
			return;
		}
	}
	
	
	
}
