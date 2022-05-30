package bot.commands;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class hui {
	static JDA jda;
	final static String TOCKEN = "ODQ4ODU2NTMwOTMyNzI3ODM4.GxyjYF.Sr_ClKHHUu9Sk_ksTzDIXfxWVynTU78IIJagP4";

	public static void main(String[] args) {
		try {
			jda = JDABuilder.createDefault(TOCKEN).build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		
	}

}
