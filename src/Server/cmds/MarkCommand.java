package Server.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Server.utils.PointMarker;
import net.md_5.bungee.api.ChatColor;

public class MarkCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "Only players can mark locations");
			return false;
		}
		Player player = (Player)sender;
		if(args.length != 1) {
			player.sendMessage("Either No category was specified, or there are too many arguments");
			return false;
		}
		PointMarker marker = new PointMarker(1, args[0]);
		boolean success = marker.insertPoint(player.getLocation());
		if (success) {
			String msg = "Point number %d of category '%s' was inserted successfully";
			player.sendMessage(
					ChatColor.GREEN + String.format(msg, marker.getN(), marker.getCategory()));
			return true;
		}
		return false;
	}
}
