package Server.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Server.utils.Util;

public class ChestPlaceCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can summon their private chests!");
			return false;
		}
		if(args.length != 0) {
			sender.sendMessage(ChatColor.RED + "Invalid arguments!");
			return false;	
		}
		Player player = (Player)sender;
		Location loc = player.getLocation().clone();
		loc.add(loc.getDirection().normalize()).add(0, 1, 0);
		loc.getBlock().setType(Material.CHEST);
		Chest chest = (Chest) loc.getBlock().getState();
		chest.setCustomName(Util.getColour(player) + player.getName() + "'s Inventory");
		chest.getInventory().addItem(new ItemStack(Material.YELLOW_BED, 2));
		return true;
	}
}
