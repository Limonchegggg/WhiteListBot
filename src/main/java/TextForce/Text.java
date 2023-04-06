package TextForce;

import org.bukkit.ChatColor;

public class Text {
	public String ColorizeText(String text) {
		for(int a = 0; a < text.length(); a++) {
			text = ChatColor.translateAlternateColorCodes('&', text);
		}
		return text;
	}
	
}
