package Server.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Server.main.ServerMain;
import Server.utils.SettingsManager;

public class SpawnsetCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can set a spawn point");
			return false;
		}
		
		Player player = (Player)sender;
		if(args.length > 0) {
			player.sendMessage(ChatColor.RED + "illegal Command!");
			return false;
		}
		SettingsManager sm = ServerMain.getManager();
		Location loc = player.getLocation();
		sm.getConfig().set("spawn.x", loc.getX());
		sm.getConfig().set("spawn.y", loc.getY());
		sm.getConfig().set("spawn.z", loc.getZ());
		sm.saveConfig();
		player.sendMessage(ChatColor.GREEN + "The spawn location is now: " + loc.toString());
		return true;
	}
}
