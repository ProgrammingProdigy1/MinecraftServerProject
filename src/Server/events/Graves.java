package Server.events;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;

import Server.main.ServerMain;

public class Graves implements Listener{

	@EventHandler
	
	public void onPlayerDies(PlayerDeathEvent event) throws NoSuchMethodException, SecurityException {
		Player player = event.getEntity();
		HolographicDisplaysAPI.createHologram(ServerMain.getPlugin(ServerMain.class)
				, player.getLocation().add(0, 5, 0)
				, player.getName() + " died here.", "Cause: " + player.getLastDamageCause().getCause().name());
	}
}
