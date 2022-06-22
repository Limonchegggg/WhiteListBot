package Survival.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
	@EventHandler
	public void FleshEat(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
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
