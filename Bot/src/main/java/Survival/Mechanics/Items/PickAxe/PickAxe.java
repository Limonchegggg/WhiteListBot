package Survival.Mechanics.Items.PickAxe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import Api.ConfigCreator;
import Survival.Mechanics.Items.Item;

public class PickAxe{

	
	@SuppressWarnings("deprecation")
	/*
	 * Создаем рецепт каменной кирки
	 * И ее файл в котором будут записаны характеристики, если их нет
	 */
	public ShapedRecipe CreateStonePickaxe() {
		//Установка пути до файла предмета
		String path = "items\\StonePickAxe.yml";
		//Провека на существование конфика и его создание при необходимости
		if(ConfigCreator.get(path) == null) {
			Item item = new Item();
			List<String> lore = new ArrayList<String>();
			lore.set(0, "Чуть лучше чем деревянная");
			lore.set(1, "но хочется больше");
			item.CreateCategoria("Каменная кирка", lore, (short)52, 10);
		}
		
		ItemStack item = new ItemStack(Material.STONE_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		
		meta.setLore(ConfigCreator.get(path).getStringList("lore"));
		meta.setDisplayName(ConfigCreator.get(path).getString("name"));
		item.setDurability((short)ConfigCreator.get(path).getInt("durability"));
		
		item.setItemMeta(meta);
		
		ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("stone_pickaxe"), item);
		recipe.shape("CCC"," S "," S ");
		recipe.setIngredient('C', Material.COBBLESTONE);
		recipe.setIngredient('S', Material.STICK);
		
		return recipe;
	}
	
	@SuppressWarnings("deprecation")
	public ShapedRecipe CreateIronPickaxe() {
		//Установка пути до файла предмета
		String path = "items\\IronPickAxe.yml";
		//Проверка на существование файла и его создание при необходимости
		if(ConfigCreator.get(path) == null) {
			Item item = new Item();
			List<String> lore = new ArrayList<String>();
			lore.set(0, "Уже намного лучше");
			lore.set(1, "но хочется больше!");
			item.CreateCategoria("Железная кирка", lore, (short)101, 10);
		}
		ItemStack item = new ItemStack(Material.STONE_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		
		meta.setLore(ConfigCreator.get(path).getStringList("lore"));
		meta.setDisplayName(ConfigCreator.get(path).getString("name"));
		item.setDurability((short)ConfigCreator.get(path).getInt("durability"));
		
		item.setItemMeta(meta);
		
		ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("iron_pickaxe"), item);
		recipe.shape("III"," S "," S ");
		recipe.setIngredient('I', Material.IRON_PICKAXE);
		recipe.setIngredient('S', Material.STICK);
		
		return recipe;
	}
	
}
