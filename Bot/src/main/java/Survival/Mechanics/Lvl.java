package Survival.Mechanics;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import Api.ConfigCreator;
import Main.Main;
import Survival.Mechanics.Items.Category;
import console.Logging;
import net.md_5.bungee.api.ChatColor;

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
	 *  
	 */
	public void addCategoria(String categoria, int maxLvl, String localPath) {
		if(localPath == null) {
			new Logging().Log("Ошибка создания категории! Не указан путь!");
			return;
		}
		if(ConfigCreator.get(localPath).contains(categoria)) {
			return;
		}
		ConfigCreator.get(localPath).createSection(categoria);
		ConfigCreator.get().getConfigurationSection(categoria).set("lvl", 0);
		ConfigCreator.get().getConfigurationSection(categoria).set("ExperienceGoal", 100);
		ConfigCreator.get().getConfigurationSection(categoria).set("Experience", 0);
		ConfigCreator.get().getConfigurationSection(categoria).set("IsMaxLvl", false);
		ConfigCreator.get().getConfigurationSection(categoria).set("MaxLvl", maxLvl);
		ConfigCreator.save();
		ConfigCreator.reload();
	}
	
	public int getLvlFromConfig(String name, String categoria) {
		if(ConfigCreator.get("players" + File.separator + name + ".yml") == null) {
			return 0;
		}
		return ConfigCreator.get("players" + File.separator + name + ".yml").getConfigurationSection(categoria).getInt("lvl");
	}
	public int getExperienceFromConfig(String name, String categoria) {
		if(ConfigCreator.get("players" + File.separator + name + ".yml") == null) {
			return 0;
		}
		return ConfigCreator.get("players" + File.separator + name + ".yml").getConfigurationSection(categoria).getInt("Experience");
	}
	public int getExperienceGoalFromConfig(String name, String categoria) {
		if(ConfigCreator.get("players" + File.separator + name + ".yml") == null) {
			return 0;
		}
		return ConfigCreator.get("players" + File.separator + name + ".yml").getConfigurationSection(categoria).getInt("ExperienceGoal");
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
		Player player = Bukkit.getPlayer(name);
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
		
		int lvl = getLvl(name, category)+1;
		//Увеличение уровня на 1 единицу ПУТЬ -> Name -> CATEGORY -> LVL
		main.player_category.get(name).get(category).replace("lvl", lvl);
		main.player_category.get(name).get(category).replace("goal", (int) (getExpGoal(name, category)*modify));
		
		main.player_category.get(name).get(category).replace("experience", 0);
		
		
		Scoreboard board = player.getScoreboard();
		Team team = board.getTeam(name);
		team.setSuffix(ChatColor.GREEN +" [" + ChatColor.YELLOW + getLvl(player.getName(), Category.Digging.getTitle()) + " уровень" + ChatColor.GREEN +"]");
		
	}
	public void lvlDown(String name, String category, double modify) {
		Main main = Main.getPlugin(Main.class);
		Player player = Bukkit.getPlayer(name);
		if(name == null) {
			new Logging().Log("FAIL! name is null! LvlUp is not Work!");
			return;
		}
		if(category == null) {
			new Logging().Log("FAIL! category is null! LvlUp is not Work!");
			return;
		}
		if(!main.player_category.containsKey(name)) {
			new Logging().Log("FAIL! Name is not EXISTS! LvlUp is not Work!");
			return;
		}
		int lvl = getLvl(name, category);
		
		int nextLvl = lvl-1;
		
		if(nextLvl < 0) {
			nextLvl = 0;
		}
		
		int exp = 100;
		for(int i=0; i<nextLvl; i++) {
			exp = (int) (exp*modify);
		}
		
		//Уменьшения уровня на 1 единицу ПУТЬ -> Name -> CATEGORY -> LVL
		main.player_category.get(name).get(category).replace("lvl", nextLvl);
		main.player_category.get(name).get(category).replace("goal", exp);
		
		main.player_category.get(name).get(category).replace("experience", 0);
		
		Scoreboard board = player.getScoreboard();
		Team team = board.getTeam(name);
		team.setSuffix(ChatColor.GREEN +" [" + ChatColor.YELLOW + getLvl(player.getName(), Category.Digging.getTitle()) + " уровень" + ChatColor.GREEN +"]");
	}
	
	public void setLvl(String name, String category, double modify, int lvl) {
		Main main = Main.getPlugin(Main.class);
		Player player = Bukkit.getPlayer(name);
		if(name == null) {
			new Logging().Log("FAIL! name is null! LvlUp is not Work!");
			return;
		}
		if(category == null) {
			new Logging().Log("FAIL! category is null! LvlUp is not Work!");
			return;
		}
		if(!main.player_category.containsKey(name)) {
			new Logging().Log("FAIL! Name is not EXISTS! LvlUp is not Work!");
			return;
		}
		
		int exp = getExperience(name, category);
		
		for(int i=0; i<lvl; i++) {
			exp = (int) (exp*modify);
		}
		
		main.player_category.get(name).get(category).replace("lvl", lvl);
		main.player_category.get(name).get(category).replace("goal", exp);
		
		main.player_category.get(name).get(category).replace("experience", 0);
		
		Scoreboard board = player.getScoreboard();
		Team team = board.getTeam(name);
		team.setSuffix(ChatColor.GREEN +" [" + ChatColor.YELLOW + getLvl(player.getName(), Category.Digging.getTitle()) + " уровень" + ChatColor.GREEN +"]");
		
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
		lvl_list.put("lvl", ConfigCreator.get("players"+ File.separator +playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).getInt("lvl"));
		lvl_list.put("experience", ConfigCreator.get("players"+ File.separator +playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).getInt("Experience"));
		lvl_list.put("goal", ConfigCreator.get("players"+ File.separator +playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).getInt("ExperienceGoal"));
		HashMap<String, HashMap<String, Integer>> Categoryes = new HashMap<String, HashMap<String, Integer>>();
		Categoryes.put("Digging", lvl_list);
		main.player_category.put(playerName, Categoryes);
		return;
		
	}
	
	public void SaveLvl(String playerName) {
		Main main = Main.getPlugin(Main.class);
		if(!main.player_category.containsKey(playerName)) return;
		HashMap<String, Integer> stats = main.player_category.get(playerName).get(Category.Digging.getTitle());
		ConfigCreator.get("players"+ File.separator +playerName+".yml").getConfigurationSection(Category.Digging.getTitle()).set("lvl", stats.get("lvl"));
		ConfigCreator.get().getConfigurationSection(Category.Digging.getTitle()).set("ExperienceGoal", stats.get("goal"));
		ConfigCreator.get().getConfigurationSection(Category.Digging.getTitle()).set("Experience", stats.get("experience"));
		ConfigCreator.save();
		ConfigCreator.reload();
		
	} 
}
