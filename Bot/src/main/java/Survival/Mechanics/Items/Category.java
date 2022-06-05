package Survival.Mechanics.Items;

public enum Category {
	None("None"),
	Digging("Digging");
	
	String string;
	
	Category(String string) {
		this.string = string;
	}
	
	public String getTitle() {
		return string;
	}
}
