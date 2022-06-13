package Survival;



import Api.ConfigCreator;
import Main.Main;
import Survival.Events.CheckItems;
import Survival.Mechanics.Items.Commands.CreateItem;

public class SurvivalMain {
	private Main main;
	
	//Неизменяемая переменная для записи предметов в дб
	private final String PATH = "items\\settings.yml";
	
	public SurvivalMain(Main main) {
		this.main = main;
		
		ConfigCreator.CreateConfig(PATH);
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
		
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
