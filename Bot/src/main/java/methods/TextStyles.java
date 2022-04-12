package methods;

import org.bukkit.ChatColor;
public class TextStyles {
	public String ColorizeText(String text) {
		for(int a = 0; a < text.length(); a++) {
			text = ChatColor.translateAlternateColorCodes('&', text);
		}
		return text;
	}
}
