package Database.sql;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
					PreparedStatement ps = plugin.sql.getConnection().prepareStatement("DELETE FROM whitelist WHERE NAME='"+playerName+"'");
					ps.executeUpdate();
					ps.close();
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
	public String getDiscordName(String playerName) {
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT DISCORD FROM whitelist WHERE NAME=?");
			ps.setString(1, playerName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String name = rs.getString("DISCORD");
				return name;
			}
			
		}catch(Exception e) {
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
	
	public Array getKeys(){
		try {
			PreparedStatement ps = plugin.sql.getConnection().prepareStatement("SELECT NAME FROM whitelist WHERE NAME=?");
			ResultSet rs = ps.executeQuery();
			Array list = rs.getArray("NAME");
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<String> getValues() throws SQLException{
		try (Statement s = plugin.sql.getConnection().createStatement()){
			try(ResultSet rs = s.executeQuery("SELECT NAME FROM whitelist")){
				List<String> names = new ArrayList<String>();
				while(rs.next()) {
					names.add(rs.getString("NAME"));
				}
				return names;
			}
		}
	}
}
