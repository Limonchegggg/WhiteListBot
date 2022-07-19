package Survival.Mechanics.Items;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.ImmutableMap;

import Api.ConfigCreator;
import Main.Main;

public class Med {
	
	public void CreateSettings() {
		
		ConfigCreator.CreateConfig("MedSettings.yml");
		
		ConfigCreator.get().addDefault("IDs", Arrays.asList("ECHO_SHARD"));
		
		ConfigCreator.get().addDefault("Names", Arrays.asList("Драконий шард"));
		
		ConfigCreator.get().addDefault("Mobs_IDs", Arrays.asList(EntityType.ENDER_DRAGON.name(), EntityType.ZOMBIE.name()));
		ConfigCreator.get().addDefault("MobChanceDrop", 100);
		ConfigCreator.get().addDefault("Mobs_Chances", ImmutableMap.of(EntityType.ENDER_DRAGON.name(), "100-51", EntityType.ZOMBIE.name(), "50-0"));
		
		ConfigCreator.get().addDefault("Effects", Arrays.asList("DAMAGE_RESISTANCE", "JUMP", "SPEED", "FAST_DIGGING"));
		
		ConfigCreator.get().addDefault("Locale", ImmutableMap.of("DAMAGE_RESISTANCE", "Сопротевление", "JUMP", "Прыжок", "SPEED","Скорость","FAST_DIGGING","Спешка"));
		
		ConfigCreator.get().addDefault("ItemChanceDrop", 100);
		ConfigCreator.get().addDefault("ChancesLvlDrop", ImmutableMap.of(5, "100-95", 4, "95-85", 3, "85-70", 2, "70-40", 1, "40-0"));
		
		ConfigCreator.get().addDefault("MaxPerks", 3);
		ConfigCreator.get().addDefault("MaxStrength", 5);
		
		ConfigCreator.get().options().copyDefaults(true);
		ConfigCreator.save();
	}
	
	public ItemStack drop() {
		ItemStack item;
		//Выбор материала для предмета
		int material = 0+(int)(Math.random()*ConfigCreator.get("MedSettings.yml").getStringList("IDs").size());
		
		item = new ItemStack(Material.getMaterial(ConfigCreator.get("MedSettings.yml").getStringList("IDs").get(material)));
		
		ItemMeta meta = item.getItemMeta();
		
		int ranName = 0+(int)(Math.random()*ConfigCreator.get("MedSettings.yml").getStringList("Names").size());
		
		
		//Поддержка цветового текста для имени
		String name = ConfigCreator.get("MedSettings.yml").getStringList("Names").get(ranName);
		for(int a = 0; a < name.length(); a++) {
			name = ChatColor.translateAlternateColorCodes('&', name);
		}
		meta.setDisplayName(name);
		
		
		
		//Добавление лора
		List<String> effects_list = ConfigCreator.get("MedSettings.yml").getStringList("Effects");
		List<String> lore = new ArrayList<String>();
		List<String> loreCon = new ArrayList<String>();
		int maxStr = (ConfigCreator.get("MedSettings.yml").getInt("MaxStrength")+1);
		
		int maxPerks = 1+(int)(Math.random()*ConfigCreator.get("MedSettings.yml").getInt("MaxPerks"));
		
		int chanceConf = ConfigCreator.get("MedSettings.yml").getInt("ItemChanceDrop");
		//Перебор листа с перками
		for(int i=0; i<effects_list.size(); i++) {
			
			int effect = 0+(int)(Math.random()*effects_list.size());
			//Проверка максимума перков
			if(lore.size() >= maxPerks) continue;
			//Проверка наличия перка в лоре
			if(loreCon.contains(effects_list.get(effect))) {
				continue;
			}
			loreCon.add(effects_list.get(effect));
			int chance = 0+(int)(Math.random()*chanceConf);
			//Добавление перков на амулет
			for(int a=1; a<maxPerks; a++) {
				
				int ranStr = 0+(int)(Math.random()*maxStr);
				if(ranStr == 0) ranStr++;
				//Проверка выпавшего шанса
				if(chance < Integer.parseInt(ConfigCreator.get("MedSettings.yml").getConfigurationSection("ChancesLvlDrop").getString(Integer.toString(ranStr)).split("-")[0]) 
						&& chance > Integer.parseInt(ConfigCreator.get().getConfigurationSection("ChancesLvlDrop").getString(Integer.toString(ranStr)).split("-")[1])) {
					String effectNameLore = ConfigCreator.get("MedSettings.yml").getConfigurationSection("Locale").getString(effects_list.get(i))+ " " + "+" + " " + ranStr;
					//Цветной текст
					for(int p = 0; p < effectNameLore.length(); p++) {
						effectNameLore = ChatColor.translateAlternateColorCodes('&', effectNameLore);
					}
					if(!lore.contains(effectNameLore)) {
						lore.add(effectNameLore);
					}
					
				}
			}
		}
		//Если ни 1 эффект не выпал, дается любой
		if(lore.isEmpty() || lore.size() == 0) {
			int ranStr = 0+(int)(Math.random()*(maxStr-2));
			int effect = 0+(int)(Math.random()*effects_list.size());
			if(ranStr == 0) ranStr++;
			String effectNameLore = ConfigCreator.get("MedSettings.yml").getConfigurationSection("Locale").getString(effects_list.get(effect))+ " " + "+" + " " + ranStr;
			//Цветной текст
			for(int p = 0; p < effectNameLore.length(); p++) {
				effectNameLore = ChatColor.translateAlternateColorCodes('&', effectNameLore);
			}
			lore.add(effectNameLore);
		}
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public void loadPerks() {
		List<String> effects = ConfigCreator.get("MedSettings.yml").getStringList("Effects");
		for(int i=0; i<effects.size(); i++) {
			
			String local = ConfigCreator.get().getConfigurationSection("Locale").getString(effects.get(i));
			local = ChatColor.translateAlternateColorCodes('&', local);
			Main.getPlugin(Main.class).perks.put(local, effects.get(i));
		}
	}
	
	public boolean containsMob(EntityType entity) {
		if(ConfigCreator.get("MedSettings.yml").getString("Mobs_IDs").contains(entity.name())) {
			return true;
		}
		return false;
	}
	
	public String getPerk(List<String> lore, int loreLabel) {
		String[] perk = lore.get(loreLabel).split(" ");
		return perk[0];
	}
	
	public int getPerkBuff(List<String> lore, int loreLabel) {
		String[] buff = lore.get(loreLabel).split(" ");
		return Integer.parseInt(buff[(buff.length-1)]);
	}
	
}
