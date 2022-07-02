package bot;

import org.bukkit.Bukkit;

import bot.commands.Commands;
import bot.commands.WhiteListJoin;
import configs.Players;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class WhiteListBot{
	private JDA jda;
	private final String TOKEN = Players.get().getString("token");
	public void startbot() {
		if(TOKEN.isEmpty()) {
			System.out.println("Token is null!");
			return;
		}
		try {
			this.jda = JDABuilder.createDefault(TOKEN).build();
			jda.addEventListener(new Commands());
			jda.addEventListener(new WhiteListJoin());
			
			
			jda.getPresence().setActivity(Activity.watching(Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers()));
			System.out.println("Bot has started!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public JDA getJDA() {
		return jda;
	}
	public void stopBot(){
		if(jda != null) {
			this.jda.setAutoReconnect(false);
			this.jda.shutdownNow();
			this.jda.getHttpClient().connectionPool().evictAll();
			this.jda.getHttpClient().dispatcher().executorService().shutdown();
			this.jda = null;
		}
	}
}