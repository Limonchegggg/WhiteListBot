package Database;

import java.sql.SQLException;

import javax.naming.CommunicationException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import Main.Main;

public class TryConnectDataBase {
	private Main main = Main.getPlugin(Main.class);
	private Plugin plugin = Main.getPlugin(Main.class);
	
	public TryConnectDataBase() throws SQLException, ClassNotFoundException, CommunicationException{
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			do {
				try {
					main.sql.connect();
				} catch (ClassNotFoundException | SQLException e) {
				}
			}while(!main.sql.isConnected());
		}, 20, 0);
	}
}