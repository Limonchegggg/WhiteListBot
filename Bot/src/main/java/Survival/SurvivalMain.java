package Survival;




import Main.Main;
import Survival.Events.BalanceFixEvents;
import Survival.Events.CheckItems;
import Survival.Mechanics.Items.Item;
import Survival.Mechanics.Items.Commands.CreateItem;
import Survival.Mechanics.Items.Commands.getLvl;

public class SurvivalMain {
	private Main main;
	
	
	public SurvivalMain(Main main) {
		this.main = main;
		
		
		
		main.getServer().getPluginCommand("stats").setExecutor(new getLvl());
		main.getServer().getPluginCommand("item").setExecutor(new CreateItem());
		main.getServer().getPluginCommand("item").setTabCompleter(new CreateItem());
		main.getServer().getPluginManager().registerEvents(new CheckItems(), main);
		main.getServer().getPluginManager().registerEvents(new BalanceFixEvents(), main);
		new Item().LoadItems();
		new Item().LoadBlackList();
	}
	
	public Main getMain() {
		return main;
	}
	


}
