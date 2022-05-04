package Server.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import Server.utils.Util;

public class SpawnListener implements Listener{

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		if(Util.inSpawn(event.getLocation()))
			event.setCancelled(true);
	}
}
