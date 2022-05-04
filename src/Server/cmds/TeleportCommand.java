package Server.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can teleport to one another");
			return false;
		}
		Player player = (Player) sender;
		if (args.length != 1) {
			player.sendMessage(ChatColor.RED + "Please specify a player to whom you want to teleport");
			return false;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.RED + "The player specified does not exist");
			return false;
		}
		player.teleport(target);
		return true;
	}
}
