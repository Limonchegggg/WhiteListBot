package Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import Admin.AdminCommands;
import Admin.BanData;
import Admin.Timer;
import Api.ConfigCreator;
import Database.MySql;
import Database.SQLDiscord;
import Database.SQLGetter;
import Database.SqlConnection;
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
import locale.LocaleData;

public class Main extends JavaPlugin{
	public boolean ServerWork = false;
	public MySql sql;
	public SQLGetter data;
	public WhiteListBot jda;
	public SQLDiscord sqld;
	public ConfigCreator cc;
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
	
	/**
	 * Список Команда - Уровень
	 */
	public HashMap<String, Integer> CommandLevelMap = new HashMap<String, Integer>();
	
	/*
	 * Реестр модов
	 */
	public HashMap<String, ItemStack> mods = new HashMap<String, ItemStack>();
	
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
		Players.get().addDefault("placeHolder", "Hello &player& how are you?");
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
		this.sql = new MySql();
		this.data = new SQLGetter(this);
		this.sqld = new SQLDiscord(this);
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
			sqld.CreateTable();
			data.createTable();
			
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
			sql.disconnect();
			jda.stopBot();
			ServerWork = false;
		}
		
		@Override
		public void reloadConfig() {
			new BanData().saveBans();
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
		
		
		@SuppressWarnings("unused")
		private void createLocale() {
			LocaleData.setup();
	    	//Очередь
	    	HashMap<String, String> discord = new HashMap<String, String>();
	    	discord.put("DiscordQueueAdd", "Дорогой %player%! Теперь ты можешь играть на сервере!");
	    	discord.put("DiscordQueueAddQueue", "Дорогой %player%! Я добавил тебя в очередь и напишу когда тебя примут!");
	    	discord.put("DiscordQueueContain", "Вы уже состоите в очереди");
	    	discord.put("DiscordQueueCancel", "Дорогой %player%! Мне жаль, тебе отказали, но не расстраивайся и попробуй еще раз! 😿");
	    	discord.put("DiscordQueueAccept", "Дорогой `` %duser% ``! Теперь ты можешь играть на сервере! 😎");
	    	discord.put("DiscordQueueCommandUsing", "Использование gowl <Ник> \nПример **gowl Shybka**");
	    	discord.put("DiscordQueueFail", "Я не понимаю что вы хотите написать!");
	    	discord.put("DiscordQueueNickNameLenghtDeny", "Минимальная длинна ника 4, а максимальная 20");
	    	discord.put("DiscordQueueNickNameDeny", "Ник должен состоять только из английских букв и/или цифр");
	    	discord.put("DiscordQueueAccountDeny", "**Такой ник зарегистрирован или у вас уже есть аккаунт на сервере!**");
	    	LocaleData.get().addDefault("Discord", discord);
	    	//Дискорд помощь застрявшим
	    	HashMap<String, String> discordHelp = new HashMap<String, String>();
	    	discordHelp.put("DiscordHelpDeny", "Я не вижу что ты застрял, ничего делать не буду");
	    	discordHelp.put("DiscordHelpPlayerOnServerFail", "Я не вижу тебя на сервере");
	    	discordHelp.put("DiscordHelp", "Я сделал все что смог");
	    	LocaleData.get().set("DiscordHelp", discordHelp);
	    	
	    	//Дискорд личные сообщения
	    	HashMap<String, String> privateDiscordMessage = new HashMap<String, String>();
	    	privateDiscordMessage.put("DiscordTellBotDeny", "Блять ну привет я тоже бот");
	    	privateDiscordMessage.put("DiscordTellCommandUsing", "Чтобы отправить личное сообщение в игру, тебе необходимо ввести [tell <Ник игрока> <Сообщение>]");
	    	privateDiscordMessage.put("DiscordTellPlayerOnServerFail", "Я не вижу этого игрока на сервере");
	    	privateDiscordMessage.put("DiscordTellPlayer", "[ЛС][Discord]%player%: %message%");
	    	LocaleData.get().set("privateDiscordMessage", privateDiscordMessage);
	    	
	    	//Телемост
	    	HashMap<String, String> TeleChat = new HashMap<String, String>();
	    	TeleChat.put("DiscordChatToMinecraft", "[Discord]%duser%: %message%");
	    	LocaleData.get().addDefault("TeleChat", TeleChat);
	    	
	    	//Конфиги
	    	HashMap<String, String> Config = new HashMap<String, String>();
	    	Config.put("ConfigReloaded", "Конфиг перезагружен");
	    	Config.put("ConfigReloadedFail", "Ошибка перезагрузки конфига");
	    	LocaleData.get().addDefault("Config", Config);
	    	
	    	//Отказ при заходе
	    	HashMap<String, String> DenyJoin = new HashMap<String, String>();
	    	DenyJoin.put("MinecraftJoinDeny", "Вас нет в вайтлисте");
	    	DenyJoin.put("MinecraftJoinDenyBan", "Вы временно заблокированы");
	    	DenyJoin.put("MinecraftQuit", "%player% вышел с сервера");
	    	LocaleData.get().addDefault("DenyJoin", DenyJoin);
	    	
	    	//Чат
	    	HashMap<String, String> Chat = new HashMap<String, String>();
	    	Chat.put("MinecraftChatMute", "Выш чат временно заблокирован");
	    	Chat.put("MinecraftAntiSpam", "Отдохни");
	    	Chat.put("MinecraftLocalChat", "[Локал]%player%: %message%");
	    	Chat.put("MinecraftGlobalChat", "[Глобал]%player%: %message%");
	    	Chat.put("MinecraftToDiscordChat", "[Minecraft]%player%: %message%");
	    	LocaleData.get().addDefault("Chat", Chat);
	    	
	    	//Бот
	    	HashMap<String, String> Bot = new HashMap<String, String>();
	    	Bot.put("BotStopCommand", "Бот остановлен");
	    	Bot.put("BotStartCommand", "Бот запущен");
	    	Bot.put("BotWrongWordAdd", "Слово %word% было добавлено в список");
	    	Bot.put("BotWrongWordRemove", "Слово %word% было убрано из списка");
	    	LocaleData.get().addDefault("Bot", Bot);
	    	
	    	//Отказ в праве
	    	LocaleData.get().addDefault("PermissionDeny", "У вас недостаточно прав");
	    	
	    	//Бан команда
	    	HashMap<String, String> Ban = new HashMap<String, String>();
	    	Ban.put("AdminCommandBanUserIsBanned", "%user% уже в бане");
	    	Ban.put("AdminCommandBanUserDoesNotInWhiteList", "%user% нет в вайтслисте");
	    	Ban.put("AdminCommandBanTimeLenghtFail", "Неправильно указано время бана!");
	    	Ban.put("AdminCommandBanUser", "%User% был забанен на %lenght% %time%");
	    	Ban.put("AdminCommandBanUserDiscordMessage", "Мне жаль, но вы забанены по причине %reason%! Вам нужно подождать %lenght% %time% перед игрой");
	    	LocaleData.get().addDefault("Ban", Ban);
	    	
	    	//Команда пардон
	    	HashMap<String, String> pardon = new HashMap<String, String>();
	    	pardon.put("AdminCommandPardonUserIsNoBanned", "%banUser% не забанен");
	    	pardon.put("AdminCommandPardonUserPardon", "%banUser% был разбанен");
	    	pardon.put("AdminCommandPardonUserPardonDiscord", "Поздравляю вас разбанили! Теперь вы можете играть на сервере!");
	    	LocaleData.get().addDefault("Pardon", pardon);
	    	
	    	//Мут
	    	HashMap<String, String> Mute = new HashMap<String, String>();
	    	Mute.put("AdminCommandMuteUserIsMuted", "%muteUser% уже замучен");
	    	Mute.put("AdminCommandMuteLenghtFail", "Неправильно указано время мута");
	    	Mute.put("AdminCommandMuteUser", "%muteUser% был замучен на %lenght% %time%");
	    	LocaleData.get().addDefault("Mute", Mute);
	    	
	    	//Размут
	    	HashMap<String, String> UnMute = new HashMap<String, String>();
	    	UnMute.put("AdminCommandUserIsNotMuted", "%user% не в муте");
	    	UnMute.put("AdminCommandUserUnMuteMessage", "Вы были размучены");
	    	LocaleData.get().addDefault("unMute", UnMute);
	    	
	    	//АвтоСнятиеНаказания
	    	HashMap<String, String> AutoPardon = new HashMap<String, String>();
	    	AutoPardon.put("DiscordAutoPardonMessage", "Поздравляю вас разбанили! Теперь вы можете играть на сервере!");
	    	AutoPardon.put("MinecraftUnMuteMessage", "Вы были размучены!");
	    	LocaleData.get().addDefault("AutoPardon", AutoPardon);
	    	
	    	//Инвентари
	    	HashMap<String, String> Inventoryes = new HashMap<String, String>();
	    	Inventoryes.put("OpenEnderChest", "Вы открыли эндер сундук игрока %user%");
	    	Inventoryes.put("OpenInventory", "Вы открыли инвентарь игрока %user%");
	    	Inventoryes.put("OpenEnderChestFail", "Ошибка открытия Эндер Сундука у игрока %user%");
	    	Inventoryes.put("OpenEnderChestFail", "Ошибка открытия Инвентаря у игрока %user%");
	    	LocaleData.get().addDefault("Inventory", Inventoryes);
	    	
	    	LocaleData.get().options().copyDefaults(true);
	    	LocaleData.save();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}