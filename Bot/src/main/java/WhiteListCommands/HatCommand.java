package WhiteListCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class HatCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("bwt.hat")) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав для этого!");
			return false;
		}
		
		Player player = (Player) sender;
		if(player.getInventory().getItemInMainHand() == null) {
			sender.sendMessage(ChatColor.GRAY + "Вы должы держать желаемый предмет");
			return false;
		}
		if(player.getInventory().getHelmet() != null) {
			ItemStack helmet = player.getInventory().getHelmet();
			ItemStack item = player.getInventory().getItemInMainHand();
			
			player.getInventory().setItemInMainHand(helmet);
			player.getInventory().setHelmet(item);
			return false;
		}
		player.getInventory().setHelmet(player.getInventory().getItemInMainHand());
		player.getInventory().setItemInMainHand(null);
		
		
		return false;
	}

}
