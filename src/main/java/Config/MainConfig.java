package Config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.Main;
import net.dv8tion.jda.api.entities.Activity;

public class MainConfig {

		private static File file;
	    private static FileConfiguration customFile;

	    //Finds or generates the custom config file
	    public static void setup(){
	        file = new File(Bukkit.getServer().getPluginManager().getPlugin(Main.getPlugin(Main.class).getName()).getDataFolder(), "BotSettings.yml");

	        if (!file.exists()){
	            try{
	                file.createNewFile();
	            }catch (IOException e){
	                //owww
	            }
	        }
	        customFile = YamlConfiguration.loadConfiguration(file);
	    }

	    public static FileConfiguration get(){
	        return customFile;
	    }

	    public static void save(){
	        try{
	            customFile.save(file);
	        }catch (IOException e){
	            System.out.println("Файл не смог сохраниться!");
	        }
	    }

	    public static void reload(){
	        customFile = YamlConfiguration.loadConfiguration(file);
	    }
	    
	    public static void loadConfig() {
			setup();
			//Config
			Map<String, Object> config = new HashMap<String, Object>();
			config.put("enableBot", false);
			config.put("BotKey", "");
			
			//Activity
			Map<String, Object> activity = new HashMap<String, Object>();
			activity.put("enable", false);
			activity.put("activityType", Activity.ActivityType.WATCHING.getKey());
			activity.put("activity", "%players%/%maxOnline%");
			config.put("activity", activity);
			
			Map<String, Object> commandOnlineConfig = new HashMap<String, Object>();
			commandOnlineConfig.put("enable", false);
			commandOnlineConfig.put("channelId", 0);
			commandOnlineConfig.put("isSlashCommand", false);
			commandOnlineConfig.put("slashCommandDescription", "This command shows online on Minecraft server");
			commandOnlineConfig.put("slashCommandID", 0);
			commandOnlineConfig.put("command", "!online");
			Map<String, Object> embed = new HashMap<String, Object>();
			embed.put("author", "**ShybkaBot**");
			embed.put("title", "*Online Players*");
			embed.put("description", "**==>** *%player%*");
			embed.put("footer", "It`s a cool server");
			embed.put("nullOnline", "**We want to you!**");
			commandOnlineConfig.put("embed", embed);
			config.put("onlineCommand", commandOnlineConfig);
			
			
			
			get().addDefaults(config);
			get().options().copyDefaults(true);
			save();
	    }
	}
