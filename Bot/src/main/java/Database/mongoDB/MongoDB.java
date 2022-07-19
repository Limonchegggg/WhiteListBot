package Database.mongoDB;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoServerException;
import com.mongodb.MongoSocketException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import configs.Players;

public class MongoDB {
	
	private String CLIENT = Players.get().getConfigurationSection("MongoDB").getString("MongoClient");
	
	private MongoClient client;
	
	public void Connect() throws MongoClientException, MongoServerException, MongoSocketException{
		MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CLIENT))
                .build();
		this.client = MongoClients.create(settings);
	}
	
	public MongoClient getClient() {
		return client;
	}
	
	public MongoDatabase getDatabase() {
		return client.getDatabase(Players.get().getConfigurationSection("MongoDB").getString("MongoDatabase"));
	}
	
	public void closeConnection() {
		client.close();
	}
	
	public boolean isConnected() {
		return (client == null ? false : true);
	}
	
}
