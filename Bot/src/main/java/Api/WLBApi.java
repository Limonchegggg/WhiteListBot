package Api;

import Main.Main;
import net.dv8tion.jda.api.JDA;

public class WLBApi {
	private Main main = Main.getPlugin(Main.class);
	//Проверка наличия игрока в списке
	public boolean existsPlayer(String player) {
		return main.data.existsPlayer(player);
	}
	//Проверка наличия ИД в списке
	public boolean existsID(String id) {
		return main.sqld.ExistId(id);
	}
	//Получение ИД по никнейму
	public String getID(String name) {
		return main.data.getIdFromName(name);
	}
	//Получение никнейма по иде
	public String getPlayer(String ID) {
		return main.sqld.getPlayer(ID);
	}
	//Получиние JDA
	public JDA getJDA() {
		return main.jda.getJDA();
	}
	
}
