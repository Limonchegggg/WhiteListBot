package locale;


import Admin.AdminCommands;

public class Locale {
	public String getLocale(LocaleList locale) {
		switch(locale) {
		case AdminCommandBanTimeLenghtFail:
			return LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanTimeLenghtFail");

		case AdminCommandBanUser:
			return LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUser");
		case AdminCommandBanUserDiscordMessage:
			return LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUserDiscordMessage");
		case AdminCommandBanUserDoesNotInWhiteList:
			return LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUserDoesNotInWhiteList");
		case AdminCommandBanUserIsBanned:
			return LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUserIsBanned");
		case AdminCommandMuteLenghtFail:
			return LocaleData.get().getConfigurationSection("Mute").getString("AdminCommandMuteLenghtFail");
		case AdminCommandMuteUser:
			return LocaleData.get().getConfigurationSection("Mute").getString("AdminCommandMuteUser");
		case AdminCommandMuteUserIsMuted:
			return LocaleData.get().getConfigurationSection("Mute").getString("AdminCommandMuteUserIsMuted");
		case AdminCommandPardonUserIsNoBanned:
			return LocaleData.get().getConfigurationSection("Pardon").getString("AdminCommandPardonUserIsNoBanned");
		case AdminCommandPardonUserPardon:
			return LocaleData.get().getConfigurationSection("Pardon").getString("AdminCommandPardonUserPardon");
		case AdminCommandPardonUserPardonDiscord:
			return LocaleData.get().getConfigurationSection("Pardon").getString("AdminCommandPardonUserPardonDiscord");
		case AdminCommandUserIsNotMuted:
			return LocaleData.get().getConfigurationSection("unMute").getString("AdminCommandUserIsNotMuted");
		case AdminCommandUserUnMuteMessage:
			return LocaleData.get().getConfigurationSection("unMute").getString("AdminCommandUserUnMuteMessage");
		case BotStartCommand:
			return LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotStartCommand");
		case BotStopCommand:
			return LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotStopCommand");
		case BotWrongWordAdd:
			return LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotWrongWordAdd");
		case BotWrongWordRemove:
			return LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotWrongWordRemove");
		case ConfigReloaded:
			return LocaleData.get().getConfigurationSection("Config").getString("ConfigReloaded");
		case ConfigReloadedFail:
			return LocaleData.get().getConfigurationSection("Config").getString("ConfigReloadedFail");
		case DiscordAutoPardonMessage:
			return LocaleData.get().getConfigurationSection("AutoPardon").getString("DiscordAutoPardonMessage");
		case DiscordChatToMinecraft:
			return LocaleData.get().getConfigurationSection("TeleChat").getString("DiscordChatToMinecraft");
		case DiscordHelp:
			return LocaleData.get().getConfigurationSection("DiscordHelp").getString("DiscordHelp");
		case DiscordHelpDeny:
			return LocaleData.get().getConfigurationSection("DiscordHelp").getString("DiscordHelpDeny");
		case DiscordHelpPlayerOnServerFail:
			return LocaleData.get().getConfigurationSection("DiscordHelp").getString("DiscordHelpPlayerOnServerFail");
		case DiscordQueueAccept:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAccept");
		case DiscordQueueAccountDeny:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAccountDeny");
		case DiscordQueueAdd:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAdd");
		case DiscordQueueAddQueue:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAddQueue");
		case DiscordQueueCancel:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueCancel");
		case DiscordQueueCommandUsing:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueCommandUsing");
		case DiscordQueueContain:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueContain");
		case DiscordQueueFail:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueFail");
		case DiscordQueueNickNameDeny:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueNickNameDeny");
		case DiscordQueueNickNameLenghtDeny:
			return LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueNickNameLenghtDeny");
		case DiscordTellBotDeny:
			return LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellBotDeny");
		case DiscordTellCommandUsing:
			return LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellCommandUsing");
		case DiscordTellPlayer:
			return LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellPlayer");
		case DiscordTellPlayerOnServerFail:
			return LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellPlayerOnServerFail");
		case MinecraftAntiSpam:
			return LocaleData.get().getConfigurationSection("Chat").getString("MinecraftAntiSpam");
		case MinecraftChatMute:
			return LocaleData.get().getConfigurationSection("Chat").getString("MinecraftChatMute");
		case MinecraftGlobalChat:
			return LocaleData.get().getConfigurationSection("Chat").getString("MinecraftGlobalChat");
		case MinecraftJoinDeny:
			return LocaleData.get().getConfigurationSection("DenyJoin").getString("MinecraftJoinDeny");
		case MinecraftJoinDenyBan:
			return LocaleData.get().getConfigurationSection("DenyJoin").getString("MinecraftJoinDenyBan");
		case MinecraftLocalChat:
			return LocaleData.get().getConfigurationSection("Chat").getString("MinecraftLocalChat");
		case MinecraftQuit:
			return LocaleData.get().getConfigurationSection("DenyJoin").getString("MinecraftQuit");
		case MinecraftToDiscordChat:
			return LocaleData.get().getConfigurationSection("Chat").getString("MinecraftToDiscordChat");
		case MinecraftUnMuteMessage:
			return LocaleData.get().getConfigurationSection("AutoPardon").getString("MinecraftUnMuteMessage");
		case PermissionDeny:
			return LocaleData.get().getString("PermissionDeny");
		case OpenEnderChest:
			return LocaleData.get().getConfigurationSection("Inventory").getString("OpenEnderChest");
		case OpenInventory:
			return LocaleData.get().getConfigurationSection("Inventory").getString("OpenInventory");
		case OpenEnderChestFail:
			return LocaleData.get().getConfigurationSection("Inventory").getString("OpenEnderChestFail");
		case OpenInventoryFail:
			return LocaleData.get().getConfigurationSection("Inventory").getString("OpenEnderChestFail");
		default:
			return null;
		
		}
	}
	//%player% %BanWord% %lenght% %time% %reason% %admin% %message%
	public String setPlaceHolder(String string) {
		AdminCommands ac = new AdminCommands();
		String msg = string;
		if(msg.contains("%player%")) {
			msg.replace("%player%", ac.getPlayer());
		}
		if(msg.contains("%lenght%")) {
			msg.replace("%lenght%", ac.getLenght());
		}
		if(msg.contains("%time%")) {
			msg.replace("%time%", ac.getTime());
		}
		if(msg.contains("%reason%")) {
			msg.replace("%reason%", ac.getReason());
		}
		if(msg.contains("%admin%")) {
			msg.replace("%admin%", ac.getAdmin());
		}
		return msg;
	}
}
