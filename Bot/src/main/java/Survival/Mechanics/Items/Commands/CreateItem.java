package Survival.Mechanics.Items.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Survival.Mechanics.Items.Category;
import Survival.Mechanics.Items.Item;
import net.md_5.bungee.api.ChatColor;

public class CreateItem implements CommandExecutor,TabCompleter{

	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("bwt.survival.createItem")) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав для этоо!");
			return false;
		}
		Item it = new Item();
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInMainHand();
		if(item == null) {
			player.sendMessage(ChatColor.GRAY + "Вы должны держать необходимый предмет в руке");
			return false;
		}
		String name = item.getItemMeta().getDisplayName();
		String ID = item.getType().name();
		List<String> lore = item.getItemMeta().getLore();
		short durability = item.getDurability();
		
		it.CreateCategoria(ID, name, lore, durability, Category.Digging.getTitle(), durability);
		player.sendMessage("Предмет добавлен, Теперь необходимо настроить его конфиг");
		player.sendMessage("После этого нужно перезапустить сервер");
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
