package Survival;




import Main.Main;
import Survival.Events.BalanceFixEvents;
import Survival.Events.CheckItems;
import Survival.Mechanics.CommandLevel;
import Survival.Mechanics.Items.CraftItem;
import Survival.Mechanics.Items.InvisibleFrame;
import Survival.Mechanics.Items.Item;
import Survival.Mechanics.Items.Modifycator;
import Survival.Mechanics.Items.Commands.AdminCommands;
import Survival.Mechanics.Items.Commands.CreateItem;
import Survival.Mechanics.Items.Commands.EventCommand;
import Survival.Mechanics.Items.Commands.LvlCommand;
import Survival.Mechanics.Items.Commands.getLvl;

public class SurvivalMain {
	private Main main;
	
	
	public SurvivalMain(Main main) {
		this.main = main;
		
		
		
		main.getServer().getPluginCommand("stats").setExecutor(new getLvl());
		
		main.getServer().getPluginCommand("item").setExecutor(new CreateItem());
		main.getServer().getPluginCommand("item").setTabCompleter(new CreateItem());
		
		main.getServer().getPluginCommand("lvl").setExecutor(new AdminCommands());
		main.getServer().getPluginCommand("lvl").setTabCompleter(new AdminCommands());
		
		main.getServer().getPluginCommand("lvlcommand").setExecutor(new LvlCommand());
		main.getServer().getPluginCommand("lvlcommand").setTabCompleter(new LvlCommand());
		
		main.getServer().getPluginManager().registerEvents(new CheckItems(), main);
		main.getServer().getPluginManager().registerEvents(new BalanceFixEvents(), main);
		main.getServer().getPluginManager().registerEvents(new EventCommand(), main);
		new Item().LoadItems();
		new Item().LoadBlackList();
		new CommandLevel().loadCommand();
		
		main.getServer().getPluginManager().registerEvents(new InvisibleFrame(), main);
		
		main.getServer().addRecipe(new CraftItem().Snowball());
		
		new Modifycator().loadMods();
	}
	public Main getMain() {
		return main;
	}
}