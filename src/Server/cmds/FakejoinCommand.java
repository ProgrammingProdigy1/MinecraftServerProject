package Server.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FakejoinCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a player.");
			return false;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + args[0] + " joined the game.");
		return true;
	}
}
