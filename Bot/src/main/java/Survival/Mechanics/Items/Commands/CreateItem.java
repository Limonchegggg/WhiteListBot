package Survival.Mechanics.Items.Commands;

import java.util.Arrays;
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

public class CreateItem implements CommandExecutor, TabCompleter{

	
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
		if(args.length < 1) {
			player.sendMessage(ChatColor.GRAY + "Мало аргументов");
			return false;
		}
		if(item == null) {
			player.sendMessage(ChatColor.GRAY + "Вы должны держать необходимый предмет в руке");
			return false;
		}
		switch(args[0]) {
		case "add":
			String ItemName = item.getItemMeta().getDisplayName();
			String ID = item.getType().name();
			List<String> lore = item.getItemMeta().getLore();
			short durability = item.getDurability();
			it.CreateItem(ID, ItemName, lore, durability, Category.Digging.getTitle(), durability);
			player.sendMessage(ChatColor.GRAY + "Предмет добавлен, Теперь необходимо настроить его конфиг");
			player.sendMessage(ChatColor.GRAY + "После этого нужно перезапустить сервер");
			return false;
		case "addBlackList":
			it.addToBlackList(item.getType().name());
			player.sendMessage(ChatColor.GRAY + "Предмет был добавлен в исключение");
			return false;
		case "removeBlackList":
			it.removeToBlackList(item.getType().name());
			player.sendMessage(ChatColor.GRAY + "Предмет был убран из исключения");
			return false;
		}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return Arrays.asList("add", "addBlackList", "removeBlackList");
	}


}
