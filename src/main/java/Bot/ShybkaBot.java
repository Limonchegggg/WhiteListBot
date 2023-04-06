package Bot;

import Bot.Commands.onlineCommand;
import Bot.Commands.onlineSlashCommand;
import Config.MainConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class ShybkaBot {
	private static JDA jda;
	
	public static void Start(String Key) {
		jda = JDABuilder
				.createDefault(Key)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT)
				.build();
		
		if(MainConfig.get().getConfigurationSection("onlineCommand").getBoolean("isSlashCommand") == false) {
			jda.addEventListener(new onlineCommand());
		}else {
			jda.upsertCommand(MainConfig.get().getConfigurationSection("onlineCommand").getString("command"), MainConfig.get().getConfigurationSection("onlineCommand").getString("slashCommandDescription"));
			jda.addEventListener(new onlineSlashCommand());
		}
		
	}
	public static JDA getJDA() {
		return jda;
	}
	public static void Stop() {
		if(jda != null) {
			jda.setAutoReconnect(false);
			jda.shutdownNow();
			jda.getHttpClient().connectionPool().evictAll();
			jda.getHttpClient().dispatcher().executorService().shutdown();
			jda = null;
		}
	}
}
