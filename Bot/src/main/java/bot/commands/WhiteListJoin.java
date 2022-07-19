package bot.commands;


import java.util.List;

import com.google.common.collect.ImmutableMap;

import Database.DataBaseType;
import Database.mongoDB.MongoDbTables.Collection;
import Main.Main;
import bot.DiscordData;
import configs.Players;
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
		String command = message[0].toLowerCase(); // Убирает чувствительность к регистру
		if(!command.equals("gowl")) {
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
		String nick = message[1];
		String author = e.getAuthor().getName();
		String ID = e.getAuthor().getId();
		switch(DataBaseType.getByName(Players.get().getString("DatabaseType"))) {
		case All:
			if(plugin.mongoTables.containValue("NickName", nick, Collection.WhiteList) || plugin.mongoTables.containValue("Discord", author, Collection.WhiteList) || plugin.mongoTables.containValue("_id", ID, Collection.WhiteList)
					|| plugin.data.existsPlayer(nick) || plugin.data.existsDiscord(author) || plugin.data.existsDiscordId(ID)) {
				e.getChannel().sendMessage("**Такой ник зарегистрирован или у вас уже есть аккаунт на сервере!**").queue();
				return;
			}
			break;
		case MongoDB:
			if(plugin.mongoTables.containValue("NickName", nick, Collection.WhiteList) || plugin.mongoTables.containValue("Discord", author, Collection.WhiteList) || plugin.mongoTables.containValue("_id", ID, Collection.WhiteList)) {
				e.getChannel().sendMessage("**Такой ник зарегистрирован или у вас уже есть аккаунт на сервере!**").queue();
				return;
			}
			break;
		case MySQL:
			if(plugin.data.existsPlayer(nick) || plugin.data.existsDiscord(author) || plugin.data.existsDiscordId(ID)) {
				e.getChannel().sendMessage("**Такой ник зарегистрирован или у вас уже есть аккаунт на сервере!**").queue();
				return;
			}
			break;
		case None:
			e.getChannel().sendMessage("**Вайтлист отключен на сервере или работает по другому!**").queue();
			return;
		default:
			break;
		
		}
		
		if(pg.isTicket()) {
			if(DiscordData.get().getStringList("Queue").contains(e.getAuthor().getId())) {
				e.getChannel().sendMessage("**Вы уже состоите в очереди**").queue();
				return;		
			}
			e.getMessage().addReaction("👍").queue();
			e.getMessage().addReaction("👎").queue();
			List<String> queue = DiscordData.get().getStringList("Queue");
			queue.add(e.getAuthor().getId());
			DiscordData.get().set("Queue", queue);
			DiscordData.save();
			DiscordData.reload();
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
				String[] msg = message.getContentRaw().split(" ");
				if(!DiscordData.get().getStringList("Queue").contains(message.getAuthor().getId())) return;
				String Nick = msg[1];
				String Discord = message.getAuthor().getName();
				String DiscordId = message.getAuthor().getId();
				switch(e.getReactionEmote().getName()) {
				//Палец вверх
				case "👍":
					switch(DataBaseType.getByName(Players.get().getString("DatabaseType"))) {
					case All:
						plugin.mongoTables.addDocument(plugin.mongoTables.CreateDocument(ImmutableMap.of("_id", DiscordId, "NickName", Nick, "Discord", Discord)), Collection.WhiteList);
						plugin.data.cratePlayer(Nick, Discord, DiscordId);
						plugin.sqld.CreatePlayer(Nick, Discord, DiscordId);
						break;
					case MongoDB:
						plugin.mongoTables.addDocument(plugin.mongoTables.CreateDocument(ImmutableMap.of("_id", DiscordId, "NickName", Nick, "Discord", Discord)), Collection.WhiteList);
						break;
					case MySQL:
						plugin.data.cratePlayer(Nick, Discord, DiscordId);
						plugin.sqld.CreatePlayer(Nick, Discord, DiscordId);
						break;
					default:
						break;
					
					}
					List<String> queue = DiscordData.get().getStringList("Queue");
					queue.remove(DiscordId);
					DiscordData.get().set("Queue", queue);
					DiscordData.save();
					DiscordData.reload();
					message.getAuthor().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Дорогой `" + Nick + "`! Теперь ты можешь играть на сервере! 😎").queue();
					});
					return;
				//Палец вниз
				case "👎":
					List<String> queue1 = DiscordData.get().getStringList("Queue");
					queue1.remove(DiscordId);
					DiscordData.get().set("Queue", queue1);
					DiscordData.save();
					DiscordData.reload();
					message.getAuthor().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Дорогой `" + Nick + "`! Мне жаль, тебе отказали, но не расстраивайся и попробуй еще раз! 😿").queue();
					});
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