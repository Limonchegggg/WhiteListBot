package Database;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Main.Main;

public class SqlConnection implements CommandExecutor {

	Main plugin = Main.getPlugin(Main.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
		if(!sender.hasPermission("bwt.admin")) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав!");
		}
		sender.sendMessage(ChatColor.GRAY + "Состояние подключения " + plugin.sql.isConnected());
		if(!plugin.sql.isConnected()) {
			try {
				plugin.sql.connect();
			} catch (ClassNotFoundException | SQLException e) {
				//e.printStackTrace();
			}
			System.out.println("Подключено!");
		}
		return false;
	}
}