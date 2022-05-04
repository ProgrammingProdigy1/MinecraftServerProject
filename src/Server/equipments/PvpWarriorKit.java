package Server.equipments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PvpWarriorKit extends WarriorKit{

	public PvpWarriorKit(ItemStack sword, Material foodType, int foodAmount, PotionEffect effect) {
		super(sword, foodType, foodAmount, effect);
		setWarriorLevel("Pvp");
	}
	
	public void kitPlayer(Player player) {
		super.kitPlayer(player);
		player.sendMessage(ChatColor.DARK_GREEN + "You've been equiped with the pvp kit");
	}
}
