package Server.events;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MedicListener implements Listener {

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[Heal]")) {
			event.setLine(0, "&3[Heal]");
			event.setLine(1, "&3Click here");
			event.setLine(2, "&3to get healed!");
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
			Sign s = (Sign) event.getClickedBlock().getState();
			if (s.getLine(0).equals("&3[Heal]")) {
				event.getPlayer().setHealth(20);
				event.getPlayer().sendMessage(ChatColor.GREEN + "You were healed!");
			}
		}
	}
}
