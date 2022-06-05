package Survival;


import java.util.HashMap;

import Api.ConfigCreator;
import Main.Main;
import Survival.Events.CheckItems;
import Survival.Mechanics.Items.Commands.CreateItem;

public class SurvivalMain {
	private Main main;
	public HashMap<String, String> player_category;
	//Неизменяемая переменная для записи предметов в дб
	private static final String PATH = "items\\items.yml";
	
	public SurvivalMain(Main main) {
		this.main = main;
		
		ConfigCreator.CreateConfig("items\\test.yml");
		ConfigCreator.get().createSection("items");
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
		player_category = new HashMap<String, String>();
		main.getServer().getPluginCommand("additem").setExecutor(new CreateItem());
		main.getServer().getPluginManager().registerEvents(new CheckItems(), main);
	}
	
	public Main getMain() {
		return main;
	}
	
	public String getPath() {
		return PATH;
	}

}
