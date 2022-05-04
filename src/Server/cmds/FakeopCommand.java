package Server.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakeopCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a player.");
			return false;
		}
		Player player = Bukkit.getServer().getPlayer(args[0]);
		if(player == null) {
			sender.sendMessage(ChatColor.RED + "The player '" + args[0] + "' does not exist.");
			return false;
		}
		player.sendMessage(ChatColor.GREEN + "You are now a server operator!");
		sender.sendMessage(ChatColor.GREEN + args[0] + " is now a server operator");
		return true;
	}

}
