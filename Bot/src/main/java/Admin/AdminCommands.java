package Admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			Player player = Bukkit.getPlayer(args[1]);
			int time = Integer.parseInt(args[2]);
			String timeString = args[2];
			String reason = String.join(" ", args);
			String type = args[3];
			reason = reason.replace("ban ", "");
			reason = reason.replace(player.getName() + " ", "");
			reason = reason.replace(timeString + " ", "");
			reason = reason.replace(type + " ", "");
			Text text = new Text();
			switch(args[3]){
			case "h":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " часов по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], time*3600);
				player.kickPlayer("Вы были забанены на " + time + " часов");
				main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(player.getName())).queue((channel) ->{
					channel.sendMessage("Мне жаль, но тебя забанили! Тебе нужно подождать " + time + " часов перед игрой.");
				});
				return false;
			case "m":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " минут по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], time*60);
				player.kickPlayer("Вы были забанены на " + time + " минут");
				main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(player.getName())).queue((channel) ->{
					channel.sendMessage("Мне жаль, но тебя забанили! Тебе нужно подождать " + time + " минут перед игрой.");
				});
				return false;
			case "s":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " секунд по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], time);
				player.kickPlayer("Вы были забанены на " + time + " секунд");
				main.jda.getJDA().openPrivateChannelById(main.data.getIdFromName(player.getName())).queue((channel) ->{
					channel.sendMessage("Мне жаль, но тебя забанили! Тебе нужно подождать " + time + " секунд перед игрой.");
				});
				return false;
			}
			return false;
		case "pardon":
			if(args.length < 2) {
				sender.sendMessage(ChatColor.GRAY + "/adm pardon <Ник>");
			}
			if(!main.ban_list.containsKey(args[1])) {
				sender.sendMessage(ChatColor.GRAY + "Этот игрок не в бане");
				return false;
			}
			main.ban_list.remove(args[1]);
			sender.sendMessage(ChatColor.GRAY + "Вы разбанили игрока " + args[1]);
			main.jda.getJDA().getUserById(main.data.getIdFromName(args[1])).openPrivateChannel().queue(channel ->{
				channel.sendMessage("Тебя Разбанили! Теперь ты можешь играть на сервере!");
			});
		default:
			sender.sendMessage(ChatColor.GRAY + "/adm [ban/pardon]");
			return false;
		}
	}
}
