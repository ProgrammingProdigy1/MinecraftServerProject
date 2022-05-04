package Server.equipments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class ArcherWarriorKit extends WarriorKit{

	private ItemStack bow, arrows;
	
	public ArcherWarriorKit(ItemStack sword, Material foodType, int foodAmount, int arrows, PotionEffect effect) {
		super(sword, foodType, foodAmount, effect);
		this.bow = new ItemStack(Material.BOW, 1);
		this.arrows = new ItemStack(Material.ARROW, arrows);
		setWarriorLevel("Archer");
	}

	public ItemStack getBow() {
		return new ItemStack(bow);
	}

	public ItemStack getArrows() {
		return new ItemStack(arrows);
	}

	public void setArrows(int arrows) {
		this.arrows.setAmount(arrows);
	}
	
	public void kitPlayer(Player player) {
		super.kitPlayer(player);
		player.getInventory().addItem(bow);
		player.getInventory().addItem(arrows);
		player.sendMessage(ChatColor.DARK_GREEN + "You've been equiped with the archer kit");
	}
}
