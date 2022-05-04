package Server.notifications;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class ResourcePackUpdate implements Listener{

	@EventHandler
	public void onResourcepackStatusEvent(PlayerResourcePackStatusEvent event){
		if(event.getStatus() == PlayerResourcePackStatusEvent.Status.DECLINED)
			  event.getPlayer().kickPlayer(" You did not accept the resourcepack request.");
	}
}
