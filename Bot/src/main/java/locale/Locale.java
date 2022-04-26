package locale;

import org.bukkit.entity.Player;

public class Locale {
	public String getLocale(LocaleList locale) {
		switch(locale) {
		case AdminCommandBanTimeLenghtFail:
			break;
		case AdminCommandBanUserDoesNotInWhiteList:
			break;
		case AdminCommandBanUserHour:
			break;
		case AdminCommandBanUserHourDiscord:
			break;
		case AdminCommandBanUserIsBanned:
			break;
		case AdminCommandBanUserMinute:
			break;
		case AdminCommandBanUserMinuteDiscord:
			break;
		case AdminCommandBanUserSecond:
			break;
		case AdminCommandBanUserSecondDiscord:
			break;
		case AdminCommandBanUsing:
			break;
		case AdminCommandMuteHour:
			break;
		case AdminCommandMuteLenghtFail:
			break;
		case AdminCommandMuteMinute:
			break;
		case AdminCommandMuteSecond:
			break;
		case AdminCommandMuteUserIsMuted:
			break;
		case AdminCommandMuteUsing:
			break;
		case AdminCommandPardonUserIsNoBanned:
			break;
		case AdminCommandPardonUserPardon:
			break;
		case AdminCommandPardonUserPardonDiscord:
			break;
		case AdminCommandPardonUsing:
			break;
		case AdminCommandUnMuteUsing:
			break;
		case AdminCommandUserIsNotMuted:
			break;
		case AdminCommandUserUnMuteAdminMessage:
			break;
		case AdminCommandUserUnMutePlayerMessage:
			break;
		case AdminCommandUsing:
			break;
		case BotDiscordReloadConfig:
			break;
		case BotReloadConfig:
			break;
		case BotStartCommand:
			break;
		case BotStopCommand:
			break;
		case BotWrongWordAdd:
			break;
		case BotWrongWordRemove:
			break;
		case ConfigReloaded:
			break;
		case ConfigReloadedFail:
			break;
		case DiscordChatToMinecraft:
			break;
		case DiscordHelp:
			break;
		case DiscordHelpDeny:
			break;
		case DiscordHelpMessage:
			break;
		case DiscordHelpPlayerOnServerFail:
			break;
		case DiscordPardonMessage:
			break;
		case DiscordQueueAccept:
			break;
		case DiscordQueueAccountDeny:
			break;
		case DiscordQueueAdd:
			break;
		case DiscordQueueAddQueue:
			break;
		case DiscordQueueCancel:
			break;
		case DiscordQueueCommandUsing:
			break;
		case DiscordQueueContain:
			break;
		case DiscordQueueDeny:
			break;
		case DiscordQueueFail:
			break;
		case DiscordQueueNickNameDeny:
			break;
		case DiscordQueueNickNameLenghtDeny:
			break;
		case DiscordTellBotDeny:
			break;
		case DiscordTellCommandUsing:
			break;
		case DiscordTellPlayer:
			break;
		case DiscordTellPlayerOnServerFail:
			break;
		case MinecraftAntiSpam:
			break;
		case MinecraftChatMute:
			break;
		case MinecraftGlobalChat:
			break;
		case MinecraftJoinDeny:
			break;
		case MinecraftJoinDenyBan:
			break;
		case MinecraftLocalChat:
			break;
		case MinecraftQuit:
			break;
		case MinecraftToDiscordChat:
			break;
		case MinecraftUnMuteMessage:
			break;
		case PermissionDeny:
			break;
		default:
			break;
		
		}
		return null;
	}
	public String setPlaceHolder(String string, Player player) {
		if(string.contains("&player&")) {
			String str = string.replace("&player&", player.getName());
		}
		return null;
	}
}
