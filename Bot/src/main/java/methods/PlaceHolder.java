package methods;

public class PlaceHolder {
    public String setPlayerPlaceHolder(String text, String player){
       return text.replace("%player%", player);
    }
    public String setUserPlaceHolder(String text, String user) {
    	return text.replace("%user%", user);
    }
    public String setTimeType(String type) {
    	return type.replace("%type%", type);
    }
    public String setTimeLenght(String lenght) {
    	return lenght.replace("%lenght%", lenght);
    }
    public String setReason(String reason) {
    	return reason.replace("%reason%", reason);
    }
    public String setMessage(String message) {
    	return message.replace("%message%", message);
    }
    public String setDiscordUser(String user) {
    	return user.replace("%duser%", user);
    }
    public String setBanWord(String word) {
    	return word.replace("%word%", word);
    }
    
}
