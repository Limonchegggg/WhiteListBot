package WhiteListCommands;



import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Database.DataBaseType;
import Database.mongoDB.MongoDbTables.Collection;
import Main.Main;
import configs.Players;
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
		
		String name = args[0];
		switch(DataBaseType.getByName(Players.get().getString("DatabaseType"))) {
		case All:
			if(!plugin.data.existsPlayer(args[0]) || !plugin.mongoTables.containValue("NickName", name, Collection.WhiteList)){
				sender.sendMessage(ChatColor.GRAY + "Такого игрока нет в базе");
				return false;
			}
			plugin.mongoTables.deleteDocument(plugin.mongoTables.getDocument("NickName", name, Collection.WhiteList), Collection.WhiteList);
			plugin.data.removePlayer(name);
			break;
		case MongoDB:
			if(!plugin.mongoTables.containValue("NickName", name, Collection.WhiteList)) {
				sender.sendMessage(ChatColor.GRAY + "Такого игрока нет в базе");
				return false;
			};
			plugin.mongoTables.deleteDocument(plugin.mongoTables.getDocument("NickName", name, Collection.WhiteList), Collection.WhiteList);
			break;
		case MySQL:
			if(!plugin.data.existsPlayer(args[0])){
				sender.sendMessage(ChatColor.GRAY + "Такого игрока нет в базе");
				return false;
			}
			plugin.data.removePlayer(name);
			break;
		case None:
			break;
		default:
			break;
		
		}
		
		if(Bukkit.getPlayer(args[0]) != null) {
		Bukkit.getPlayer(args[0]).kickPlayer("Вас выгнали из вайтлиста");
		}
		sender.sendMessage(ChatColor.GRAY + "Игрок убран из листа");
		return false;
	}
}
