package Survival.Mechanics.Items.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import net.md_5.bungee.api.ChatColor;

public class getLvl implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("bwt.stats")) {
			sender.sendMessage(ChatColor.GRAY + "У вас нет прав");
			return false;
		}
		Player player = (Player) sender;
		
		Lvl lvl = new Lvl();
		
		player.sendMessage(ChatColor.GREEN + "Ваш уровень равен " + ChatColor.GOLD + lvl.getLvl(player.getName(), Category.Digging.getTitle()));
		player.sendMessage(ChatColor.GREEN + "Ваш прогресс уровня " + ChatColor.GOLD + lvl.getExperience(player.getName(), Category.Digging.getTitle()) + "/" + lvl.getExpGoal(player.getName(), Category.Digging.getTitle()));
		return false;
	}

}
