package Survival.Events;


import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import Api.ConfigCreator;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import Survival.Mechanics.Items.Item;
import net.md_5.bungee.api.ChatColor;

public class CheckItems implements Listener{

	@EventHandler
	public void BreakItem(BlockBreakEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = e.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand == null) {
			return;
		}
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
	public void PlaceItem(BlockPlaceEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = e.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand == null) {
			return;
		}
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
	public void Bucket(PlayerBucketEmptyEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = e.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand == null) {
			return;
		}
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
	}
	@EventHandler
	public void EquipItem(InventoryClickEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = (Player) e.getWhoClicked();
		ItemStack hand = e.getCurrentItem();
		if(hand == null) {
			return;
		}
		if(hand.getType().isAir()) {
			return;
		}
		if(!it.isExistItem(hand.getType().name())) {
			return;
		}
		switch(e.getInventory().getType()) {
		case CHEST:
			return;
		case BARREL:
			return;
		case DROPPER:
			return;
		case SHULKER_BOX:
			return;
		case DISPENSER:
			return;
		default:
		}
		if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(hand.getType().name())) {
			if(hand.getType().name().contains("HELMET") || hand.getType().name().contains("CHESTPLATE") || hand.getType().name().contains("LEGGINGS") || hand.getType().name().contains("BOOTS")) {
			e.setCancelled(true);
			player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
			}
			return;
		}
	}
	
	@EventHandler
	public void CraftItem(CraftItemEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = (Player) e.getWhoClicked();
		ItemStack hand = e.getCurrentItem();
		if(hand == null) {
			return;
		}
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
	}
	//TODO: Фикс жителей
	@EventHandler
	public void StopVillager() {
		
	}
	
}