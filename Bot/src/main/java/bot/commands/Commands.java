package bot.commands;

import java.awt.Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import Main.Main;
import configs.Players;
import configs.PlayersGetter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.md_5.bungee.api.ChatColor;

public class Commands extends ListenerAdapter{
	private Main plugin = Main.getPlugin(Main.class);
	private PlayersGetter pg = new PlayersGetter();
	@Override
	public void onMessageReceived(MessageReceivedEvent e){
		//Игрок добавляет в лист
		String[] message = e.getMessage().getContentRaw().split(" ");
		switch(message[0]){
			case "online":
					if(!e.getChannel().getId().equals(Players.get().getString("ChannelCommand"))) return;
					if(message.length > 1) {
						e.getChannel().sendMessage("Я не понимаю что вы хотите написать!").queue();
						return;
					}
					EmbedBuilder eb = new EmbedBuilder();
					if(Bukkit.getOnlinePlayers().size() == 0) {
						eb.setColor(Color.HSBtoRGB(236,207,4));
						eb.setAuthor("Shybka");
						eb.setTitle("◄ Игроки онлайн ►");
						eb.appendDescription("**▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬**" + "\n**◕ Сейчас никто не играет ◕**");
						eb.setFooter("но вы всегда можете это исправить ( ͝° ͜ʖ͡°)" + "\nIP: shybka.gomc.fun " + "\nВерсия: 1.18.1");
						e.getChannel().sendMessageEmbeds(eb.build()).queue();
					}else {
						eb.setColor(Color.HSBtoRGB(236,207,4));
						eb.setColor(Color.HSBtoRGB(236,207,4));
						eb.setAuthor("Shybka");
						eb.setTitle("◄ Игроки онлайн ►");
						eb.appendDescription("**▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬ ▬**" + "\n");
						eb.setFooter("Играйте с нами! " + "\nIP: shybka.gomc.fun " + "\nВерсия: 1.18.1");
						for(Player p : Bukkit.getOnlinePlayers()) {
							eb.appendDescription("**➜ " + p.getName() + "**" + "\n");
						}
						e.getChannel().sendMessageEmbeds(eb.build()).queue();
					}
				break;
			case "помоги":
				if(e.getChannelType() != ChannelType.PRIVATE) {
					break;
				}
				Player player1 = plugin.stack_players.get(e.getAuthor().getId());
				String ID = e.getAuthor().getId();
				if(!plugin.stack_players.containsKey(ID)) {
					e.getAuthor().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Я не вижу что ты застрял, ничего делать не буду").queue();
					});
					break;
				}
				if(!player1.isOnline()) {
					e.getAuthor().openPrivateChannel().queue((channel) -> {
						channel.sendMessage("Я не вижу тебя на сервере").queue();
					});
					break;
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						player1.teleport(player1.getLocation().add(1.0, 0.0, 1.0));
						cancel();
					}
				}.runTask(plugin);
				e.getAuthor().openPrivateChannel().queue((channel) -> {
					channel.sendMessage("Я сделал все что смог").queue();
					plugin.stack_players.remove(ID);
				});
				break;
			case "tell":
				if(e.getChannelType() != ChannelType.PRIVATE) {
					break;
				}
				if(e.getAuthor().isBot()) {
					sendPrivateMessage(e.getAuthor(), "Блять ну привет я тоже бот");
					return;
				}
				if(message.length < 3) {
					sendPrivateMessage(e.getAuthor(), "Чтобы отправить личное сообщение в игру, тебе необходимо ввести [tell <Ник игрока> <Сообщение>]");
					return;
				}
				Player player2 = Bukkit.getPlayer(message[1]);
				if(player2 == null) {
					sendPrivateMessage(e.getAuthor(), "Я не вижу **" + message[1] + "** на сервере");
					break;
				}
				if(!player2.isOnline()) {
					sendPrivateMessage(e.getAuthor(), "Я не вижу **" + message[1] + "** на сервере");
					return;
				}
				String msg = e.getMessage().getContentRaw().replace("tell " + message[1], "");
				player2.sendMessage(ChatColor.GRAY + "[ЛС]" + ChatColor.BLUE + "[Discord]" + ChatColor.WHITE + e.getAuthor().getName() + ":" + msg);
				System.out.println("[WhiteListBot Logger]: " + "[ЛС][Discord] "+ e.getAuthor().getName() + ":" + msg);
				break;
			default:
				if(!e.getChannel().getId().equals(pg.getTeleMessageChannelId())) {
					return;
				}
				if(e.getAuthor().isBot()) {
					return;
				}
				if(Bukkit.getOnlinePlayers().size() == 0) {
					return;
				}
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					player.sendMessage(ChatColor.BLUE + "[Discord] " + ChatColor.WHITE + e.getAuthor().getName() + ": " + e.getMessage().getContentRaw());
				}
				System.out.println("[WhiteListBot Logger]: " + "[Discord] " + e.getAuthor().getName() + ": " + e.getMessage().getContentRaw());
				break;
		}
	}
	
	public void sendPrivateMessage(User user, String message) {
		user.openPrivateChannel().queue((channel) ->{
			channel.sendMessage(message).queue();
		});
		return;
	}
	public void sendSyncMessageBukkit(Player player, String message) {
		new BukkitRunnable() {
			@Override
			public void run() {
				player.sendMessage(message);
				cancel();
			}
		}.runTask(plugin);
		
	}
	
}
