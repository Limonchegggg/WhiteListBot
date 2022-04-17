package Admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.Main;

public class BanData {
	private Main main = Main.getPlugin(Main.class);
	private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("WhiteListBot").getDataFolder(), "BanData.yml");

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
    public void saveBans() {
    	get().set("Bans", main.ban_list);
    }
    @SuppressWarnings("unchecked")
	public void loadBans() {
    	main.ban_list = (HashMap<String, Integer>) get().getMapList("Bans");
    }
}
