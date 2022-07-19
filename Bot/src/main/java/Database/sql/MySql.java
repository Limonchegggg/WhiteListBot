package Database.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import configs.Players;

public class MySql {
	
	private String host = Players.get().getString("host");
	private String port = Players.get().getString("port");
	private String database = Players.get().getString("database");
	private String username = Players.get().getString("username");
	private String password = Players.get().getString("password");
	private String jdbc = Players.get().getString("jdbc");
	
	private Connection connection;
	
	public boolean isConnected() {
		return (connection == null ? false : true);
	}
	
	public void connect() throws ClassNotFoundException, SQLException{
		if(!isConnected()) {
			try {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				connection = DriverManager.getConnection(jdbc);
			}catch(SQLException | NullPointerException e) {
				Class.forName("org.mariadb.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false",username,password);
				connection.setAutoCommit(true);
			}
			}catch(SQLException ex) {
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		return;
	}
	public void disconnect() {
		if(isConnected()) {
			try {
				connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public Connection getConnection() {
		return connection;
	}
}