package Survival;



import Main.Main;
import Survival.Events.CheckItems;
import Survival.Mechanics.Items.Item;
import Survival.Mechanics.Items.Commands.CreateItem;
import Survival.Mechanics.Items.Commands.getLvl;

public class SurvivalMain {
	private Main main;
	
	
	public SurvivalMain(Main main) {
		this.main = main;

		main.getServer().getPluginCommand("stats").setExecutor(new getLvl());
		main.getServer().getPluginCommand("additem").setExecutor(new CreateItem());
		main.getServer().getPluginManager().registerEvents(new CheckItems(), main);
		new Item().LoadItems();
	}
	
	public Main getMain() {
		return main;
	}
	


}
