package WhiteListCommands;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Main.Main;
import net.md_5.bungee.api.ChatColor;

public class WhiteListAdd implements CommandExecutor{
	Main plugin = Main.getPlugin(Main.class);
	public boolean onCommand(CommandSender sender, Command com, String label, String[] args) {
		if(!(sender.hasPermission("bwt.admin"))) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав для этого!");
			return false;
		}
		if(args.length == 0) {
			sender.sendMessage(ChatColor.GRAY + "/wladd <Ник> <Дискорд> <Дискорд Ид>");
			return false;
		}
		if(plugin.data.existsPlayer(args[0])) {
			sender.sendMessage(ChatColor.GRAY + "Такой игрока уже есть базе данных!");
			return false;
		}
		plugin.data.cratePlayer(args[0], args[1], args[2]);
		
		sender.sendMessage(ChatColor.GRAY + "Игрок " + args[0] + " был добавлен в вайтлист");
		return false;
		
	}
}
