package Survival.Mechanics.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import Api.ConfigCreator;
import Main.Main;
import console.Logging;

public class Item {
	private List<String> lore;
	
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
	
	public int getLvl(String name) {
		Main main = Main.getPlugin(Main.class);
		try {
			
			return main.item_lvl.get(name);
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public boolean isExistItem(String name) {
		Main main = Main.getPlugin(Main.class);
		if(!main.item_lvl.containsKey(name)) {
			return false;
		}
		return true;
	}
	
	
	
	/**
	 *Этот метод загружает предметы в реестр сервера для упрощения проверок
	 */
	public void LoadItems() {
		Main main = Main.getPlugin(Main.class);
		
		if(ConfigCreator.getConfigList("items\\").size() == 0) {
			new Logging().Log(("List is empty, items cannot be loaded!"));
			return;
		}
		ArrayList<String> item_names = ConfigCreator.getConfigList("items\\");
		for(int i=0; i<item_names.size(); i++) {
			main.item_lvl.put(item_names.get(i).replace(".yml", ""), ConfigCreator.get("items\\"+item_names.get(i)).getInt("lvlUse"));
		}
		for(Entry<String, Integer> key : main.item_lvl.entrySet()) {
			System.out.println(key.getKey() + ": " + key.getValue());
		}
		new Logging().Log("Success, items was loaded!");
		return;
	}
	
	
	/**
	 * Метод загружает блоки или предметы с которых не будет падать опыт
	 */
	public void LoadBlackList() {
		Main main = Main.getPlugin(Main.class);
		if(ConfigCreator.get("items\\BlackList.yml") == null) {
			ConfigCreator.CreateConfig("items\\BlackList.yml");
			ConfigCreator.get().createSection("BlackList");
			ConfigCreator.get().options().copyDefaults(true);
		}
		
		main.BlackListBlock = ConfigCreator.get("items\\BlackList.yml").getStringList("BlackList");
		
	}
	
	public void addToBlackList(String itemName) {
		List<String> list = ConfigCreator.get("items\\BlackList.yml").getStringList("BlackList");
		list.add(itemName);
		ConfigCreator.get().set("BlackList", list);
		Main.getPlugin(Main.class).BlackListBlock = ConfigCreator.get("items\\BlackList.yml").getStringList("BlackList");
	}
	
	public void removeToBlackList(String itemName) {
		List<String> list = ConfigCreator.get("items\\BlackList.yml").getStringList("BlackList");
		list.remove(itemName);
		ConfigCreator.get().set("BlackList", list);
		Main.getPlugin(Main.class).BlackListBlock = ConfigCreator.get("items\\BlackList.yml").getStringList("BlackList");
	}
	
	public boolean IsBlackListed(String name) {
		if(Main.getPlugin(Main.class).BlackListBlock.contains(name)) {
			return true;
		}
		return false;
	}
}
