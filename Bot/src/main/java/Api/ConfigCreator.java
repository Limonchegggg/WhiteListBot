package Api;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;


public class ConfigCreator {
	
	private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void CreateConfig(@NotNull String path){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), path);

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }
    public static void CreateConfig(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), "players\\players.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                //owww
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration get() {
    	return customFile;
    }
    public static FileConfiguration get(String path){
    	file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), path);
    	customFile = YamlConfiguration.loadConfiguration(file);
    	return customFile;
    }
    
    public boolean existFile(String path) {
    	file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), path);
    	if(file == null) {
    		return false;
    	}
    	return true;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Файл не смог сохраниться!");
            System.out.println(e);
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
