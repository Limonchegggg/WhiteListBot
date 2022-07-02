package Survival.Mechanics.Items.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import Api.ConfigCreator;
import Survival.Mechanics.Lvl;
import Survival.Mechanics.Items.Category;
import net.md_5.bungee.api.ChatColor;

public class AdminCommands implements CommandExecutor,TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("bwt.admin.lvl")) {
			sender.sendMessage(ChatColor.RED + "У вас нет прав!");
			return false;
		}
		if(Bukkit.getPlayer(args[1]) == null) {
			sender.sendMessage(ChatColor.GRAY + "Игрока нет на сервре!");
			return false;
		}
		if(args.length < 1) {
			sender.sendMessage(ChatColor.GRAY + "/lvl add/remove <ник>");
			return false;
		}
		Player player = Bukkit.getPlayer(args[1]);
		try {
		Lvl lvl = new Lvl();
		
		switch(args[0]) {
		case "add":
			if(lvl.isMaxLvl(args[1], Category.Digging.getTitle())) {
				sender.sendMessage(ChatColor.GRAY + "У игрока максимальный уровень!");
				return false;
			}
			int times = Integer.parseInt(args[2]);
			for(int i=1; i<times; i++) {
				lvl.lvlUp(args[1], Category.Digging.getTitle(), 1.20);
			}
			lvl.lvlUp(player.getName(), Category.Digging.getTitle(), 1.20);
			player.sendMessage(ChatColor.GRAY + "Вы увеличели уровень! Теперь он равен " + lvl.getLvl(player.getName(), Category.Digging.getTitle()));
			player.sendMessage(ChatColor.GREEN + "------Теперь вам доступно------");
			ArrayList<String> list = ConfigCreator.getConfigList("items"+ File.separator);
			for(int i=0; i<list.size(); i++) {
				if(ConfigCreator.get("items"+ File.separator +list.get(i)).getInt("lvlUse") == lvl.getLvl(player.getName(), Category.Digging.getTitle())) {
					String item = list.get(i).replace(".yml", "");
					player.sendMessage(ChatColor.GRAY + item);
				}
			}
			player.sendMessage(ChatColor.GREEN + "-------------------------------");
			sender.sendMessage(ChatColor.GRAY + "Вы добавили игроку " + ChatColor.YELLOW + args[1] + " " + args[2] + ChatColor.GRAY + " уровней");
			return false;
		case "remove":
			int times1 = Integer.parseInt(args[2]);
			for(int i=0; i<times1; i++) {
				lvl.lvlDown(args[1], Category.Digging.getTitle(), 1.20);
			}

			sender.sendMessage(ChatColor.GRAY + "Вы убрали у игрока " + ChatColor.YELLOW + args[1] + " " + args[2] + ChatColor.GRAY + " уровней");
			player.sendMessage(ChatColor.GRAY + "У вас забрали " + args[2] + " уровней");
			return false;
		case "get":
			sender.sendMessage(ChatColor.GRAY + "Уровень игрока: " + lvl.getLvl(args[1], Category.Digging.getTitle()));
			sender.sendMessage(ChatColor.GRAY + "Прогресс равен уровня: " + lvl.getExperience(args[1], Category.Digging.getTitle()) + "/" + lvl.getExpGoal(args[1], Category.Digging.getTitle()));
			return false;
		}
		}catch(Exception e) {
			sender.sendMessage(ChatColor.GRAY + "/lvl add/remove <Player> <Количество>");
			sender.sendMessage(ChatColor.GRAY + "/lvl get <Player>");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> Commands = Arrays.asList("add","remove","get");
		List<String> nums = Arrays.asList("1","2","3","4","5","6","7","8","9");
		ArrayList<String> result = new ArrayList<String>();
		switch(args.length) {
		case 1:
			for(String a : Commands) {
				if(args[0].startsWith(args[0].toLowerCase())) {
					result.add(a);
				}
			}
			return result;
		case 2:
			return null;
		case 3:
			return nums;
		default:
		}
		
		return null;
	}

}
