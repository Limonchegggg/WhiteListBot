package Database.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.Main;

	public class SQLDiscord {
		private Main main;
		public SQLDiscord(Main main){
			this.main = main;
		};
	public void CreateTable() {
		try {
			PreparedStatement ps = main.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS discord " 
					+ "(NAME VARCHAR(100),"
					+ "DISCORD VARCHAR(100), "
					+ "DISCORDID VARCHAR(100),"
					+ "PRIMARY KEY (DISCORDID))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void CreatePlayer(String playerName, String Discord, String DiscordId) {
		try {
		if(!ExistId(DiscordId)) {
			PreparedStatement ps = main.sql.getConnection().prepareStatement("INSERT IGNORE INTO discord (NAME,DISCORD,DISCORDID) VALUES (?,?,?)");
			ps.setString(1, playerName);
			ps.setString(2, Discord);
			ps.setString(3, DiscordId);
			ps.executeUpdate();
			return;
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void removePlayer(String DiscordId) {
		try {
			if(ExistId(DiscordId)) {
					PreparedStatement ps = main.sql.getConnection().prepareStatement("DELETE FROM whitelist WHERE NAME='"+DiscordId+"'");
					ps.executeUpdate();
					ps.close();
				return;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean ExistId(String id) {
		try {
			Statement state = main.sql.getConnection().createStatement();
			ResultSet rs = state.executeQuery("SELECT * FROM discord WHERE DISCORDID='"+id+"'");
			if(rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getPlayer(String id) {
		try {
			PreparedStatement ps = main.sql.getConnection().prepareStatement("SELECT NAME FROM discord WHERE DISCORDID=?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			String Name = "";
			if(rs.next()) {
				Name = rs.getString("NAME");
				return Name;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
