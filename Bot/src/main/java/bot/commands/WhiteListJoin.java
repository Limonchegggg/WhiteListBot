package bot.commands;


import java.util.HashMap;
import Main.Main;
import configs.PlayersGetter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WhiteListJoin extends ListenerAdapter{
	private PlayersGetter pg = new PlayersGetter();
	private Main plugin = Main.getPlugin(Main.class);
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		String[] message = e.getMessage().getContentRaw().split(" ");
		if(!e.getChannel().getId().equals(pg.getChanWhiteList())) {
			return;
		}
		if(!message[0].equals("gowl")) {
			return;
		}
		if(message.length <= 1) {
			e.getChannel().sendMessage("Использование gowl <Ник>" + "\nПример **gowl Shybka**").queue();
			return;
		}
		if(message.length > 2) {
			e.getChannel().sendMessage("Я не понимаю что вы хотите написать!").queue();
			return;
		}
		//Проверка символов
		if(message[1].length() < 4 || message[1].length() > 20) {
			e.getChannel().sendMessage("Минимальная длинна ника 4, а максимальная 20").queue();
			return;
		}
		//Проверка на сеодержание
		String lowerInput = message[1].toLowerCase();
		if(!lowerInput.matches("[A-Za-z0-9_]+")) {
			e.getChannel().sendMessage("Ник должен состоять только из английских букв и/или цифр").queue();
			return;
		}
		if(plugin.data.existsPlayer(message[1]) || plugin.data.existsDiscord(e.getAuthor().getName()) || plugin.data.existsDiscordId(e.getAuthor().getId())) {
			e.getChannel().sendMessage("**Такой ник зарегистрирован или у вас уже есть аккаунт на сервере!**").queue();
			return;
		}
		if(pg.isTicket()) {
			if(plugin.messages_id.containsKey(e.getAuthor().getId())) {
				e.getChannel().sendMessage("**Вы уже состоите в очереди**").queue();
				return;		
			}
			e.getMessage().addReaction("👍").queue();
			e.getMessage().addReaction("👎").queue();
			HashMap<String, String> UserTags = new HashMap<String, String>();
			UserTags.put("Nick", message[1]);
			UserTags.put("Disord", e.getAuthor().getName());
			UserTags.put("DiscordId", e.getAuthor().getId());
			plugin.messages_id.put(e.getAuthor().getId(), UserTags);
			e.getChannel().sendMessage("Дорогой `" + message[1] + "`! Я добавил тебя в очередь и напишу когда тебя примут!").queue();
			return;
		}
		plugin.data.cratePlayer(message[1], e.getAuthor().getName(), e.getAuthor().getId());
		e.getChannel().sendMessage("Дорогой `" + message[1] + "`! Теперь ты можешь играть на сервере!").queue();
	}
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		if(e.getUser().isBot()) return;
		if(!e.getChannel().getId().equals(pg.getChanWhiteList())) return;
		if(!pg.getAdminIdList().contains(e.getUser().getId())) return;
		try {
			e.retrieveMessage().queue(message -> {
				if(!plugin.messages_id.containsKey(message.getAuthor().getId())) return;
				String Nick = plugin.messages_id.get(message.getAuthor().getId()).get("Nick");
				String Discord = plugin.messages_id.get(message.getAuthor().getId()).get("Discrod");
				String DiscordId = plugin.messages_id.get(message.getAuthor().getId()).get("DiscrodId");
				switch(e.getReactionEmote().getName()) {
				//Палец вверх
				case "👍":
					message.getAuthor().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Дорогой `" + Nick + "`! Теперь ты можешь играть на сервере! 😎").queue();
					});
					message.addReaction("😎");
					plugin.data.cratePlayer(Nick, Discord, DiscordId);
					plugin.messages_id.remove(message.getAuthor().getId());
					return;
				//Палец вниз
				case "👎":
					plugin.messages_id.remove(e.getMember().getId());
					message.getAuthor().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Дорогой `" + Nick + "`! Мне жаль, тебе отказали, но не расстраивайся и попробуй еще раз! 😿").queue();
					});
					message.addReaction("😿");
					plugin.messages_id.remove(message.getAuthor().getId());
					return;
				default:
					log("Нетот смайлик");
					return;
				}
			});
		}catch(Exception ee) {
			log("Fail");
		}
	}
	private void log(String text) {
		System.out.println(text);
	}
}