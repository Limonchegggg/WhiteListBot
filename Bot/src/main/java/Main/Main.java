package Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ImmutableMap;
import com.mongodb.MongoClientException;
import com.mongodb.MongoServerException;
import com.mongodb.MongoSocketException;

import Admin.AdminCommands;
import Admin.BanData;
import Admin.Timer;
import Database.DataBaseCommands;
import Database.DataBaseType;
import Database.mongoDB.MongoDB;
import Database.mongoDB.MongoDbTables;
import Database.mongoDB.MongoDbTables.Collection;
import Database.sql.MySql;
import Database.sql.SQLDiscord;
import Database.sql.SQLGetter;
import Database.sql.SqlConnection;
import Events.JoinEvent;
import Events.TeleMessageMinecraft;
import Survival.SurvivalMain;
import WhiteListCommands.HatCommand;
import WhiteListCommands.WhiteListAdd;
import WhiteListCommands.WhiteListRemove;
import WhiteListCommands.connectBot;
import WhiteListCommands.vanish;
import bot.DiscordData;
import bot.WhiteListBot;
import configs.ConfigCommand;
import configs.Players;

public class Main extends JavaPlugin{
	public boolean ServerWork = false;
	public MySql sql;
	public SQLGetter data;
	public WhiteListBot jda;
	public SQLDiscord sqld;
	public MongoDB mongoDB;
	public MongoDbTables mongoTables;
	//Игроки, которые застряли
	public HashMap<String, Player> stack_players = new HashMap<String, Player>();
	//Игроки в ванише
	public ArrayList<String> vanish = new ArrayList<String>();
	//Игрок - время
	public HashMap<String, Integer> ban_list = new HashMap<String, Integer>();
	public HashMap<String, Integer> mute_list = new HashMap<String, Integer>();
	/**
	 * Игрок - Катекогия - Уровень в категории
	 */
	public HashMap<String, HashMap<String, HashMap<String, Integer>>> player_category = new HashMap<String, HashMap<String, HashMap<String, Integer>>>(); //Игрок_Категория
	
	//Загрузка предметов в реест сервера
	public HashMap<String, Integer> item_lvl = new HashMap<String, Integer>();
	
	/**
	 * Черный список вещей за которые не будет падать экспа
	 */
	public List<String> BlackListBlock = new ArrayList<String>();
	
	public HashMap<String, String> perks = new HashMap<String, String>();
	
	/**
	 * Список Команда - Уровень
	 */
	public HashMap<String, Integer> CommandLevelMap = new HashMap<String, Integer>();
	
	
	
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
		Players.get().addDefault("DatabaseType", "MySQL"); // База по умолчанию
		Players.get().addDefault("MongoDB", ImmutableMap.of("MongoClient", "client", "MongoDatabase", "database"));
		Players.get().options().copyDefaults(true);
		Players.save();
		
		DiscordData.setup();
		DiscordData.get().addDefault("Queue", null);
		DiscordData.get().addDefault("BlackWords", null);
		DiscordData.save();
		
		BanData.setup();
		BanData.get().addDefault("Bans", null);
		BanData.get().addDefault("Mute", null);
		BanData.save();
		
		//createLocale();
		
		
		this.jda = new WhiteListBot();
		
		jda.startbot();
		
		getServer().getPluginCommand("connection").setExecutor(new SqlConnection());
		getServer().getPluginCommand("wladd").setExecutor(new WhiteListAdd());
		getServer().getPluginCommand("wlremove").setExecutor(new WhiteListRemove());
		getServer().getPluginCommand("v").setExecutor(new vanish());
		getServer().getPluginCommand("bot").setExecutor(new connectBot());
		getServer().getPluginCommand("adm").setExecutor(new AdminCommands());
		getServer().getPluginCommand("adm").setTabCompleter(new AdminCommands());
		getServer().getPluginCommand("reloadConfig").setExecutor(new ConfigCommand());
		getServer().getPluginCommand("hat").setExecutor(new HatCommand());
		getServer().getPluginCommand("database").setExecutor(new DataBaseCommands());
		getServer().getPluginCommand("database").setTabCompleter(new DataBaseCommands());
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new TeleMessageMinecraft(), this);
		
		Bukkit.getLogger().info("Selected base is " + DataBaseType.getByName(Players.get().getString("DatabaseType")).getTitle());
		
		//База по уполчанию
		switch(DataBaseType.getByName(Players.get().getString("DatabaseType"))){
		case All:
			mongoDB = new MongoDB();
			mongoTables = new MongoDbTables(this);
			try {
			mongoDB.Connect();
			}catch(MongoClientException clientEx) {
				clientEx.printStackTrace();
				System.out.println("Mongo is not connected (Client Exception)");
			}catch(MongoServerException serverEx) {
				serverEx.printStackTrace();
				System.out.println("Mongo is not connected (Server Exception)");
			}catch(MongoSocketException socketEx) {
				socketEx.printStackTrace();
				System.out.println("Mongo is not connected (Socket Exception)");
			}
			if(mongoDB.isConnected() == true) {
				System.out.println("Mongo database is connected");
			}
			if(!mongoTables.existCollection(Collection.WhiteList)) {
				mongoTables.CreateCollection(Collection.WhiteList);
			}
			this.sql = new MySql();
			this.data = new SQLGetter(this);
			this.sqld = new SQLDiscord(this);
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
				Bukkit.getLogger().info("MySQL database is connected");
				sqld.CreateTable();
				data.createTable();
			}
			break;
		case MongoDB:
			mongoDB = new MongoDB();
			mongoTables = new MongoDbTables(this);
			try {
			mongoDB.Connect();
			}catch(MongoClientException clientEx) {
				clientEx.printStackTrace();
				System.out.println("Mongo is not connected (Client Exception)");
			}catch(MongoServerException serverEx) {
				serverEx.printStackTrace();
				System.out.println("Mongo is not connected (Server Exception)");
			}catch(MongoSocketException socketEx) {
				socketEx.printStackTrace();
				System.out.println("Mongo is not connected (Socket Exception)");
			}
			if(mongoDB.isConnected() == true) {
				System.out.println("Mongo database is connected");
			}
			if(!mongoTables.existCollection(Collection.WhiteList)) {
				mongoTables.CreateCollection(Collection.WhiteList);
			}
			break;
		case MySQL:
			this.sql = new MySql();
			this.data = new SQLGetter(this);
			this.sqld = new SQLDiscord(this);
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
				Bukkit.getLogger().info("MySQL database is connected");
				sqld.CreateTable();
				data.createTable();
			}
			break;
		case None:
			Bukkit.getLogger().info("DataBase is not requared, whitelist in not work");
			break;
		default:
			break;
			
		}
		
		try {
		if(!BanData.get().getStringList("Names").isEmpty()) {
		new BanData().loadBans();
		}
		}catch(Exception e) {
			System.out.println("Ошибка загрузки банов");
		}
		try {
			if(!BanData.get().getStringList("NamesMute").isEmpty()) {
			new BanData().loadBans();
		}
		}catch(Exception e) {
			System.out.println("Ошибка загрузки мутов");
		}
		new Timer();
		new SurvivalMain(this);
		ServerWork = true;
		}
		@Override
		public void onDisable() {
			new BanData().saveBans();
			new BanData().saveMute();
			if(sql != null) sql.disconnect();
			if(mongoDB != null) mongoDB.closeConnection();
			jda.stopBot();
			ServerWork = false;
		}
		
		@Override
		public void reloadConfig() {
			new BanData().saveBans();
			sql.disconnect();
			jda.stopBot();
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}