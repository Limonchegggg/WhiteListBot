package Survival;


import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import Main.Main;
import Survival.Mechanics.Items.PickAxe.PickAxe;

public class SurvivalMain {
	private Main main;
	public SurvivalMain(Main main) {
		this.main = main;
		DeleteRecipes();
		
		Bukkit.addRecipe(new PickAxe().CreateStonePickaxe());
		Bukkit.addRecipe(new PickAxe().CreateIronPickaxe());
	}
	
	public Main getMain() {
		return main;
	}
	
	public void RemoveRecipe(String recipe) {
		for(Iterator<Recipe> iter = Bukkit.getServer().recipeIterator(); iter.hasNext();) {
			if(iter.next() == Bukkit.getServer().getRecipe(NamespacedKey.minecraft(recipe))) {
				iter.remove();
			}
		};
	}
	public void DeleteRecipes() {
		RemoveRecipe("stone_pickaxe");
		RemoveRecipe("ireon_pickaxe");
	}

}
