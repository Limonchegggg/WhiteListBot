package Survival.Events;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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
import Main.Main;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import Survival.Mechanics.Items.Item;
import Survival.Mechanics.Items.Med;
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
			
			ArrayList<String> coms = ConfigCreator.getConfigList("command"+File.separator);
			for(int i=0; i<coms.size(); i++) {
				if(ConfigCreator.get("command"+File.separator + coms.get(i)).getInt("Lvl") == lvl.getLvl(player.getName(), Category.Digging.getTitle())) {
					String com = coms.get(i).replace(".yml", "");
					player.sendMessage(ChatColor.GRAY + "/" + com);
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
			
			ArrayList<String> coms = ConfigCreator.getConfigList("command"+File.separator);
			for(int i=0; i<coms.size(); i++) {
				if(ConfigCreator.get("command"+File.separator + coms.get(i)).getInt("Lvl") == lvl.getLvl(player.getName(), Category.Digging.getTitle())) {
					String com = coms.get(i).replace(".yml", "");
					player.sendMessage(ChatColor.GRAY + "/" + com);
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
	
	@EventHandler(priority = EventPriority.LOWEST)
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
		if(!it.isExistItem(hand.getType().name()))  return;
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
	public void ThrowEvent(ProjectileLaunchEvent e) {
		if(!(e.getEntity().getShooter() instanceof Player)) return;
		Player player = (Player) e.getEntity().getShooter();
		if(player.getInventory().getItemInMainHand().getItemMeta().getLore() == null) return;
		switch(e.getEntity().getType()) {
		case SNOWBALL:
			if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains("Запускает игрока")) {
				e.getEntity().addPassenger(player);
			}
			return;
		default:
			return;
		}
	}
	@EventHandler
	public void TakeMobs(PlayerInteractEntityEvent e) {
		try {
			if(e.getRightClicked() == null) return;
			LivingEntity ent = (LivingEntity) e.getRightClicked();
			Player player = e.getPlayer();
			switch(e.getRightClicked().getType()) {
			case PLAYER:
				
				if(player.getPassengers().size() >= 1) return;
				if(!player.getInventory().getHelmet().getType().equals(Material.SADDLE)) return;
				player.addPassenger(ent);
				return;
			case ITEM_FRAME:
				return;
			default:
				
				if(player.getPassengers().size() >= 1) return;
				if(!player.isSneaking()) return;
				player.addPassenger(ent);
				return;
			}
			
		}catch(Exception ee) {
			
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerRemovePass(PlayerToggleSneakEvent e) {
		try {
			if(e.getPlayer().isSneaking()) return;
			if(e.getPlayer().getPassengers().size() > 0) {
				e.getPlayer().removePassenger(e.getPlayer().getPassenger());
			}
		}catch(Exception ee) {
			
		}
	}
	@EventHandler
	public void moveWithMobs(PlayerMoveEvent e) {
		if(e.getPlayer().getPassengers().size() > 0) {
			e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 1, true));
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void CheckArmor(PlayerMoveEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = e.getPlayer();
		int i = 0+(int)(Math.random()*5);
		switch(i) {
		case 1:
			ItemStack helmet = player.getInventory().getHelmet();
			if(helmet == null) {
				return;
			}
			if(helmet.getType().isAir()) {
				return;
			}
			if(!it.isExistItem(helmet.getType().name())) {
				return;
			}
			if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(helmet.getType().name())) {
				player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
				player.getWorld().dropItemNaturally(player.getLocation(), helmet);
				player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
				return;
			}
			break;
		case 2:
			ItemStack chestplate = player.getInventory().getChestplate();
			if(chestplate == null) {
				return;
			}
			if(chestplate.getType().isAir()) {
				return;
			}
			if(!it.isExistItem(chestplate.getType().name())) {
				return;
			}
			if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(chestplate.getType().name())) {
				player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
				player.getWorld().dropItemNaturally(player.getLocation(), chestplate);
				player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
				return;
			}
			break;
		case 3:
			ItemStack leggings = player.getInventory().getLeggings();
			if(leggings == null) {
				return;
			}
			if(leggings.getType().isAir()) {
				return;
			}
			if(!it.isExistItem(leggings.getType().name())) {
				return;
			}
			if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(leggings.getType().name())) {
				player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
				player.getWorld().dropItemNaturally(player.getLocation(), leggings);
				player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
				return;
			}
			break;
		case 4:
			ItemStack boots = player.getInventory().getBoots();
			if(boots == null) {
				return;
			}
			if(boots.getType().isAir()) {
				return;
			}
			if(!it.isExistItem(boots.getType().name())) {
				return;
			}
			if(lvl.getLvl(player.getName(), Category.Digging.getTitle()) < it.getLvl(boots.getType().name())) {
				player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
				player.getWorld().dropItemNaturally(player.getLocation(), boots);
				player.sendMessage(ChatColor.GRAY + "У вас недостаточный уровень");
				return;
			}
			break;
		default:
			break;
		}
		}
	
	@EventHandler
	public void checkPerks(PlayerMoveEvent e) {
		Player pl = e.getPlayer();
		
		ItemStack item = pl.getInventory().getItem(8);
		
		if(item == null) return;
		if(item.getType().equals(Material.AIR)) return;
		if(item.getItemMeta().getLore() == null) return;
		Main main = Main.getPlugin(Main.class);
		try {
			Med m = new Med();
			
			List<String> lore = item.getItemMeta().getLore();
		
			for(int i=0; i<lore.size(); i++) {
				pl.addPotionEffect(new PotionEffect(PotionEffectType.getByName(main.perks.get(m.getPerk(lore, i))), 100, (m.getPerkBuff(lore, i)-1)));
			}
		}catch(Exception ee) {
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void checkKilledMob(EntityDeathEvent e) {
		if(!(e.getEntity().getKiller() instanceof Player)) return;
		Med m = new Med();
		if(!m.containsMob(e.getEntity().getType())) return;
		int chance = 0+(int)(Math.random()*ConfigCreator.get("MedSettings.yml").getInt("MobChanceDrop"));
			//Проверка выпадения шанса и и его смерение
		if(chance < Integer.parseInt(ConfigCreator.get("MedSettings.yml").getConfigurationSection("Mobs_Chances").getString(e.getEntity().getType().name()).split("-")[0]) 
					&& chance > Integer.parseInt(ConfigCreator.get().getConfigurationSection("Mobs_Chances").getString(e.getEntity().getType().name()).split("-")[1])) {
			e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), m.drop());
		}
	}
	
}