package WhiteListCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Main;
import net.md_5.bungee.api.ChatColor;

public class vanish implements CommandExecutor{
	private Main main = Main.getPlugin(Main.class);
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender senderCommand, Command command, String string, String[] args) {
		if(!senderCommand.hasPermission("bwt.vanish")) {
			senderCommand.sendMessage(ChatColor.RED + "У вас нет прав для этого!");
			return false;
		}
		Player sender = (Player) senderCommand;
		if(main.vanish.contains(sender.getName())) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(sender);
			}
			main.vanish.remove(sender.getName());
			sender.sendMessage(ChatColor.GRAY + "Ваниш выключен");
			System.out.println(sender.getName() + " выключил ваниш");
			return false;
		}
		main.vanish.add(sender.getName());
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!player.hasPermission("bwt.admin")) {
				player.hidePlayer(sender);
			}
		}
		sender.sendMessage(ChatColor.GRAY + "Ваниш " + ChatColor.GREEN + "включен");
		System.out.println(sender.getName() + " включил ваниш");
		return false;
	}
}
