package Survival.Mechanics;

import Api.ConfigCreator;
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
	
	/*
	 * Этот метод создает профиль игрока
	 */
	
	public void CreateProfile(String ProfileName){
		ConfigCreator.CreateConfig("players\\" + ProfileName+".yml");
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
	}
	
	//Добавления категории для прокачки
	public void addCategoria(String categoria, int maxLvl) {
		if(path == null) {
			new Logging().Log("Ошибка создания категории! Не указан путь!");
			return;
		}
		ConfigCreator.get(path).createSection(categoria);
		ConfigCreator.get(path).getConfigurationSection(categoria).addDefault("lvl", 0);
		ConfigCreator.get(path).getConfigurationSection(categoria).addDefault("ExperienceGoal", 100);
		ConfigCreator.get(path).getConfigurationSection(categoria).addDefault("experience", 0);
		ConfigCreator.get(path).getConfigurationSection(categoria).addDefault("IsMaxLvl", false);
		ConfigCreator.get(path).getConfigurationSection(categoria).addDefault("MaxLvl", maxLvl);
	}
	
	/*
	 * Этот метод увеличивает уровень на 1 единицу при максимальном количестве
	 * параметра experience
	 * Categoria это предмет тип предметов на которые нужно поднять уровень
	 * modifycator это число по которому будет увеличиваться количество требуемого experience
	 */
	public void LvLUp(String categoria, double modifycator) {
		if(path == null) {
			new Logging().Log("Ошибка получения уровня! Не указан путь!");
			return;
		}
		this.lvl = ConfigCreator.get(path).getConfigurationSection(categoria).getInt("lvl");
		if(lvl == ConfigCreator.get(path).getConfigurationSection(categoria).getInt("maxLvl")) {
			return;
		};
		lvl++;
		//Обновление модификатора
		int experienceGoal = (int)(ConfigCreator.get(path).getConfigurationSection(categoria).getInt("ExperienceGoal")*modifycator);
		
		//Запись уровня игрока в конфиг и обновление требований
		ConfigCreator.get(path).getConfigurationSection(categoria).set("lvl", lvl);
		ConfigCreator.get(path).getConfigurationSection(categoria).set("experience", 0);
		ConfigCreator.get(path).getConfigurationSection(categoria).set("modifycator", experienceGoal);
		new Logging().Log("Игрок " + PlayerName + " повышает свой уровень");
	}
	
	public void setLvl(String categoria, int lvl) {
		if(path == null) {
			new Logging().Log("Ошибка начесления уровня! Не указан путь!");
			return;
		}
		ConfigCreator.get(path).getConfigurationSection(categoria).set("lvl", lvl);
	}
	
	/*
	 * Добавление очков уровня за какое либо действие
	 */
	public void addExperience(String categoria) {
		if(path == null) {
			System.out.println("Ошибка получения уровня! Не указан путь!");
			return;
		}
		experience = ConfigCreator.get(path).getConfigurationSection(categoria).getInt("experience");
		experience++;
		ConfigCreator.get(path).getConfigurationSection(categoria).set("experionce", experience);
	}
	
	
	
	public int getLvl(String categoria) {
		if(path == null) {
			System.out.println("Ошибка получения уровня! Не указан путь!");
			return 0;
		}
		return ConfigCreator.get().getConfigurationSection(categoria).getInt("lvl");
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
}
