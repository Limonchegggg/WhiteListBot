package Survival.Mechanics;

import java.util.HashMap;

import Api.ConfigCreator;
import Main.Main;
import Survival.Mechanics.Items.Category;
import console.Logging;

public class Lvl {
	/**
	 * Это класс для прокачки персонажа
	 * Увеличения его уровня и записи в ДБ
	 * Проверки уровня
	 * Упрощение написания кода
	 * 
	 * @Creator Limonchegggg
	 */

	
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
	 *  @LocalPath: Путь до фала игрока
	 *  
	 *  TODO: Сделать возможность создания кастомных категорий через команды
	 *  
	 */
	public void addCategoria(String categoria, int maxLvl, String localPath) {
		System.out.println("begin");
		if(localPath == null) {
			new Logging().Log("Ошибка создания категории! Не указан путь!");
			return;
		}
		if(ConfigCreator.get(localPath).contains(categoria)) {
			return;
		}
		ConfigCreator.get(localPath).createSection(categoria);
		System.out.println("1");
		ConfigCreator.get().getConfigurationSection(categoria).set("lvl", 0);
		System.out.println("2");
		ConfigCreator.get().getConfigurationSection(categoria).set("ExperienceGoal", 100);
		System.out.println("3");
		ConfigCreator.get().getConfigurationSection(categoria).set("Experience", 0);
		System.out.println("4");
		ConfigCreator.get().getConfigurationSection(categoria).set("IsMaxLvl", false);
		System.out.println("5");
		ConfigCreator.get().getConfigurationSection(categoria).set("MaxLvl", maxLvl);
		System.out.println("6");
		ConfigCreator.save();
		ConfigCreator.reload();
	}
	
	
	
	/**
	 * Этот метод увеличивает уровень на 1 единицу при максимальном количестве
	 * параметра experience
	 * @Categoria тип предметов на которые нужно поднять уровень
	 * @modify это число по которому будет увеличиваться количество требуемого experience
	 */
	public void addExperience(int experience, String name, String category) {
		Main main = Main.getPlugin(Main.class);
		if(name == null) {
			new Logging().Log("FAIL! name is null! Add experience is not work!");
			return;
		}
		if(category == null) {
			new Logging().Log("FAIL! Category is null! Add experience is not work!");
			return;
		}
		if(!main.player_category.containsKey(name)) {
			new Logging().Log("FAIL! Name is not EXISTS! Add experience is not work!");
			return;
		}
		//Увеличение Experience на 1 удиницу ПУТЬ -> Name -> CATEGORY -> LVL
		main.player_category.get(name).get(category).replace("experience", getExperience(name, category)+experience);
	}
	
	public int getExperience(String name, String category) {
		Main main = Main.getPlugin(Main.class);
		if(name == null) {
			new Logging().Log("FAIL! Name is null");
			return 0;
		}
		if(category == null) {
			new Logging().Log("FAIL! Category is null! Getting experience is not work!");
			return 0;
		}
		if(!main.player_category.containsKey(name)) {
			new Logging().Log("FAIL! Name not EXISTS! Getting experience is not work!");
			return 0;
		}
		try {
			//Получение уровня ПУТЬ -> Name -> CATEGORY -> LVL
			return main.player_category.get(name).get(category).get("experience");
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
			return 0;
		}
	}
	/**
	 * Увеличение уровня на 1 единицу
	 * @modify
	 *  отвечает за изменение необхходимого количество опыта
	 * 
	 */
	public void lvlUp(String name, String category, double modify) {
		Main main = Main.getPlugin(Main.class);
		if(name == null) {
			new Logging().Log("FAIL! name is null! LvlUp is not Work!");
			return;
		}
		if(category == null) {
			new Logging().Log("FAIL! category is null! LvlUp is not Work!");
			return;
		}
		if(isMaxLvl(name, category)) {
			return;
		}
		if(!main.player_category.containsKey(name)) {
			new Logging().Log("FAIL! Name is not EXISTS! LvlUp is not Work!");
			return;
		}
		
		
		//Увеличение уровня на 1 единицу ПУТЬ -> Name -> CATEGORY -> LVL
		main.player_category.get(name).get(category).replace("lvl", (1+getLvl(name, category)));
		main.player_category.get(name).get(category).replace("goal", (int) (getExpGoal(name, category)*modify));
		
		main.player_category.get(name).get(category).replace("experience", 0);
		
	}
	
	public int getExpGoal(String name, String category) {
		Main main = Main.getPlugin(Main.class);
		if(name == null) {
			new Logging().Log("FAIL! name is null! getExpGoal is not Work!");
			return 0;
		}
		if(category == null) {
			new Logging().Log("FAIL! category is null! getExpGoal is not Work!");
			return 0;
		}
		
		if(!main.player_category.containsKey(name)) {
			new Logging().Log("FAIL! Name is not EXISTS! getExpGoal is not Work!");
			return 0;
		}
		return main.player_category.get(name).get(category).get("goal");
	}
	
	public boolean isMaxLvl(String name, String category) {
		Main main = Main.getPlugin(Main.class);
		if(name == null) {
			new Logging().Log("FAIL! name is null! isMaxLvl is not Work!");
			return false;
		}
		if(category == null) {
			new Logging().Log("FAIL! category is null! isMaxLvl is not Work!");
			return false;
		}
		
		if(!main.player_category.containsKey(name)) {
			new Logging().Log("FAIL! Name is not EXISTS! isMaxLvl is not Work!");
			return false;
		}
		
		if(getLvl(name, category) >= 50) {
			return true;
		}
		
		return false;
	}
	
	public int getLvl(String name, String Category) {
		Main main = Main.getPlugin(Main.class);
		if(name == null || Category == null) {
			System.out.println("Ошибка получения уровня!");
			return 0;
		}
		return main.player_category.get(name).get(Category).get("lvl");
	}
	
	
	
	public void LoadLvl(String playerName) {
		Main main = Main.getPlugin(Main.class);
		HashMap<String, Integer> lvl_list = new HashMap<String, Integer>();
		lvl_list.put("lvl", ConfigCreator.get("players\\"+playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).getInt("lvl"));
		lvl_list.put("experience", ConfigCreator.get("players\\"+playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).getInt("Experience"));
		lvl_list.put("goal", ConfigCreator.get("players\\"+playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).getInt("ExperienceGoal"));
		HashMap<String, HashMap<String, Integer>> Categoryes = new HashMap<String, HashMap<String, Integer>>();
		Categoryes.put("Digging", lvl_list);
		main.player_category.put(playerName, Categoryes);
		return;
		
	}
	
	public void SaveLvl(String playerName) {
		Main main = Main.getPlugin(Main.class);
		HashMap<String, Integer> stats = main.player_category.get(playerName).get(Category.Digging.getTitle());
		ConfigCreator.get("players\\"+playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).set("lvl", stats.get("lvl"));
		ConfigCreator.get().getConfigurationSection(Category.Digging.getTitle()).set("ExperienceGoal", stats.get("goal"));
		ConfigCreator.get().getConfigurationSection(Category.Digging.getTitle()).set("Experience", stats.get("experience"));
		ConfigCreator.save();
		ConfigCreator.reload();
		
	}
}
