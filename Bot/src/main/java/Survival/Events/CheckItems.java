package Survival.Events;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Api.ConfigCreator;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import Survival.Mechanics.Items.Item;
import net.md_5.bungee.api.ChatColor;

public class CheckItems implements Listener{

	@EventHandler(priority =  EventPriority.NORMAL)
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
		if(it.IsBlackListed(e.getBlock().getType().name())) {
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
			ArrayList<String> list = ConfigCreator.getConfigList("items" + File.separator);
			for(int i=0; i<list.size(); i++) {
				if(ConfigCreator.get("items"+ File.separator +list.get(i)).getInt("lvlUse") == lvl.getLvl(player.getName(), Category.Digging.getTitle())) {
					String item = list.get(i).replace(".yml", "");
					player.sendMessage(ChatColor.GRAY + item);
				}
			}
			player.sendMessage(ChatColor.GREEN + "-------------------------------");
		}
		
	}
	
	@EventHandler(priority =  EventPriority.NORMAL)
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
		if(it.IsBlackListed(e.getBlock().getType().name())) {
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
		lvl.addExperience(5, player.getName(), Category.Digging.getTitle());
		
		if(lvl.getExperience(player.getName(), Category.Digging.getTitle()) >= lvl.getExpGoal(player.getName(), Category.Digging.getTitle())) {
			lvl.lvlUp(player.getName(), Category.Digging.getTitle(), 1.20);
			player.sendMessage(ChatColor.GRAY + "Вы увеличели уровень! Теперь он равен " + lvl.getLvl(player.getName(), Category.Digging.getTitle()));
			player.sendMessage(ChatColor.GREEN + "------Теперь вам доступно------");
			ArrayList<String> list = ConfigCreator.getConfigList("items"+ File.separator);
			for(int i=0; i<list.size(); i++) {
				if(ConfigCreator.get("items"+ File.separator +list.get(i)).getInt("lvlUse") == lvl.getLvl(player.getName(), Category.Digging.getTitle())) {
					String item = list.get(i).replace(".yml", "");
					player.sendMessage(ChatColor.GRAY + item);
				}
			}
			player.sendMessage(ChatColor.GREEN + "-------------------------------");
		}
	}
	
	@EventHandler(priority =  EventPriority.LOW)
	public void DamageItems(PlayerItemDamageEvent e) {
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
	public void GlideEvent(EntityToggleGlideEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = (Player) e.getEntity();
		ItemStack hand = player.getInventory().getChestplate();
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
	public void SwordUse(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) return;
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = (Player) e.getDamager();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand == null) {
			return;
		}
		if(hand.getType().isAir()) {
			return;
		}
		if(it.IsBlackListed(hand.getType().name())) {
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
	
	@EventHandler
	public void SmithingCancelled(PrepareSmithingEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		if(e.getViewers() == null) return;
		List<HumanEntity> players =  e.getViewers();
		ItemStack hand = e.getResult();
		if(hand == null) {
			return;
		}
		if(hand.getType().isAir()) {
			return;
		}
		if(!it.isExistItem(hand.getType().name())) {
			return;
		}
		for(int i=0; i<players.size(); i++) {
			Player player = (Player) players.get(i);
			if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(hand.getType().name())) {
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GRAY + "Необходимый уровень " + it.getLvl(hand.getType().name()));
				lore.add(ChatColor.GRAY + "Пропишите /stats для проверки уровня");
				ItemMeta m = hand.getItemMeta();
				m.setLore(lore);
				hand.setItemMeta(m);
				e.setResult(hand);
				
				player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
				return;
			}
		}
	}
	@EventHandler
	public void SmithCancel(SmithItemEvent e) {
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
	
	@EventHandler
	public void Info(PrepareItemCraftEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		if(e.getViewers() == null) return;
		List<HumanEntity> players =  e.getViewers();
		if(e.getRecipe() == null) return;
		ItemStack hand = e.getRecipe().getResult();
		if(hand == null) {
			return;
		}
		if(hand.getType().isAir()) {
			return;
		}
		if(!it.isExistItem(hand.getType().name())) {
			return;
		}
		for(int i=0; i<players.size(); i++) {
			Player player = (Player) players.get(i);
			if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(hand.getType().name())) {
				ArrayList<String> lore = new ArrayList<String>();
				lore.add(ChatColor.GRAY + "Необходимый уровень " + it.getLvl(hand.getType().name()));
				lore.add(ChatColor.GRAY + "Пропишите /stats для проверки уровня");
				ItemMeta m = hand.getItemMeta();
				m.setLore(lore);
				hand.setItemMeta(m);
				e.getInventory().setResult(hand);
				
				return;
			}
		}
	}
	@EventHandler
	public void TakeMobs(PlayerInteractEntityEvent e) {
		try {
			if(e.getRightClicked() == null) return;
			if(e.getRightClicked().getType() == EntityType.ITEM_FRAME) return;
			if(e.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
			LivingEntity ent = (LivingEntity) e.getRightClicked();
			Player player = e.getPlayer();
			player.addPassenger(ent);
		}catch(Exception ee) {
			
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerRemovePass(PlayerToggleSneakEvent e) {
		try {
			if(e.getPlayer().getPassenger() != null) {
				e.getPlayer().removePassenger(e.getPlayer().getPassenger());
			}
		}catch(Exception ee) {
			
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void PlayerMoveMob(PlayerMoveEvent e) {
		try {
			if(e.getPlayer().getPassenger() != null) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 1, true));
			}
		}catch(Exception ee) {
			
		}
	}
	
	
}