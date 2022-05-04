package Server.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Server.main.ServerMain;
import Server.utils.SettingsManager;
import Server.utils.Util;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can teleport to a spawn point");
			return false;
		}

		Player player = (Player) sender;
		if (args.length != 0) {
			player.sendMessage(ChatColor.RED + "illegal Command!");
			return false;
		}
		SettingsManager sm = ServerMain.getManager();
		World w = Bukkit.getWorld("world");
		double x = sm.getConfig().getDouble("spawn.x"), y = sm.getConfig().getDouble("spawn.y"),
				z = sm.getConfig().getDouble("spawn.z");
		Location loc = new Location(w, x, y, z);
		if (loc.equals(new Location(w, 0, 0, 0))) {
			player.teleport(Util.getSpawn());
		} else
			player.teleport(loc);
		player.sendMessage(ChatColor.GREEN + "Teleported to spawn point");
		return true;
	}
}
