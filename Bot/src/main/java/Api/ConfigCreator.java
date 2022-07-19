package Api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    	if(file != null) {
    		return true;
    	}
    	return false;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Файл не смог сохраниться!");
            System.out.println(e);
        }
    }
    
    public static void save(String path){
        try{
        	file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), path);
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Файл не смог сохраниться!");
            System.out.println(e);
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
    public static void reload(String path) {
    	file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), path);
    	customFile = YamlConfiguration.loadConfiguration(file);
    }
    /**
     * path должен вести в папку с файлами
     * @Метод возвращает имена всех файлов в папке
     */
    public static ArrayList<String> getConfigList(String path){
    	file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), path);
    	ArrayList<String> config_Names = new ArrayList<String>();
    	for(int i=0; i<file.list().length; i++) {
    		config_Names.add(file.list()[i]);
    	}
    	return config_Names;
    }
    
    public static ArrayList<String> getConfigList(){
    	ArrayList<String> config_Names = new ArrayList<String>();
    	for(int i=0; i<file.list().length; i++) {
    		config_Names.add(file.list()[i]);
    	}
    	return config_Names;
    }
    
    /**
     * name имя файла вместе с расширением
     */
    public static boolean isYMLFile(String name) {
    	if(!name.contains(".yml")) {
    		return false;
    	}
    	return true;
    }
    
    public static void DeleteFile(String path) {
    	file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), path);
    	file.delete();
    }
    
    public static void DeleteFile() {
    	file.delete();
    }
}
