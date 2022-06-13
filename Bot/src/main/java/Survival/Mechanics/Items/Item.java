package Survival.Mechanics.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import Api.ConfigCreator;
import Main.Main;
import Survival.SurvivalMain;
import console.Logging;

public class Item {
	private String name;
	private List<String> lore;
	private int LvLuse;
	private int durability;
	
	/**
	 * Этот параметр позволяет записать Path на время выполнения команды
	 * Если он пустой, то ничего не произойдет
	 * Для использования нужно писать путь до файла
	 * @Example
	 * path = "players\\Limonchegggg.yml"
	 * \\ - Обязательно потому что \ не будет работать
	 * .yml это расширения файла
	 * 
	 * @ПАМЯТКА
	 * При использовании path надо записать и использовать его всего 1 раз,
	 * потому что программа теряет его после 1-го использования
	 * это не баг программы, а используемого API
	 * @Example
	 * Config.get(path);
	 * Config.get();
	 * 
	 */
	public String path;

	/**
	 * Создание файла для использования инструмента
	 * name - Имя предмета и будущего файла
	 * в идеале назвать как String ID в майнкрафте для нормальной работы
	 * 
	 * @lore - Описание предмета, если необходимо
	 * 
	 * @durability - Прочность предмета, выставляется как (Прочность - установленная)
	 * @Example
	 * durability = 250(Прочность железной кирки) - 100(Установленная) = 150 - Полученная прочность, которая установится
	 * 
	 * @categoria - Категория к которой будет пренадлежать предмет
	 * @lvlUse - Минимальный уровень для использования предмета
	 */
	public void CreateItem(String ID ,String name, List<String> lore, short durability, String categoria, int lvlUse) {
		ConfigCreator.CreateConfig("items\\" + ID + ".yml");
		ConfigCreator.get().addDefault("ID", ID);
		ConfigCreator.get().addDefault("name", name);
		ConfigCreator.get().set("Lore", lore);
		ConfigCreator.get().addDefault("durability", durability);
		ConfigCreator.get().addDefault("lvlUse", lvlUse);
		ConfigCreator.get().addDefault("categoria", categoria);
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
		
		List<String> list = ConfigCreator.get("items\\settings.yml").getStringList("items");
		list.add(name);
		 ConfigCreator.get().set("items", list);
		
	}
	


	
	public void CreateCategory(String Category) {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь!");
			return;
		}
		List<String> list = ConfigCreator.get(path).getStringList("Categoryes");
		list.add(Category);
		ConfigCreator.get().set("Categoryes", list);
	}

	
	public List<String> getLore(String categoria) {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь!");
			return null;
		}
		lore = ConfigCreator.get(path).getStringList("lore");
		return lore;
	}
	
	/**
	 * Добавление энчанта когда игрок добавляет модификатор
	 * Этот параметр не требует path
	 */
	public void addEnchantment(ItemStack item, Enchantment modify) {
		
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
	
	
	public String getName() {
		this.name = ConfigCreator.get(path).getString("name");
		return name;
	}
	
	
	public int getDurability() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return 0;
		}
		this.durability = ConfigCreator.get(path).getInt("durability");
		return durability;
	}
	

	public int getLvlToUse() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return 0;
		}
		this.LvLuse = ConfigCreator.get(path).getInt("lvlUse");
		return LvLuse;
	}
	
	public String getCategotria() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return null;
		}
		String categoria = ConfigCreator.get(path).getString("categoria");
		return categoria;
	}
	
	public String getID() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return null;
		}
		String ID = ConfigCreator.get(path).getString("ID");
		return ID;
	}
	/**
	 *Этот метод загружает предметы в реестр сервера для упрощения проверок
	 */
	public void LoadItems() {
		Main main = Main.getPlugin(Main.class);
		SurvivalMain sm = new SurvivalMain(main);
		HashMap<String, Integer> levels = new HashMap<String, Integer>();
		
		if(ConfigCreator.getConfigList("items\\").size() == 0) {
			new Logging().Log(("List is empty, items cannot be loaded!"));
			return;
		}
		ArrayList<String> item_names = ConfigCreator.getConfigList("items\\");
		for(int i=0; i<item_names.size(); i++) {
			String item = item_names.get(i);
			String[] item_name = item.split(".");
			if(ConfigCreator.isYMLFile(item)) {
			//	main.item_lvl.put(item_name[0], ConfigCreator.get("items\\"+item).getInt("LvlUse"));
			}
		}
		new Logging().Log(("Success, items was loaded!"));
		return;
	}
}
