package Survival.Mechanics;

import java.io.File;
import java.util.Map.Entry;

import Api.ConfigCreator;
import Main.Main;
import console.Logging;

public class CommandLevel {

	
	public void addComand(String CommandName, int lvl){
		ConfigCreator.CreateConfig("command"+File.separator+CommandName+".yml");
		ConfigCreator.get().set("Lvl", lvl);
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
		Main.getPlugin(Main.class).CommandLevelMap.put("/"+CommandName, lvl);
		return;
	}
	
	public void removeCommand(String CommandName) {
		if(!ConfigCreator.getConfigList("command"+File.separator).contains(CommandName+".yml")) {
			return;
		}
		ConfigCreator.DeleteFile("command"+File.separator+CommandName+".yml");
		Main.getPlugin(Main.class).CommandLevelMap.remove(CommandName);
	}
	
	public int getLvl(String CommandName) {
		return Main.getPlugin(Main.class).CommandLevelMap.get(CommandName);
	}
	
	public void setLvl(String CommandName, int lvl) {
		Main.getPlugin(Main.class).CommandLevelMap.remove(CommandName, lvl);
	}
	
	public boolean ExistCommand(String CommandName) {
		if(Main.getPlugin(Main.class).CommandLevelMap.containsKey(CommandName)) {
			return true;
		}
		return false;
	}
	
	public void loadCommand() {
		Main main = Main.getPlugin(Main.class);
		try {
		if(ConfigCreator.getConfigList("command"+File.separator).size() == 0) {
			new Logging().Log("Command lvl was not loaded!");
			return;
		}
		
		for(int i=0; i<ConfigCreator.getConfigList("command"+File.separator).size(); i++) {
			main.CommandLevelMap.put("/"+ConfigCreator.getConfigList("command"+File.separator).get(i).replace(".yml", ""), ConfigCreator.get("command"+File.separator+ConfigCreator.getConfigList("command"+File.separator).get(i)).getInt("Lvl"));
		}
		System.out.println("---Commands---");
		for(Entry<String, Integer> key : main.CommandLevelMap.entrySet()) {
			System.out.println(key.getKey() + ": " + key.getValue());
		}
		}catch(Exception e) {
			new Logging().Log("Command lvl was not loaded!");
		}
	}
	
}
