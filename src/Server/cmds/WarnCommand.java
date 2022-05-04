package Server.cmds;

import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class WarnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a Player!");
			return false;
		}
		Player target = Bukkit.getServer().getPlayerExact(args[0]);
		if (target == null) {
			sender.sendMessage(ChatColor.RED + "The player " + args[0]
					+ " does not exist or isn't connected to the server at this moment!");
			return false;
		}
		SettingsManager sm = ServerMain.getManager();
		int warn = 0;
		String[] suffixes = { "st", "nd", "rd" };
		String msg = "";
		String path = "warns." + target.getName();
		sm.reloadConfig();
		if (sm.getConfig().contains(path)) {
			BanList lst = Bukkit.getBanList(BanList.Type.NAME);
			warn += sm.getConfig().getInt(path);
			switch (warn) {
			case 1:
				sm.getConfig().set(path, warn+1);
				sm.saveConfig();
				msg = "this is your " + (warn + 1) + suffixes[warn] + " warning!";
				target.kickPlayer(
						"You are kicked from the server, " + msg);;
				break;
			case 2:
				Date date = new Date(System.currentTimeMillis()+60*10*1000);
				msg = "this is your " + (warn + 1) + suffixes[warn] + " warning!";
				target.kickPlayer("You are banned for 10 minutes, " + msg);
				lst.addBan(target.getName(), "You are banned for 10 minutes, " + msg, date, null);
				sm.getConfig().set(path, warn+1);
				sm.saveConfig();
				break;
			case 3:
				target.kickPlayer("You are banned permanently!");
				lst.addBan(target.getName(), "You are banned permanently, it was your last warning", null, null);
				sm.getConfig().set(path, warn+1);
				sm.saveConfig();
				break;
			case 4:
				sender.sendMessage(target.getName() + " is alerady banned permanently!");
				break;
			}
		}
		else {
			msg = "this is your " + (warn + 1) + suffixes[warn] + " warning!";
			sm.getConfig().set("warns." + target.getName(), 1);
			sm.saveConfig();
			target.sendMessage(msg);
		}
		return true;
	}
}
