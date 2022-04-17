package methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
public class Text {
	public String ColorizeText(String text) {
		for(int a = 0; a < text.length(); a++) {
			text = ChatColor.translateAlternateColorCodes('&', text);
		}
		return text;
	}
	public void sendPublicMessage(String message) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
		return;
	}
}
