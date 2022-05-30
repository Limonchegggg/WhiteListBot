package Survival;

public class PickAxe {

}
enum ToolType{
	PickAxe("PickAxe"),
	Axe("Axe"),
	Shovel("Shovel"),
	Hoe("Hoe"),
	Sword("Sword");
	
	String string;
	
	ToolType(String string) {
		this.string = string;
	}
	public String getTitle() {
		return string;
	}
}