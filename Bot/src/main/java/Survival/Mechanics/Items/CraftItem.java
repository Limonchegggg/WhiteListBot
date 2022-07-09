package Survival.Mechanics.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftItem {
	public ShapedRecipe Snowball() {
		ItemStack result = new ItemStack(Material.SNOWBALL);
		ItemMeta meta = result.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("Запускает игрока");
		meta.setLore(lore);
		result.setItemMeta(meta);
		ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("snowball"), result);
		recipe.shape("EEE","ESE","EEE");
		recipe.setIngredient('E', Material.ENDER_PEARL);
		recipe.setIngredient('S', Material.SNOWBALL);
		return recipe;
	}
}
