package Database;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import Database.mongoDB.MongoDbTables.Collection;
import Main.Main;
import configs.Players;
import net.md_5.bungee.api.ChatColor;

public class DataBaseCommands implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
		if(!sender.hasPermission("bwt.database")) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав");
			return false;
		}
		
		
		Main main = Main.getPlugin(Main.class);
		List<Document> docs = new ArrayList<Document>();
		switch(args[0]) {
		case "sqlToMongo":
			if(DataBaseType.getByName(Players.get().getString("DatabaseType")) != DataBaseType.All) {
				sender.sendMessage(ChatColor.GRAY + "Не включен режим All, портировние невозможно!");
				return false;
			}
			try {
				for(String s : main.data.getValues()) {
					Document doc = new Document().append("_id", main.data.getIdFromName(s)).append("NickName", s).append("Discord", main.data.getDiscordName(s));
					if(main.mongoTables.containValue("_id", main.data.getIdFromName(s), Collection.WhiteList)) continue;
					
					docs.add(doc);
				}
				main.mongoTables.addDocuments(docs, Collection.WhiteList);
				sender.sendMessage(ChatColor.GRAY + "Импорт прошел успешно");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		case "mongoToSql":
			if(DataBaseType.getByName(Players.get().getString("DatabaseType")) != DataBaseType.All) {
				sender.sendMessage(ChatColor.GRAY + "Не включен режим All, портировние невозможно!");
				return false;
			}
			try {
				for(Document doc : main.mongoTables.getDokuments(Collection.WhiteList)) {
					main.data.cratePlayer(doc.getString("NickName"), doc.getString("Discord"), doc.getString("_id"));
					main.sqld.CreatePlayer(doc.getString("NickName"), doc.getString("Discord"), doc.getString("_id"));
				}
			sender.sendMessage(ChatColor.GRAY + "Портирование прошло успешно!");
			}catch(Exception e) {
				sender.sendMessage(ChatColor.GRAY + "Ошибка при портировании!");
				e.printStackTrace();
			}
			return false;
		case "test":
			try {
				for(String s : main.data.getValues()) {
					sender.sendMessage(s);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		default:
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return Arrays.asList("sqlToMongo","mongoToSql");
	}
	

}
