package Survival.Events;


import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import Api.ConfigCreator;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import Survival.Mechanics.Items.Item;
import net.md_5.bungee.api.ChatColor;

public class CheckItems implements Listener{

	@EventHandler
	public void CheckItem(BlockBreakEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = e.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand.getType().isAir()) {
			return;
		}
		if(!it.isExistItem(hand.getType().name())) {
			return;
		}
		if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(hand.getType().name())) {
			e.setCancelled(true);
			player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
			return;
		}
		lvl.addExperience(10, player.getName(), Category.Digging.getTitle());
		player.sendMessage(lvl.getExpGoal(player.getName(), Category.Digging.getTitle()) + ": " + lvl.getExperience(player.getName(), Category.Digging.getTitle()));
		
		if(lvl.getExperience(player.getName(), Category.Digging.getTitle()) >= lvl.getExpGoal(player.getName(), Category.Digging.getTitle())) {
			lvl.lvlUp(player.getName(), Category.Digging.getTitle(), 1.20);
			player.sendMessage(ChatColor.GRAY + "Вы увеличели уровень! Теперь он равен " + lvl.getLvl(player.getName(), Category.Digging.getTitle()));
			player.sendMessage(ChatColor.GREEN + "------Теперь вам доступно------");
			ArrayList<String> list = ConfigCreator.getConfigList("items\\");
			for(int i=0; i<list.size(); i++) {
				if(ConfigCreator.get("items\\"+list.get(i)).getInt("lvlUse") == lvl.getLvl(player.getName(), Category.Digging.getTitle())) {
					String item = list.get(i).replace(".yml", "");
					player.sendMessage(ChatColor.GRAY + item);
				}
			}
			player.sendMessage(ChatColor.GREEN + "-------------------------------");
		}
	}
	
	@EventHandler
	public void CheckItem(BlockPlaceEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = e.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand.getType().isAir()) {
			return;
		}
		if(!it.isExistItem(hand.getType().name())) {
			return;
		}
		if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(hand.getType().name())) {
			e.setCancelled(true);
			player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
			return;
		}
		lvl.addExperience(10, player.getName(), Category.Digging.getTitle());
		player.sendMessage(lvl.getExpGoal(player.getName(), Category.Digging.getTitle()) + ": " + lvl.getExperience(player.getName(), Category.Digging.getTitle()));
		
		if(lvl.getExperience(player.getName(), Category.Digging.getTitle()) >= lvl.getExpGoal(player.getName(), Category.Digging.getTitle())) {
			lvl.lvlUp(player.getName(), Category.Digging.getTitle(), 1.25);
			player.sendMessage(ChatColor.GRAY + "Вы увеличели уровень! Теперь он равен " + lvl.getLvl(player.getName(), Category.Digging.getTitle()));
		}
	}
}
