package Server.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class MotdCommands implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "please spcify the exact method you'd like to perform on the MOTD");
			return false;
		}
		SettingsManager sm = ServerMain.getManager();
		if (args[0].equalsIgnoreCase("getIngame")) {
			sender.sendMessage(sm.getConfig().getString("motd.message"));
			return true;
		}
		if (args[0].equalsIgnoreCase("setIngame") && args.length > 1 && sender.hasPermission("motd.set")) {
			String message = "";
			for (int i = 1; i < args.length; i++) {
				if (args[i].contains("[Player]"))
					args[i] = args[i].replace("[Player]", sender.getName());
				message += args[i] + " ";
			}
			message = message.replaceAll("&", "§");
			sm.getConfig().set("motd.message", message);
			sm.saveConfig();
			sender.sendMessage("The Ingame MOTD was set to: ' " + message + "§f'");
			return true;
		} else if (args[0].equalsIgnoreCase("set") && !(sender.hasPermission("motd.set"))) {
			sender.sendMessage(ChatColor.RED + "You are not permitted to set the Ingame MOTD!");
			return false;
		}
		if (args[0].equalsIgnoreCase("getSystem")) {
			String motd = sm.getConfig().getString("motd.system");
			motd = motd.replaceAll("&", "\u00A7");
			sender.sendMessage(motd);
			return true;
		}
		if (args[0].equalsIgnoreCase("setSystem") && args.length > 1 && sender.hasPermission("motd.systemset")) {
			String message = "";
			for (int i = 1; i < args.length; i++) {
				message += args[i] + " ";
			}
			message = message.replaceAll("&", "\u00A7");
			sm.getConfig().set("motd.system", message);
			sm.saveConfig();
			sender.sendMessage("The Server MOTD was set to: ' " + message + "§f'");
			return true;
		} else if (args[0].equalsIgnoreCase("setSystem") && !(sender.hasPermission("motd.systemset"))) {
			sender.sendMessage(ChatColor.RED + "You are not permitted to set the Server MOTD!");
			return false;
		}
		
		sender.sendMessage(ChatColor.RED + "The MOTD method specified does not exist.");
		return false;
	}
}
