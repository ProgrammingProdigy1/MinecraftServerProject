package Server.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import mkremins.fanciful.FancyMessage;

public class Subscribe implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws NoSuchMethodException, SecurityException {
		new FancyMessage("Check out PogoStick29's Channel! And don't forget to subscribe! ")
		.color(ChatColor.DARK_RED)
		.then("Click here!")
		.color(ChatColor.BLUE)
		.link("https://www.youtube.com/channel/UCI5kTq_eoaZslY9TQ4XlmmA")
		.tooltip(ChatColor.GREEN + "Click Here!")
		.send(event.getPlayer());
	}
}
