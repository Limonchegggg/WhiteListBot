package Admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import Main.Main;
import methods.Text;
import net.md_5.bungee.api.ChatColor;



public class AdminCommands implements CommandExecutor, TabCompleter{

	private Main main = Main.getPlugin(Main.class);
	private String reason;
	private String user;
	private int lenght;
	private String admin;
	private String time1;
	@Override
	public boolean onCommand(CommandSender sender, Command com, String str, String[] args) {
		if(!sender.hasPermission("bwt.ban")) {
			sender.sendMessage(ChatColor.RED + "У вас нет доступа!");
			return false;
		}
		if(args.length == 0) {
			sender.sendMessage(ChatColor.GRAY + "/adm [ban/pardon/mute/unmute/endchest]");
			return false;
		}
		Player player = (Player) sender;
		Text text = new Text();
		switch(args[0]) {
		case "ban":
			if(args.length < 5) {
				sender.sendMessage(ChatColor.GRAY + "/adm ban <Ник> <Длительноесть> <[h/m/s]> <Причина>");
				return false;
			}
			if(main.ban_list.containsKey(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Это игрок в бане");
				return false;
			}
			if(!main.data.existsPlayer(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Такого игрока нет в вайтлисте");
				return false;
			}
			int time = Integer.parseInt(args[2]);
			if(time<0) {
				sender.sendMessage(ChatColor.GRAY + "Длительность не может быть отрицательной!");
				return  false;
			}
			admin = player.getName();
			user = args[1];
			lenght = time;
			time1 = args[3];
			reason = String.join(" ", args);
			reason = reason.replace(args[0], "");
			reason = reason.replace(args[1] + " ", "");
			reason = reason.replace(args[2] + " ", "");
			reason = reason.replace(args[3] + " ", "");
			String discordReason = reason;
			switch(args[3]){
			case "ч":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " часов по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], time*3600);
				if(Bukkit.getPlayer(args[1]) != null) {
					Bukkit.getPlayer(args[1]).kickPlayer("Вы были забанены на " + time + " часов");
				}
				try {
				main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(args[1])).queue((channel) ->{
					channel.sendMessage("Мне жаль, но тебя забанили по причине `" + discordReason +"`! Тебе нужно подождать " + time + " часов перед игрой.").queue();
				});
				}catch(Exception e) {
					
				}
				return false;
			case "м":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " минут по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], time*60);
				if(Bukkit.getPlayer(args[1]) != null) {
				Bukkit.getPlayer(args[1]).kickPlayer("Вы были забанены на " + time + " минут");
				}
				try {
				main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(args[1])).queue((channel) ->{
					channel.sendMessage("Мне жаль, но тебя забанили по причине `" + discordReason +"`! Тебе нужно подождать " + time + " минут перед игрой.").queue();
				});
				}catch(Exception e) {
					
				}
				return false;
			case "с":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " секунд по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], time);
				if(Bukkit.getPlayer(args[1]) != null) {
				Bukkit.getPlayer(args[1]).kickPlayer("Вы были забанены на " + time + " секунд");
				}
				try {
				main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(args[1])).queue((channel) ->{
					channel.sendMessage("Мне жаль, но тебя забанили по причине `" + discordReason +"`! Тебе нужно подождать " + time + " секунд перед игрой.").queue();
				});
				}catch(Exception e) {
					
				}
				return false;
			default:
				sender.sendMessage(ChatColor.GRAY + "Доступно только h/m/s");
				return false;
			}
		case "pardon":
			if(args.length < 2) {
				sender.sendMessage(ChatColor.GRAY + "/adm pardon <Ник>");
				return false;
			}
			if(!main.ban_list.containsKey(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Этот игрок не в бане");
				return false;
			}
			if(!main.data.existsPlayer(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Такого игрока нет в вайтлисте");
				return false;
			}
			main.ban_list.remove(args[1]);
			sender.sendMessage(ChatColor.GRAY + "Вы разбанили игрока " + args[1]);
			main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(args[1])).queue((channel) ->{
				channel.sendMessage("Тебя Разбанили! Теперь ты можешь играть на сервере!").queue();
			});
		case "mute":
			if(args.length < 5) {
				sender.sendMessage(ChatColor.GRAY + "/adm ban <Ник> <Длительноесть> <[h/m/s]> <Причина>");
				return false;
			}
			if(main.mute_list.containsKey(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Игрок уже в муте");
				return false;
			}
			if(!main.data.existsPlayer(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Такого игрока нет в вайтлсте");
				return false;
			}
			int timeMute = Integer.parseInt(args[2]);
			if(timeMute<0) {
				sender.sendMessage(ChatColor.GRAY + "Длительность не может быть отрицательной!");
				return  false;
			}
			user = args[1];
			lenght = Integer.parseInt(args[2]);
			reason = String.join(" ", args);
			reason = reason.replace(args[0], "");
			reason = reason.replace(args[1] + " ", "");
			reason = reason.replace(args[2] + " ", "");
			reason = reason.replace(args[3] + " ", "");
			
			switch(args[3]) {
			case "ч":
				main.mute_list.put(args[1], timeMute*3600);
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был замучен на " + ChatColor.YELLOW + timeMute + ChatColor.GREEN + " часов по причине " + ChatColor.YELLOW + reason);
				return false;
			case "м":
				main.mute_list.put(args[1], timeMute*60);
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был замучен на " + ChatColor.YELLOW + timeMute + ChatColor.GREEN + " минут по причине " + ChatColor.YELLOW + reason);
				return false;
			case "с":
				main.mute_list.put(args[1], timeMute);
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был замучен на " + ChatColor.YELLOW + timeMute + ChatColor.GREEN + " секунд по причине " + ChatColor.YELLOW + reason);
				return false;
			default:
				sender.sendMessage(ChatColor.GRAY + "Доступно только h/m/s");
				return false;
			}
		case "unmute":
			if(args.length < 3) {
				sender.sendMessage(ChatColor.GRAY + "/adm unmute <Ник>");
				return false;
			}
			if(!main.mute_list.containsKey(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Игрок не в муте");
				return false;
			}
			if(!main.data.existsPlayer(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Такого игрока нет в вайтлисте");
				return false;
			}
			main.mute_list.remove(args[1]);
			sender.sendMessage("Игрок размучен");
			Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN + "Вы были размучены");
			return false;
		case "endchest":
			if(args.length < 2) {
				sender.sendMessage(ChatColor.GRAY + "/adm endchest [Ник]");
				return false;
			}
			try {
			Inventory inv = Bukkit.getPlayer(args[1]).getEnderChest();
			player.openInventory(inv);
			sender.sendMessage(ChatColor.GRAY + "Вы открыли Эндер Сундук игрока " +args[1]);
			}catch(Exception e) {
				sender.sendMessage(ChatColor.GRAY + "Ошибка открытия Эндер Сундука у " + args[1]);
			}
			return false;
		case "inventory":
			try {
			Inventory in = Bukkit.getPlayer(args[1]).getInventory();
			player.openInventory(in);
			sender.sendMessage(ChatColor.GRAY + "Вы открыли Инвентарь игрока " + args[1]);
			}catch(Exception e) {
				sender.sendMessage(ChatColor.GRAY + "Ошибка открытия Инвентаря у " + args[1]);
			}
			return false;
		default:
			sender.sendMessage(ChatColor.GRAY + "/adm [ban/pardon/mute/unmute]");
			return false;
		}
	}
	
	public String getReason() {
		return reason;
	}
	public String getPlayer() {
		return user;
	}
	public String getAdmin() {
		return admin;
	}
	public String getLenght() {
		String n = ""+lenght;
		return n;
	}
	public String getTime() {
		return time1;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> arguments = Arrays.asList("ban", "pardon","mute","unmute","endchest","inventory","testPlaceHolder");
		List<String> time = Arrays.asList("1","2","3","4","5","6","7","8","9");
		List<String> type = Arrays.asList("ч","м","с");
		
		List<String> result = new ArrayList<String>();
		
		switch(args.length) {
		case 1:
			for(String a : arguments) {
				if(a.toLowerCase().startsWith(args[0].toLowerCase())) 
					result.add(a);
			}
			return result;
		case 2:
			switch(args[0]) {
			case "pardon":
				if(!BanData.get().getStringList("Names").isEmpty()) {
					for(String s : BanData.get().getStringList("Names")) {
						if(args[1].toLowerCase().startsWith(args[1].toLowerCase()))
							result.add(s);
					}
				}
				return result;
			case "unmute":
				if(!BanData.get().getStringList("MuteList").isEmpty()) {
					for(String s : BanData.get().getStringList("MuteList")) {
						if(args[1].toLowerCase().startsWith(args[1].toLowerCase()))
							result.add(s);
					}
				}
				return result;
			case "endchest":
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(args[1].toLowerCase().startsWith(args[1].toLowerCase()))
							result.add(p.getName());
					}
				return result;
			default:
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
						result.add(player.getName());
				}
				return result;
			}
			
		case 3:
			for(String s : time) {
				if(s.toLowerCase().startsWith(args[2].toLowerCase()))
					result.add(s);
			}
			return result;
		case 4:
			for(String s : type) {
				if(s.toLowerCase().startsWith(args[3].toLowerCase()))
					result.add(s);
			}
			return result;
		default:
			return null;
		}
	}
}
