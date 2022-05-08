package locale;

public enum LocaleList {
	DiscordQueueAdd(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAdd")),
	DiscordQueueAddQueue(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAddQueue")),
	DiscordQueueContain(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueContain")),
	DiscordQueueCancel(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueCancel")),
	DiscordQueueAccept(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAccept")),
	DiscordQueueCommandUsing(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueCommandUsing")),
	DiscordQueueFail(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueFail")),
	DiscordQueueNickNameLenghtDeny(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueNickNameLenghtDeny")),
	DiscordQueueNickNameDeny(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueNickNameDeny")),
	DiscordQueueAccountDeny(LocaleData.get().getConfigurationSection("Discord").getString("DiscordQueueAccountDeny")),
	
	
	DiscordHelpDeny(LocaleData.get().getConfigurationSection("DiscordHelp").getString("DiscordHelpDeny")),
	DiscordHelpPlayerOnServerFail(LocaleData.get().getConfigurationSection("DiscordHelp").getString("DiscordHelpPlayerOnServerFail")),
	DiscordHelp(LocaleData.get().getConfigurationSection("DiscordHelp").getString("DiscordHelp")),
	
	
	DiscordTellBotDeny(LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellBotDeny")),
	DiscordTellCommandUsing(LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellCommandUsing")),
	DiscordTellPlayerOnServerFail(LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellPlayerOnServerFail")),
	DiscordTellPlayer(LocaleData.get().getConfigurationSection("privateDiscordMessage").getString("DiscordTellPlayer")),
	
	
	DiscordChatToMinecraft(LocaleData.get().getConfigurationSection("TeleChat").getString("DiscordChatToMinecraft")),
	
	
	ConfigReloaded(LocaleData.get().getConfigurationSection("Config").getString("ConfigReloaded")),
	ConfigReloadedFail(LocaleData.get().getConfigurationSection("Config").getString("ConfigReloadedFail")),
	
	
	MinecraftJoinDeny(LocaleData.get().getConfigurationSection("DenyJoin").getString("MinecraftJoinDeny")),
	MinecraftJoinDenyBan(LocaleData.get().getConfigurationSection("DenyJoin").getString("MinecraftJoinDenyBan")),
	MinecraftQuit(LocaleData.get().getConfigurationSection("DenyJoin").getString("MinecraftQuit")),
	
	
	MinecraftChatMute(LocaleData.get().getConfigurationSection("Chat").getString("MinecraftChatMute")),
	MinecraftAntiSpam(LocaleData.get().getConfigurationSection("Chat").getString("MinecraftAntiSpam")),
	MinecraftLocalChat(LocaleData.get().getConfigurationSection("Chat").getString("MinecraftLocalChat")),
	MinecraftGlobalChat(LocaleData.get().getConfigurationSection("Chat").getString("MinecraftGlobalChat")),
	MinecraftToDiscordChat(LocaleData.get().getConfigurationSection("Chat").getString("MinecraftGlobalChat")),
	
	
	BotStopCommand(LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotStartCommand")),
	BotStartCommand(LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotStartCommand")),
	BotWrongWordAdd(LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotWrongWordAdd")),
	BotWrongWordRemove(LocaleData.get().getConfigurationSection("BotStopCommand").getString("BotWrongWordRemove")),
	
	PermissionDeny(LocaleData.get().getString("PermissionDeny")),
	
	AdminCommandBanUserIsBanned(LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUserIsBanned")),
	AdminCommandBanUserDoesNotInWhiteList(LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUserDoesNotInWhiteList")),
	AdminCommandBanTimeLenghtFail(LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanTimeLenghtFail")),
	AdminCommandBanUser(LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUser")),
	AdminCommandBanUserDiscordMessage(LocaleData.get().getConfigurationSection("Ban").getString("AdminCommandBanUserDiscordMessage")),
	
	AdminCommandPardonUserIsNoBanned(LocaleData.get().getConfigurationSection("Pardon").getString("AdminCommandPardonUserIsNoBanned")),
	AdminCommandPardonUserPardon(LocaleData.get().getConfigurationSection("Pardon").getString("AdminCommandPardonUserPardon")),
	AdminCommandPardonUserPardonDiscord(LocaleData.get().getConfigurationSection("Pardon").getString("AdminCommandPardonUserPardonDiscord")),
	
	AdminCommandMuteUserIsMuted(LocaleData.get().getConfigurationSection("Mute").getString("AdminCommandMuteUserIsMuted")),
	AdminCommandMuteLenghtFail(LocaleData.get().getConfigurationSection("Mute").getString("AdminCommandMuteLenghtFail")),
	AdminCommandMuteUser(LocaleData.get().getConfigurationSection("Mute").getString("AdminCommandMuteUser")),
	
	AdminCommandUserIsNotMuted(LocaleData.get().getConfigurationSection("unMute").getString("AdminCommandUserIsNotMuted")),
	AdminCommandUserUnMuteMessage(LocaleData.get().getConfigurationSection("unMute").getString("AdminCommandUserUnMuteMessage")),
	
	DiscordAutoPardonMessage(LocaleData.get().getConfigurationSection("AutoPardon").getString("DiscordAutoPardonMessage")),
	MinecraftUnMuteMessage(LocaleData.get().getConfigurationSection("AutoPardon").getString("MinecraftUnMuteMessage")),
	
	OpenEnderChest(LocaleData.get().getConfigurationSection("Inventory").getString("OpenEnderChest")),
	OpenEnderChestFail(LocaleData.get().getConfigurationSection("Inventory").getString("OpenEnderChestFail")),
	OpenInventory(LocaleData.get().getConfigurationSection("Inventory").getString("OpenInventory")),
	OpenInventoryFail(LocaleData.get().getConfigurationSection("Inventory").getString("OpenEnderChestFail")),;
	
	private String string;
	
	LocaleList(String string) {
		this.string = string;
	}
	public String getString() {
		return string;
	}
	


}
