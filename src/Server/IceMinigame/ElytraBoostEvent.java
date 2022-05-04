package Server.IceMinigame;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ElytraBoostEvent extends Event{

	private Player player;
	final long coolDownInSeconds = 7;
	
	public ElytraBoostEvent(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	private static final HandlerList handlers = new HandlerList();
    
    public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
