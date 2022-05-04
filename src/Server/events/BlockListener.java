package Server.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import Server.utils.Util;

public class BlockListener implements Listener{

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player= event.getPlayer();
		if(player.isOp()) 
			return;
		if(Util.inSpawn(event.getBlock().getLocation())) {
			event.setCancelled(true);
			String s = ChatColor.DARK_RED + "You cannot place blocks onto spawn area blocks!";
			player.sendMessage(s);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player= event.getPlayer();
		if(player.isOp()) 
			return;
		if(Util.inSpawn(event.getBlock().getLocation())) {
			event.setCancelled(true);
			String s = ChatColor.DARK_RED + "You cannot break spawn area blocks!";
			player.sendMessage(s);
		}
	}
}
