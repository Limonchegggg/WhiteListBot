package Survival.Events;


import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import Survival.Mechanics.Items.Item;
import net.md_5.bungee.api.ChatColor;

public class BalanceFixEvents implements Listener{
	@EventHandler
	public void StopVillager(VillagerCareerChangeEvent e) {
		Villager vil = e.getEntity();
		vil.setVillagerExperience(1);
	}
	@EventHandler
	public void BabyCareer(EntityTransformEvent e) {
		if(!(e.getTransformedEntity() instanceof Villager)) return;
		Villager vil = (Villager) e.getTransformedEntity();
		if(e.getTransformReason() == TransformReason.CURED) {
			Villager v = (Villager) vil.getWorld().spawnEntity(vil.getLocation(), EntityType.VILLAGER);
			vil.remove();
			v.setBaby();
			return;
		}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void FleshEat(PlayerItemConsumeEvent e) {
		Lvl lvl = new Lvl();
		Item it = new Item();
		Player player = e.getPlayer();
		ItemStack hand = e.getItem();
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
		switch(e.getItem().getType()) {
		case PORKCHOP:
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0, false));
			return;
		case COD:
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0, false));
			return;
		case CHICKEN:
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0, false));
			return;
		case RABBIT:
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0, false));
			return;
		case BEEF:
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0, false));
			return;
		case MUTTON:
			player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0, false));
			return;
		default:
			return;
		}
	}
}
