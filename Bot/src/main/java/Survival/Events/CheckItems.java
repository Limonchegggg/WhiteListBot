package Survival.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;


import Main.Main;
import Survival.SurvivalMain;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import Survival.Mechanics.Items.Item;

public class CheckItems implements Listener{
	private Item item = new Item();
	private Lvl lvl = new Lvl();
	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void CheckItem(BlockBreakEvent e) {
		SurvivalMain sm = new SurvivalMain(main);
		Player player = e.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand == null) {
			e.setCancelled(true);
			return;
		}
		switch(sm.player_category.get(player.getName())) {
		case "Digging":
			item.setPath("items\\"+hand.getType().name()+".yml");
			lvl.setPath("players\\"+player.getName()+"yml");
			if(item.getLvluse() != lvl.getLvl("Digging")) {
				e.setCancelled(true);
				player.sendMessage("У вас недостаточный уровень для использования этого предмета");
			}
			break;
		
		default:
			break;
		}
	}
	@EventHandler
	public void ChangeCategory(PlayerItemHeldEvent e) {
		SurvivalMain sm = new SurvivalMain(main);
		String category;
		switch(e.getPlayer().getInventory().getItemInMainHand().getType()) {
			case WOODEN_PICKAXE:
				category = Category.Digging.getTitle();
				sm.player_category.replace(e.getPlayer().getName(),sm.player_category.get(e.getPlayer().getName()) ,category);
				break;
			case STONE_PICKAXE:
				category = Category.Digging.getTitle();
				sm.player_category.replace(e.getPlayer().getName(),sm.player_category.get(e.getPlayer().getName()) ,category);
				break;
			case IRON_PICKAXE:
				category = Category.Digging.getTitle();
				sm.player_category.replace(e.getPlayer().getName(),sm.player_category.get(e.getPlayer().getName()) ,category);
				break;
			case GOLDEN_PICKAXE:
				category = Category.Digging.getTitle();
				sm.player_category.replace(e.getPlayer().getName(),sm.player_category.get(e.getPlayer().getName()) ,category);
				break;
			case DIAMOND_PICKAXE:
				category = Category.Digging.getTitle();
				sm.player_category.replace(e.getPlayer().getName(),sm.player_category.get(e.getPlayer().getName()) ,category);
				break;
			case NETHERITE_PICKAXE:
				category = Category.Digging.getTitle();
				sm.player_category.replace(e.getPlayer().getName(),sm.player_category.get(e.getPlayer().getName()) ,category);
				break;
			default:
				category = Category.None.getTitle();
				sm.player_category.replace(e.getPlayer().getName(),sm.player_category.get(e.getPlayer().getName()) ,category);
				break;
		}
	}
}
