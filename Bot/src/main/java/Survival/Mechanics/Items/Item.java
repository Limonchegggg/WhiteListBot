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
	
	
	private String path;
	
	//Создание файла для использования инструмента
	/*
	 * name - Имя предмета и будущего файла
	 * в идеале назвать как String ID в майнкрафте для нормальной работы или так как она называется в игре
	 * 
	 * lore - Описание предмета, если необходимо
	 * 
	 * durability - Прочность предмета, выставляется как (Прочность - установленная)
	 * @Example
	 * durability = 250(Прочность железной кирки) - 100(Установленная) = 150 - Полученная прочность, которая установится
	 * 
	 * categoria - Категория к которой будет пренадлежать предмет
	 * lvlUse - Минимальный уровень для использования предмета
	 */
	public void CreateCategoria(String ID ,String name, List<String> lore, short durability, String categoria, int lvlUse) {
		ConfigCreator.CreateConfig("items\\" + ID + ".yml");
		ConfigCreator.get().addDefault("ID", ID);
		ConfigCreator.get().addDefault("name", name);
		ConfigCreator.get().set("Lore", lore);
		ConfigCreator.get().addDefault("durability", durability);
		ConfigCreator.get().addDefault("lvlUse", lvlUse);
		ConfigCreator.get().addDefault("categoria", categoria);
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
	}
	
	/*
	 * Этот параметр позволяет записать Path на время выполнения команды
	 * Если он пустой, то ничего не произойдет
	 * Для использования нужно писать путь до файла
	 * @Example
	 * setPath("players\\Limonchegggg.yml")
	 * \\ - Обязательно потому что \ не будет работать
	 * .yml это расширения файла
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
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
		String localPath = path;
		this.LvLuse = ConfigCreator.get(localPath).getInt("lvlUse");
		return LvLuse;
	}
	
	public String getCategotria() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return null;
		}
		String localPath = path;
		String categoria = ConfigCreator.get(localPath).getString("categoria");
		return categoria;
	}
	
	public String getID() {
		if(path == null) {
			new Logging().Log("Ошибка! Не указан путь до предмета!");
			return null;
		}
		String localPath = path;
		String ID = ConfigCreator.get(localPath).getString("ID");
		return ID;
	}
}
