package Survival.Mechanics;

import java.util.HashMap;

import Api.ConfigCreator;
import Main.Main;
import console.Logging;

public class Lvl {
	/*
	 * Это класс для прокачки персонажа
	 * Увеличения его уровня и записи в ДБ
	 * Проверки уровня
	 * Упрощение написания кода
	 * 
	 * Создано Limonchegggg
	 */
	private String PlayerName;
	private int lvl;
	private int experience;
	private Main main = Main.getPlugin(Main.class);
	
	/**
	 * Этот параметр позволяет записать Path на время выполнения команды
	 * Если он пустой, то ничего не произойдет
	 * Для использования нужно писать путь до файла
	 * @Example
	 * path = "players\\Limonchegggg.yml"
	 * \\ - Обязательно потому что \ не будет работать
	 * .yml это расширения файла
	 * @ПАМЯТКА
	 * При использовании path надо записать и использовать его всего 1 раз,
	 * потому что программа теряет его после 1-го использования
	 * это не баг программы, а используемого API
	 * @Example
	 * Config.get(path);
	 * Config.get();
	 * 
	 */
	private String path;

	
	/**
	 * Этот метод создает профиль игрока
	 */
	
	public void CreateProfile(String ProfileName){
		ConfigCreator.CreateConfig(ProfileName);
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
	}
	
	/**
	 * Добавления категории для прокачки
	 * Пример категории
	 * Digging:
	 *  @lvl: 10 - Сам уровень категории
	 *  @ExperianceGoal: 1200 - Необходимое количество экспы для апа уровня
	 *  @Experience: 0 - Экспа для прокачивания
	 *  @IsMaxLvl: false - Зависит от показателя MaxLvl
	 *  @MaxLvl: 50 - Максимальный уровень категории
	 *  
	 *  TODO: Сделать возможность создания кастомных категорий через команды
	 *  
	 */
	public void addCategoria(String categoria, int maxLvl) {
		if(path == null) {
			new Logging().Log("Ошибка создания категории! Не указан путь!");
			return;
		}
		ConfigCreator.get(path).createSection(categoria);
		ConfigCreator.get().getConfigurationSection(categoria).addDefault("lvl", 0);
		ConfigCreator.get().getConfigurationSection(categoria).addDefault("ExperienceGoal", 100);
		ConfigCreator.get().getConfigurationSection(categoria).addDefault("Experience", 0);
		ConfigCreator.get().getConfigurationSection(categoria).addDefault("IsMaxLvl", false);
		ConfigCreator.get().getConfigurationSection(categoria).addDefault("MaxLvl", maxLvl);
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Этот метод увеличивает уровень на 1 единицу при максимальном количестве
	 * параметра experience
	 * @Categoria это предмет тип предметов на которые нужно поднять уровень
	 * @modifycator это число по которому будет увеличиваться количество требуемого experience
	 */
	//TODO: Проверить как работает этот метод
	public void LvLUp(String categoria, double modifycator) {
		if(path == null) {
			new Logging().Log("Ошибка получения уровня! Не указан путь!");
			return;
		}
		this.lvl = ConfigCreator.get(path).getConfigurationSection(categoria).getInt("lvl");
		if(lvl == ConfigCreator.get().getConfigurationSection(categoria).getInt("maxLvl")) {
			return;
		};
		lvl++;
		//Обновление модификатора
		int experienceGoal = (int)(ConfigCreator.get(path).getConfigurationSection(categoria).getInt("ExperienceGoal")*modifycator);
		//Запись уровня игрока в конфиг и обновление требований
		ConfigCreator.get().getConfigurationSection(categoria).set("lvl", lvl);
		ConfigCreator.get().getConfigurationSection(categoria).set("experience", 0);
		ConfigCreator.get().getConfigurationSection(categoria).set("modifycator", experienceGoal);
		new Logging().Log("Игрок " + PlayerName + " повышает свой уровень");
	}
	
	public void setLvl(String categoria, int lvl) {
		if(path == null) {
			new Logging().Log("Ошибка начесления уровня! Не указан путь!");
			return;
		}
		ConfigCreator.get(path).getConfigurationSection(categoria).set("lvl", lvl);
	}
	

	public void addExperience(String categoria) {
		if(path == null) {
			System.out.println("Ошибка получения уровня! Не указан путь!");
			return;
		}
		experience = ConfigCreator.get(path).getConfigurationSection(categoria).getInt("Experience");
		experience++;
		ConfigCreator.get().getConfigurationSection(categoria).set("Experience", experience);
	}
	
	
	
	public int getLvl(String categoria) {
		if(path == null) {
			System.out.println("Ошибка получения уровня!");
			return 0;
		}
		return 0;
	}
	
	public boolean isMaxLvl(String categoria) {
		if(path == null) {
			System.out.println("Ошибка! Не указан путь!");
			return false;
		}
		if(ConfigCreator.get(path).getConfigurationSection(categoria).getString("lvl") == ConfigCreator.get(path).getConfigurationSection(categoria).getString("maxLvl")) {
			return true;
		}
		return false;
	}
	
	public void LoadLvl(String playerName) {
		HashMap<String, Integer> lvl_list = new HashMap<String, Integer>();
		lvl_list.put("lvl", ConfigCreator.get("players\\"+playerName+".yml").getInt("lvl"));
		HashMap<String, HashMap<String, Integer>> Categoryes = new HashMap<String, HashMap<String, Integer>>();
		Categoryes.put("Digging", lvl_list);
		//main.player_category.put(playerName, Categoryes);
		return;
		
	}
}
