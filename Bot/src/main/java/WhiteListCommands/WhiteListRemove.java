package WhiteListCommands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Main.Main;
import net.md_5.bungee.api.ChatColor;

public class WhiteListRemove implements CommandExecutor{
	private Main plugin = Main.getPlugin(Main.class);
	public boolean onCommand(CommandSender sender, Command com, String label, String[] args) {
		if(!(sender.hasPermission("bwt.admin"))) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав для этого!");
			return false;
		}
		if(args.length < 1 || args.length > 1) { 
			sender.sendMessage(ChatColor.GRAY + "Использование: /removewl <Ник>");
			return false;
			}
		
		plugin.data.removePlayer(args[0]);
		if(Bukkit.getPlayer(args[0]) != null) {
		Bukkit.getPlayer(args[0]).kickPlayer("Вас выгнали из вайтлиста");
		}
		sender.sendMessage(ChatColor.GRAY + "Игрок убран из листа");
		return false;
	}
}
