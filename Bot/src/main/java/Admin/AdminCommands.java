package Admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Main.Main;
import methods.Text;
import net.md_5.bungee.api.ChatColor;

public class AdminCommands implements CommandExecutor{
	private Main main = Main.getPlugin(Main.class);
	@Override
	public boolean onCommand(CommandSender sender, Command com, String str, String[] args) {
		if(!sender.hasPermission("bwt.ban")) {
			sender.sendMessage(ChatColor.RED + "У вас нет доступа!");
			return false;
		}
		if(args.length == 0) {
			sender.sendMessage(ChatColor.GRAY + "/adm [ban/pardon]");
			return false;
		}
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
			String reason = String.join(" ", args);
			reason = reason.replace(args[0], "");
			reason = reason.replace(args[1] + " ", "");
			reason = reason.replace(args[2] + " ", "");
			reason = reason.replace(args[3] + " ", "");
			String discordReason = reason;
			switch(args[3]){
			case "h":
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
			case "m":
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
			case "s":
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
			String reasonMute = String.join(" ", args);
			reasonMute = reasonMute.replace(args[0], "");
			reasonMute = reasonMute.replace(args[1] + " ", "");
			reasonMute = reasonMute.replace(args[2] + " ", "");
			reasonMute = reasonMute.replace(args[3] + " ", "");
			switch(args[3]) {
			case "h":
				main.mute_list.put(args[1], timeMute*3600);
				text.sendPublicMessage(reasonMute);
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был замучен на " + ChatColor.YELLOW + timeMute + ChatColor.GREEN + " часов по причине " + ChatColor.YELLOW + reasonMute);
				return false;
			case "m":
				main.mute_list.put(args[1], timeMute*60);
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был замучен на " + ChatColor.YELLOW + timeMute + ChatColor.GREEN + " минут по причине " + ChatColor.YELLOW + reasonMute);
				return false;
			case "s":
				main.mute_list.put(args[1], timeMute);
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был замучен на " + ChatColor.YELLOW + timeMute + ChatColor.GREEN + " секунд по причине " + ChatColor.YELLOW + reasonMute);
				return false;
			default:
				sender.sendMessage(ChatColor.GRAY + "Доступно только h/m/s");
				return false;
			}
		default:
			sender.sendMessage(ChatColor.GRAY + "/adm [ban/pardon/mute/unmute]");
			return false;
		}
	}
}
