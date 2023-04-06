package Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.Main;

public class MinecraftConfig {


			private static File file;
		    private static FileConfiguration customFile;

		    //Finds or generates the custom config file
		    public static void setup(){
		        file = new File(Bukkit.getServer().getPluginManager().getPlugin(Main.getPlugin(Main.class).getName()).getDataFolder(), "MinecraftSettings.yml");

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
		    
		    public static void loadConfig(){
		    	setup();
		    	get().addDefault("noPermissonMessage", "&cНет прав!");
		    	get().addDefault("configReloadMessage", "&8Конфиг %config% успешно перезагружен");
		    	get().addDefault("invalidArguments", "&cНеверный аргумент! &7Доступные аргументы: &6%args%");
		    	get().addDefault("restartBotMessage", "&7Бот успешно перезапущен");
		    	get().addDefault("botStartedMessage", "&7Бот успешно запущен");
		    	get().addDefault("botStoppedMessage", "&7от успешно выключен");
		    	get().options().copyDefaults(true);
		    	save();
		    }
		}
