package TextForce;

import org.bukkit.Bukkit;

public class PlaceHolders {
	public String onlinePlayers(String text) {
		String newText = text.replace("%players%", ""+Bukkit.getOnlinePlayers().size());
		return newText;
	}
	public String maxOnlinePlayers(String text) {
		String newText = text.replace("%maxOnline%", ""+Bukkit.getMaxPlayers());
		return newText;
	}
}
