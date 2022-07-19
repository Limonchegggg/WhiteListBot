package Database.mongoDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bson.Document;

import com.google.common.collect.ImmutableMap;
import com.mongodb.client.MongoCollection;

import Main.Main;

public class MongoDbTables {
	Main plugin;
	public MongoDbTables(Main plugin) {
		this.plugin = plugin;
	}
	//Создание аналага таблицы MySql
	public void CreateCollection(Collection collection) {
		plugin.mongoDB.getDatabase().createCollection(collection.getTitle());
	}
	
	public boolean existCollection(Collection collection) {
		return getCollection(collection.getTitle()) == null ? false : true;
	}
	/**
	 * Создает документ с нужными параметрами
	 * @param key_value Ключ - Значение в {@link String}
	 * @return Документ базыданных {@link Document}
	 */
	public Document CreateDocument(HashMap<String, String> key_value){
		Document doc = new Document();
		for(Entry<String, String> key : key_value.entrySet()) {
			doc.append(key.getKey(), key.getValue());
		}
		return doc;
	}
	
	public Document CreateDocument(ImmutableMap<String, String> key_value){
		Document doc = new Document();
		for(Entry<String, String> key : key_value.entrySet()) {
			doc.append(key.getKey(), key.getValue());
		}
		return doc;
	}
	
	//Получение соллекции
	public MongoCollection<Document> getCollection(String name){
		return plugin.mongoDB.getDatabase().getCollection(name);
	}
	
	/**
	 * Добавляет документ в таблицу
	 * @param doc Документ {@link Document}
	 * @param collection Таблица {@link MongoCollection}
	 */
	public void addDocument(Document doc, Collection collection) {
		getCollection(collection.getTitle()).insertOne(doc);
	}
	/**
	 * Добавляет список документов в таблицу
	 * @param docs Список документов {@link List}, {@link Document}
	 * @param collection Таблица {@link MongoCollection}
	 */
	public void addDocuments(List<Document> docs, Collection collection) {
		getCollection(collection.getTitle()).insertMany(docs);
	}
	
	public Document getDocument(String key, String value, Collection collection) {
		MongoCollection<Document> docs = getCollection(collection.getTitle());
		Iterator<Document> iter = docs.find().iterator();
		while(iter.hasNext()) {
			Document doc = iter.next();
			if(doc.getString(key).equalsIgnoreCase(value)) {
				return doc;
			}
		}
		return null;
	}
	
	public List<Document> getDokuments(Collection collection){
		MongoCollection<Document> docs = getCollection(collection.getTitle());
		Iterator<Document> iter = docs.find().iterator();
		List<Document> ldocs = new ArrayList<Document>();
		while(iter.hasNext()) {
			ldocs.add(iter.next());
		}
		return ldocs;
	}
	
	public void deleteDocument(Document doc, Collection collection) {
		MongoCollection<Document> docs = getCollection(collection.getTitle());
		docs.deleteOne(doc);
	}
	
	//Получение всех значений ключа
	public List<String> getValues(String key, Collection collection) {
		MongoCollection<Document> docs = getCollection(collection.getTitle());
		List<String> values = new ArrayList<String>();
		Iterator<Document> iter = docs.find().iterator();
		while(iter.hasNext()) {
			Document doc = iter.next();
			values.add(doc.getString(key));
		}
		return values;
	}
	//Проверка наличия значения по ключу
	public boolean containValue(String key, String value, Collection collection) {
		MongoCollection<Document> docs = getCollection(collection.getTitle());
		Iterator<Document> iter = docs.find().iterator();
		while(iter.hasNext()) {
			Document doc = iter.next();
			if(doc.getString(key).equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
	
	public void replaceValue(String key, String oldValue, String newValue, Collection collection) {
		MongoCollection<Document> docs = getCollection(collection.getTitle());
		Iterator<Document> iter = docs.find().iterator();
		while(iter.hasNext()) {
			Document doc = iter.next();
			if(doc.getString(key).equalsIgnoreCase(oldValue)) {
				doc.replace(key, oldValue, newValue);
				return;
			}
		}
	}
	
	public enum Collection{
		WhiteList("WhiteList"),
		Discord("Discord");
		
		String title;
		
		Collection(String title) {
			this.title = title;
		}
		
		public String getTitle() {
			return title;
		}
		
	}
}
