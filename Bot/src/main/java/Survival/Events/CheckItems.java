package Survival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


import Main.Main;
import net.md_5.bungee.api.ChatColor;

public class CheckItems implements Listener{
	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void CheckItem(BlockBreakEvent e) {
		Player player = e.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand == null) {
			e.setCancelled(true);
			return;
		}
	/*	if(main.item_lvl.get(hand.getType().name()) < main.player_category.get(player.getName()).get("Digging").get("lvl")) {
	*		player.sendMessage(ChatColor.GRAY + "у вас недостаточный уровень для использования предмета");
	*		e.setCancelled(true);
	*	
	*	}
	*/
	}
}
