package bot.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;

import Main.Main;
import configs.Players;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ConsoleDiscord extends ListenerAdapter{
	private Main main = Main.getPlugin(Main.class);
	private MessageChannel msgch = main.jda.getJDA().getTextChannelById(Players.get().getString("DiscordConsole"));
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if(e.getChannel().getId() != Players.get().getString("DiscordConsole")) {
			return;
		}
			if(Players.get().getStringList("AllowUsersConsole").contains(e.getAuthor().getId())) {
			return;
		}
		String message = e.getMessage().getContentRaw();
		try {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), message);
		}catch(CommandException ce) {
			e.getChannel().sendMessage("Чтото пошло не так, возможно команда была введена не правильно").queue();
		}
	}
	public void setlogs() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);
		System.out.flush();
		System.setOut(old);
		msgch.sendMessage(baos.toString()).queue();
	}
}
