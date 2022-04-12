package Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Database.MySql;
import Database.SQLGetter;
import Database.SqlConnection;
import Events.JoinEvent;
import Events.TeleMessageMinecraft;
import WhiteListCommands.WhiteListAdd;
import WhiteListCommands.WhiteListRemove;
import WhiteListCommands.vanish;
import bot.DiscordData;
import bot.WhiteListBot;
import configs.Players;

public class Main extends JavaPlugin{
	public MySql sql;
	public SQLGetter data;
	public WhiteListBot jda;
	public HashMap<String, Player> stack_players = new HashMap<String, Player>();
	public ArrayList<String> vanish = new ArrayList<String>();
	/*
	 * Юзер:
	 *  Ник: test
	 *  Имя юзера:test
	 *  Юзер Ид:02311005
	 */
	public HashMap<String, HashMap<String, String>> messages_id = new HashMap<String, HashMap<String,String>>();
	@Override
	public void onEnable() {
		
		Players.setup();
		Players.get().addDefault("token", "Token Here");
		Players.get().addDefault("ChannelId", "Channel Id Here");
		Players.get().addDefault("ChannelCommand", "Channel Id Here");
		Players.get().addDefault("TeleMessage", "Channel Id Here");
		Players.get().addDefault("DiscordConsole", "Channel Id Here");
		Players.get().addDefault("Admins", "Id list here");
		Players.get().addDefault("ServerId", "Id Here");
		Players.get().addDefault("Kick_Message", "Вас нет в вайтлисте");
		Players.get().addDefault("Tickets", true);
		Players.get().addDefault("SQLExeption", "Произошла ошибка попробуейте еще раз");
		Players.get().addDefault("host", "host");
		Players.get().addDefault("port", "port");
		Players.get().addDefault("database", "database");
		Players.get().addDefault("username", "username");
		Players.get().addDefault("password", "password");
		Players.get().options().copyDefaults(true);
		Players.save();
		
		DiscordData.setup();
		DiscordData.get().addDefault("Queue", null);
		DiscordData.get().addDefault("Id", null);
		DiscordData.save();
		
		this.jda = new WhiteListBot();
		this.sql = new MySql();
		this.data = new SQLGetter(this);
		jda.startbot();
		
		getServer().getPluginCommand("connection").setExecutor(new SqlConnection());
		getServer().getPluginCommand("wladd").setExecutor(new WhiteListAdd());
		getServer().getPluginCommand("removewl").setExecutor(new WhiteListRemove());
		getServer().getPluginCommand("v").setExecutor(new vanish());
		
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new TeleMessageMinecraft(), this);
		try {
			sql.connect();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			Bukkit.getLogger().info("Database not connect (CLASS NOT FOUND)");
		}
		catch(SQLException e) {
			e.printStackTrace();
			Bukkit.getLogger().info("Database not connect (SQL ERROR)");
		}
			
		if(sql.isConnected()) {
			Bukkit.getLogger().info("Database is connected");
			data.createTable();
		}
	}
		@Override
		public void onDisable() {
			sql.disconnect();
			jda.stopBot();
		}
		
		@Override
		public void reloadConfig() {
			sql.disconnect();
			jda.stopBot();
		}
		
		
		
		
		public boolean existsSqlHostYml() {
			return Players.get().getString("host").isEmpty() ? false : true;
		}
		public String getSqlHost() {
			return Players.get().getString("host");
		}
		
		public boolean existsSqlPortYml() {
			return Players.get().getString("port").isEmpty() ? false : true;
		}
		public String SqlPortYml() {
			return Players.get().getString("port");
		}
		
		public boolean existsSqlDatabaseYml() {
			return Players.get().getString("database").isEmpty() ? false : true;
		}
		public String getSqlDatabaseYml() {
			return Players.get().getString("database");
		}
		
		public boolean existsSqlUsernameYml() {
			return Players.get().getString("username").isEmpty() ? false : true;
		}
		
		public boolean existsSqlPasswordYml() {
			return Players.get().getString("password").isEmpty() ? false : true;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}