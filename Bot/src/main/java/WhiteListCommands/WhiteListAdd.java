package WhiteListCommands;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.ImmutableMap;

import Database.DataBaseType;
import Database.mongoDB.MongoDbTables.Collection;
import Main.Main;
import configs.Players;
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
		
		String name = args[0];
		String Discord = args[1];
		String ID = args[2];
		switch(DataBaseType.getByName(Players.get().getString("DatabaseType"))) {
		case All:
			if(plugin.mongoTables.containValue("_id", ID, Collection.WhiteList) || plugin.data.existsPlayer(args[0])) {
				sender.sendMessage(ChatColor.GRAY + "Такой игрока уже есть базе данных!");
				return false;
			}
			plugin.mongoTables.addDocument(plugin.mongoTables.CreateDocument(ImmutableMap.of("_id", ID, "NickName", name, "Discord", Discord)), Collection.WhiteList);
			plugin.data.cratePlayer(name, Discord, ID);
			break;
		case MongoDB:
			if(plugin.mongoTables.containValue("_id", ID, Collection.WhiteList)) {
				sender.sendMessage(ChatColor.GRAY + "Такой игрока уже есть базе данных!");
				return false;
			}
			plugin.mongoTables.addDocument(plugin.mongoTables.CreateDocument(ImmutableMap.of("_id", ID, "NickName", name, "Discord", Discord)), Collection.WhiteList);
			break;
		case MySQL:
			if(plugin.data.existsPlayer(args[0])) {
				sender.sendMessage(ChatColor.GRAY + "Такой игрока уже есть базе данных!");
				return false;
			}
			plugin.data.cratePlayer(args[0], args[1], args[2]);
			break;
		case None:
			break;
		default:
			break;
		
		}
		sender.sendMessage(ChatColor.GRAY + "Игрок " + args[0] + " был добавлен в вайтлист");
		return false;
		
	}
}
