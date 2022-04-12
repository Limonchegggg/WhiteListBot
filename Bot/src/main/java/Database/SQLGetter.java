package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.Main;

public class SQLGetter {
	
	private Main plugin;
	public SQLGetter(Main plugin) {
		this.plugin = plugin;
	}
	public void createTable() {
		PreparedStatement ps;
		try {
			ps = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS whitelist " 
					+ "(NAME VARCHAR(100),"
					+ "DISCORD VARCHAR(100), "
					+ "DISCORDID VARCHAR(100),"
					+ "PRIMARY KEY (NAME))");
			ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cratePlayer(String playerName, String discord, String discordId) {
		try {
			if(!existsPlayer(playerName) && !existsDiscordId(discordId) && !existsDiscord(discord)) {
				PreparedStatement ps2 = plugin.sql.getConnection().prepareStatement("INSERT IGNORE INTO whitelist "
						+ "(NAME,DISCORD,DISCORDID) VALUES (?,?,?)");
				ps2.setString(1, playerName);
				ps2.setString(2, discord);
				ps2.setString(3, discordId);
				ps2.executeUpdate();
				return;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removePlayer(String playerName) {
		try {
			if(existsPlayer(playerName)) {
					Statement stm = plugin.sql.getConnection().createStatement();
					ResultSet rs = stm.executeQuery("DELETE FROM whitelist WHERE NAME='" + playerName + "'");
					rs.close();
					stm.close();
				return;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
public boolean existsPlayer(String playerName) {
	try {
		Statement stm = plugin.sql.getConnection().createStatement();
		ResultSet rs = stm.executeQuery("SELECT * FROM whitelist WHERE NAME='"+playerName+"'");
		if(rs.next()) {
			return true;
		}
		//Нет игрока
		return false;
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return false;
}
	public boolean existsDiscordId(String discordid) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM whitelist WHERE DISCORDID=?");
			ps.setString(1, discordid);
			
			ResultSet rs1 = ps.executeQuery();
			if(rs1.next()) {
				//Есть игрок
				return true;
			}
			//Нет игрока
			return false;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	public void removeDiscordId(String discordId) {
		try {
		Statement stm = plugin.sql.getConnection().createStatement();
		ResultSet rs = stm.executeQuery("DELETE FROM whitelist WHERE DISCORDID= '"+discordId+"'");
		rs.close();
		stm.close();
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public String getIdFromName(String playername) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT DISCORDID FROM whitelist WHERE NAME=?");
			ps.setString(1, playername);
			ResultSet rs = ps.executeQuery();
			String ID = " ";
			if(rs.next()) {
				ID = rs.getString("DISCORDID");
				return ID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean existsDiscord(String discord) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT * FROM whitelist WHERE DISCORD=?");
			ps.setString(1, discord);
			ResultSet rs1 = ps.executeQuery();
			if(rs1.next()) {
				//Есть игрок
				return true;
			}
			//Нет игрока
			return false;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
}
