package Server.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RightClickNpcEvent extends Event implements Cancellable{

private static final HandlerList handlers = new HandlerList();
    
	
	private Player player;
	private Object entityPlayer;
	private boolean isCancelled;
	
    public RightClickNpcEvent(Player player, Object entityPlayer) {
		this.player = player;
		this.entityPlayer = entityPlayer;
		this.isCancelled = false;
	}

    public Player getPlayer() {
    	return player;
    }
    
    public Object getNPC() {
    	return entityPlayer;
    }
    
	public HandlerList getHandlers() {
        return handlers;
    }
     
    public static HandlerList getHandlerList() {
        return handlers;
    }

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean paramBoolean) {
		this.isCancelled = paramBoolean;
		
	}  
}
