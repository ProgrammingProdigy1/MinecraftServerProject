package Server.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import Server.utils.Util;

public class InventoryListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Material mat = player.getInventory().getItemInMainHand().getType();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && 
				event.getClickedBlock().getState() instanceof Sign) {
			Block sign = event.getClickedBlock();
			Sign s = (Sign)sign.getState();
			if(s.getLine(0).equals("[Kit]:")) {
				String kit = s.getLine(1);
				Util.kitPlayer(player, kit);
			}
		}
	}
}
