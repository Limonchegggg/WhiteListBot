package Survival.Mechanics.Items;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Api.ConfigCreator;
import Main.Main;
import console.Logging;

public class Modifycator{
	/**
	 * Загружает моды в память сервера
	 */
	
	public void loadMods() {
		try {
		List<String> Configs = ConfigCreator.getConfigList("mods"+File.separator);
		if(!ConfigCreator.getConfigList("mods"+File.separator).contains("ExampleMod.yml")) {
			ConfigCreator.CreateConfig("mods"+File.separator+"ExampleMod.yml");
			ConfigCreator.get().set("Name", "Высокий прыжок"); 
			ConfigCreator.get().set("Lore", "Дает возможность прыгать выше"); //Добавляет лор к предмету
			ConfigCreator.get().set("ItemId", Material.RABBIT_FOOT.name());
			ConfigCreator.get().set("BuffList", Arrays.asList("JUMPING"));
			ConfigCreator.get().createSection("Buffs").set("JUMPING", 1); // Добавляет баф
			ConfigCreator.get().set("MaxMods", 2); // Максимум модов на предмет
			ConfigCreator.get().set("LvlUse", 5); // Минимальный уровень для добавления
			ConfigCreator.get().options().copyDefaults(true);
			ConfigCreator.save();
		}
		new Logging().Log("---MODS---");
		for(int i=0; i<Configs.size(); i++) {
			String Filename = Configs.get(i);
			String Name = Filename.replace(".yml", "");
			String Lore = ConfigCreator.get("mods"+File.separator+Filename).getString("Lore");
			Material material = Material.getMaterial(ConfigCreator.get("mods"+File.separator+Filename).getString("ItemId"));
			int MaxMods = ConfigCreator.get("mods"+File.separator+Filename).getInt("MaxMods");
			int LvlUse = ConfigCreator.get("mods"+File.separator+Filename).getInt("LvlUse");
			
			List<String> buffs = ConfigCreator.get("mods"+File.separator+Filename).getStringList("BuffList"); // Берет только последнее значение
			int modBuff = 0;
			String keyBuff = null;
			for(int a=0; a<buffs.size(); a++) {
				modBuff = ConfigCreator.get("mods"+File.separator+Filename).getConfigurationSection("Buffs").getInt(buffs.get(a));
				keyBuff = buffs.get(a);
			}
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Name);
			List<String> lore = new ArrayList<String>();
			lore.add(Lore);
			lore.add("Дает " + keyBuff + " +" + modBuff);
			lore.add("Максимальное количество для использования " + MaxMods);
			lore.add("Минимальный уровень для использования " + LvlUse);
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			Main.getPlugin(Main.class).mods.put(Name, item);
		}
		
		for(Entry<String, ItemStack> key : Main.getPlugin(Main.class).mods.entrySet()) {

			System.out.println(key.getKey());
		}
		new Logging().Log("---MODS LOADED---");
		
	}catch(Exception e) {
		new Logging().Log("---ERROR MODS LOAD");
		e.printStackTrace();
		}
	}
	
	public int getMinLvl(ItemStack item) {
		try {
		ItemMeta meta = item.getItemMeta();
		String lvlParcer = null;
		List<String> lore = meta.getLore();
		for(int i=0; i<lore.size(); i++) {
			if(lore.get(i).contains("Минимальный уровень для использования ")) {
				lvlParcer = lore.get(i);
				continue;
			}
		}
		
		String[] args = lvlParcer.split(" ");
		
		int lvl = Integer.parseInt(args[4]);
		
		return lvl;
		}catch(Exception e) {
			System.out.println("FAIL! Cannot get lvl");
			return 0;
		}
	}
	
	public int getUse(ItemStack item) {
		try {
			ItemMeta meta = item.getItemMeta();
			String lvlParcer = null;
			List<String> lore = meta.getLore();
			for(int i=0; i<lore.size(); i++) {
				if(lore.get(i).contains("Максимальное количество для использования ")) {
					lvlParcer = lore.get(i);
					continue;
				}
			}
			
			String[] args = lvlParcer.split(" ");
			
			int lvl = Integer.parseInt(args[4]);
			
			return lvl;
		}catch(Exception e) {
			System.out.println("FAIL! Cannot get use times");
			return 0;
		}
	}
	/**
	 * 
	 * getting Buff name
	 * 
	 * @param item
	 * @return
	 */
	public String getBuff(ItemStack item) {
		try {
			ItemMeta meta = item.getItemMeta();
			String lvlParcer = null;
			List<String> lore = meta.getLore();
			for(int i=0; i<lore.size(); i++) {
				if(lore.get(i).contains("Дает ")) {
					lvlParcer = lore.get(i);
					continue;
				}
			}
			
			String[] args = lvlParcer.split(" ");
			
			return args[1];
		}catch(Exception e) {
			System.out.println("FAIL! Cannot get buff");
			return null;
		}
	}
	/**
	 * 
	 * getting Buff lvl
	 * 
	 * @param item
	 * @return
	 */
	public int getLvlBuff(ItemStack item) {
		try {
			ItemMeta meta = item.getItemMeta();
			String lvlParcer = null;
			List<String> lore = meta.getLore();
			for(int i=0; i<lore.size(); i++) {
				if(lore.get(i).contains("Дает ")) {
					lvlParcer = lore.get(i);
					continue;
				}
			}
			
			String[] args = lvlParcer.split(" ");
			
			String num = args[2].replace("+", "");
			
			return Integer.parseInt(num);
		}catch(Exception e) {
			System.out.println("FAIL! Cannot get lvl buff");
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean containsMod(ItemStack item) {
		if(!Main.getPlugin(Main.class).mods.containsKey(item.getItemMeta().getDisplayName())) {
			return false;
		}
		return true;
	}
	
	public ItemStack addMod(ItemStack item ,ItemStack mod, BuffsType buff) {
		ItemMeta metaMod = mod.getItemMeta();
		List<String> loreMod = metaMod.getLore();
		
		ItemMeta itemMeta = item.getItemMeta();
		List<String> ItemLore = itemMeta.getLore();
		
		if(!Main.getPlugin(Main.class).mods.containsKey(metaMod.getDisplayName())) {
			return null;
		}
		
		Item it = new Item();
		
		switch(buff) {
		case копание:
			return null;
		case прыжок:
			try {
				for(int i=0; i<ItemLore.size(); i++) {
					if(ItemLore.get(i).toLowerCase().contains("Прыжок ")) {
						ItemLore.set(i ,"Прыжок " + it.getBuffLvl(item, buff)+1);
					}
				}
			
			}catch(Exception ee) {
				ItemLore.add("Прыжок " + 1);
			}
			return null;
		case скорость:
			return null;
		default:
			return null;
		
		}
		
	}
	
	public enum BuffsType{
		прыжок("JUMP"),
		скорость("SPEED"),
		копание("FAST_DIGGING");
		
		String name;
		
		BuffsType(String name) {
			this.name = name;
		}
		
		public String getTitle() {
			return name;
		}
	}
	
	public List<String> getBuffsTypes(){
		return Arrays.asList("прижок","скорость","копание");
	}
}


