package Server.bank;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MoneyReceiveEvent extends Event{

	private double money;
	private Player player;
		
	public MoneyReceiveEvent(double money, Player player) {
		this.money = money;
		this.player = player;
	}

	
	public double getMoney() {
		return money;
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
