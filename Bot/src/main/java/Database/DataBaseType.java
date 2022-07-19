package Database;

public enum DataBaseType {
	MySQL("MySQL"),
	MongoDB("MongoDB"),
	All("All"),
	None("None");
	
	String label;
	DataBaseType(String label){
		this.label = label;
	}
	
	public String getTitle() {
		return label;
	}
	
	public static DataBaseType getByName(String name) {
		switch(name.toLowerCase()) {
		case "mysql":
			return MySQL;
		case "mongodb":
			return MongoDB;
		case "all":
			return All;
		case "none":
			return None;
		default:
			return null;
		}
	}
	

	
}
