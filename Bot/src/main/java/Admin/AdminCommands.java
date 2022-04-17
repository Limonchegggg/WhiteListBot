package Admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Main;
import methods.Text;
import methods.timeParcer;
import net.md_5.bungee.api.ChatColor;

public class AdminCommands implements CommandExecutor{
	private Main main = Main.getPlugin(Main.class);
	@Override
	public boolean onCommand(CommandSender sender, Command com, String str, String[] args) {
		if(!sender.hasPermission("bwt.ban")) {
			sender.sendMessage(ChatColor.RED + "У вас нет доступа!");
			return false;
		}
		
		switch(args[0]) {
		case "ban":
			if(args.length < 5) {
				sender.sendMessage(ChatColor.GRAY + "/adm ban <Ник> <Длительноесть> <[h/m/s]> <Причина>");
				return false;
			}
			Player player = Bukkit.getPlayer(args[1]);
			int time = Integer.parseInt(args[2]);
			String reason = str.replace(args[0] + args[1] + args[2] + args[3], null);
			timeParcer tp = new timeParcer(time, args[3].toCharArray()[0]);
			Text text = new Text();
			switch(args[3]){
			case "h":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " часов по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], tp.getHour());
				main.jda.getJDA().getUserById(main.data.getIdFromName(args[1])).openPrivateChannel().queue(channel ->{
					channel.sendMessage("Мне жаль, но тебя забанили! Тебе нужно подождать " + time + " часов перед игрой.");
				});
				player.kickPlayer("Вы были забанены на " + time + " часов");
				return false;
			case "m":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " минут по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], tp.getMinute());
				main.jda.getJDA().getUserById(main.data.getIdFromName(args[1])).openPrivateChannel().queue(channel ->{
					channel.sendMessage("Мне жаль, но тебя забанили! Тебе нужно подождать " + time + " минут перед игрой.");
				});
				player.kickPlayer("Вы были забанены на " + time + " минут");
				return false;
			case "s":
				text.sendPublicMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + " был забанен на " + ChatColor.YELLOW + time + ChatColor.GREEN + " секунд по причине " + ChatColor.YELLOW + reason);
				main.ban_list.put(args[1], tp.getSecond());
				main.jda.getJDA().getUserById(main.data.getIdFromName(args[1])).openPrivateChannel().queue(channel ->{
					channel.sendMessage("Мне жаль, но тебя забанили! Тебе нужно подождать " + time + " секунд перед игрой.");
				});
				player.kickPlayer("Вы были забанены на " + time + " секунд");
				return false;
			}
			return false;
		case "pardon":
			if(args.length < 2) {
				sender.sendMessage(ChatColor.GRAY + "/adm pardon <Ник>");
			}
			main.ban_list.remove(args[1]);
			sender.sendMessage(ChatColor.GRAY + "Вы разбанили игрока " + args[1]);
			main.jda.getJDA().getUserById(main.data.getIdFromName(args[1])).openPrivateChannel().queue(channel ->{
				channel.sendMessage("Тебя Разбанили! Теперь ты можешь играть на сервере!");
			});
			return false;
		default:
			sender.sendMessage(ChatColor.GRAY + "/adm [ban/pardon]");
			return false;
		}
	}
}
