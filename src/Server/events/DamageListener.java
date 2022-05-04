package Server.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import Server.utils.Util;

public class DamageListener implements Listener{

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player))
			return;
		Player player = (Player)event.getDamager();
		if(Util.inSpawn(player.getLocation())) {
			event.setDamage(0);
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_RED + "You cannot attack from within spawn area!");
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player)event.getEntity();
		if(Util.inSpawn(player.getLocation())) {
			event.setDamage(0);
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerBow(EntityShootBowEvent event) {
		if(!(event.getEntity() instanceof Player))
			return;
		Player player = (Player)event.getEntity();
		if(Util.inSpawn(player.getLocation())) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_RED + "You cannot shoot from within spawn area!");
		}
	}
}
