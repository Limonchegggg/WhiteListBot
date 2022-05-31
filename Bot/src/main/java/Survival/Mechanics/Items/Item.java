package Survival.Mechanics.Items;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import Api.ConfigCreator;
import console.Logging;

public class Item {
	private String name;
	private List<String> lore;
	private int LvLuse;
	private int durability;
	
	/*
	 * Этот параметр позволяет записать Path на время выполнения команды
	 * Если он пустой, то ничего не произойдет
	 * Для использования нужно писать путь до файла
	 * @Example
	 * path = "players\\Limonchegggg.yml"
	 * \\ - Обязательно потому что \ не будет работать
	 * .yml это расширения файла
	 */
	public String path;
	
	//Создание файла для использования инструмента
	public void CreateCategoria(String name, List<String> lore, short durability, int lvlUse) {
		ConfigCreator.CreateConfig("items\\" + name + ".yml");
		ConfigCreator.get().addDefault("name", name);
		ConfigCreator.get().addDefault("Lore", lore);
		ConfigCreator.get().addDefault("durability", durability);
		ConfigCreator.get().addDefault("lvlUse", lvlUse);
	}
	
	//Получение лора предмета
	public List<String> getLore(String categoria) {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь!");
			return null;
		}
		lore = ConfigCreator.get(path).getStringList("lore");
		return lore;
	}
	
	/*
	 * Добавление энчанта когда игрок добавляет модификатор
	 * Этот параметр не требует path
	 */
	public void addEnchantment(ItemStack item ,Enchantment modify) {
		//Проверка на наличие энчанта на предмете и увеличение уровня
		if(item.getEnchantments().containsKey(modify)) {
			int level = item.getEnchantments().get(modify);
			level++;
			item.removeEnchantment(modify);
			item.addEnchantment(modify, level);
			return;
		}
		//Добавление энчанта если его нет
		item.addEnchantment(modify, 1);
	}
	
	//Проверка возможности использования предмета
	public boolean canUse(String pathPlayer, String categoria) {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до файла игрока!");
			return false;
		}
		if(ConfigCreator.get(pathPlayer).getConfigurationSection(categoria).getInt("lvl") > ConfigCreator.get(path).getInt("lvl")) {
			return true;
		}
		return false;
	}
	
	//Полчение имени предмета из файла
	public String getName() {
		this.name = ConfigCreator.get(path).getString("name");
		return name;
	}
	
	//Получение прочности предмета из файла
	public int getDurability() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return 0;
		}
		this.durability = ConfigCreator.get(path).getInt("durability");
		return durability;
	}
	
	//Получение уровня для использования из файла
	public int getLvluse() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return 0;
		}
		this.LvLuse = ConfigCreator.get(path).getInt("lvlUse");
		return LvLuse;
	}
}
