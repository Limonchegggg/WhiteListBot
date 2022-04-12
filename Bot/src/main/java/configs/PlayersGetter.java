package configs;

import java.util.List;

public class PlayersGetter {
	public String getTeleMessageChannelId() {
		return Players.get().getString("TeleMessage");
	}
	public String getChanCommand() {
		return Players.get().getString("ChannelCommand");
	}
	public String getChanWhiteList() {
		return Players.get().getString("ChannelId");
	}
	public String getChanConsole() {
		return Players.get().getString("DiscordConsole");
	}
	public boolean isTicket() {
		return Players.get().getBoolean("Tickets");
	}
	public List<String> getAdminIdList(){
		return Players.get().getStringList("Admins");
	}
	public String getServerId() {
		return Players.get().getString("ServerId");
	}
}
